package cart.integration;

import static org.assertj.core.api.Assertions.assertThat;

import cart.application.dto.cartitem.CartItemRequest;
import cart.application.dto.cartitem.CartItemResponse;
import cart.application.dto.product.ProductRequest;
import cart.domain.Member;
import cart.persistence.dao.MemberDao;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

public class CartItemIntegrationTest extends IntegrationTest {

    @Autowired
    private MemberDao memberDao;

    private Long productId;
    private Long productId2;
    private Member member;
    private Member member2;

    @BeforeEach
    void setUp() {
        super.setUp();

        productId = createProduct(new ProductRequest("치킨", 10_000, "http://example.com/chicken.jpg"));
        productId2 = createProduct(new ProductRequest("피자", 15_000, "http://example.com/pizza.jpg"));

        member = memberDao.findMemberById(1L).get().toDomain();
        member2 = memberDao.findMemberById(2L).get().toDomain();
    }

    @DisplayName("장바구니에 아이템을 추가한다.")
    @Test
    void addCartItem() {
        CartItemRequest cartItemRequest = new CartItemRequest(productId);
        ExtractableResponse<Response> response = requestAddCartItem(member, cartItemRequest);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @DisplayName("잘못된 사용자 정보로 장바구니에 아이템을 추가 요청시 실패한다.")
    @Test
    void addCartItemByIllegalMember() {
        Member illegalMember = new Member(member.getId(), member.getEmail(), member.getPassword() + "asdf", "asdf");
        CartItemRequest cartItemRequest = new CartItemRequest(productId);
        ExtractableResponse<Response> response = requestAddCartItem(illegalMember, cartItemRequest);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @DisplayName("사용자가 담은 장바구니 아이템을 조회한다.")
    @Test
    void getCartItems() {
        Long cartItemId1 = requestAddCartItemAndGetId(member, productId);
        Long cartItemId2 = requestAddCartItemAndGetId(member, productId2);

        ExtractableResponse<Response> response = requestGetCartItems(member);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());

        List<Long> resultCartItemIds = response.jsonPath().getList(".", CartItemResponse.class).stream()
                .map(CartItemResponse::getId)
                .collect(Collectors.toList());
        assertThat(resultCartItemIds).containsAll(Arrays.asList(cartItemId1, cartItemId2));
    }

    @DisplayName("장바구니에 담긴 아이템의 수량을 변경한다.")
    @Test
    void increaseCartItemQuantity() {
        Long cartItemId = requestAddCartItemAndGetId(member, productId);

        ExtractableResponse<Response> response = requestUpdateCartItemQuantity(member, cartItemId, 10);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());

        ExtractableResponse<Response> cartItemsResponse = requestGetCartItems(member);

        Optional<CartItemResponse> selectedCartItemResponse = cartItemsResponse.jsonPath()
                .getList(".", CartItemResponse.class)
                .stream()
                .filter(cartItemResponse -> cartItemResponse.getId().equals(cartItemId))
                .findFirst();

        assertThat(selectedCartItemResponse.isPresent()).isTrue();
        assertThat(selectedCartItemResponse.get().getQuantity()).isEqualTo(10);
    }

    @DisplayName("장바구니에 담긴 아이템의 수량을 0으로 변경하면, 장바구니에서 아이템이 삭제된다.")
    @Test
    void decreaseCartItemQuantityToZero() {
        Long cartItemId = requestAddCartItemAndGetId(member, productId);

        ExtractableResponse<Response> response = requestUpdateCartItemQuantity(member, cartItemId, 0);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());

        ExtractableResponse<Response> cartItemsResponse = requestGetCartItems(member);

        Optional<CartItemResponse> selectedCartItemResponse = cartItemsResponse.jsonPath()
                .getList(".", CartItemResponse.class)
                .stream()
                .filter(cartItemResponse -> cartItemResponse.getId().equals(cartItemId))
                .findFirst();

        assertThat(selectedCartItemResponse.isPresent()).isFalse();
    }

    @DisplayName("다른 사용자가 담은 장바구니 아이템의 수량을 변경하려 하면 실패한다.")
    @Test
    void updateOtherMembersCartItem() {
        Long cartItemId = requestAddCartItemAndGetId(member, productId);

        ExtractableResponse<Response> response = requestUpdateCartItemQuantity(member2, cartItemId, 10);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @DisplayName("장바구니에 담긴 아이템을 삭제한다.")
    @Test
    void removeCartItem() {
        Long cartItemId = requestAddCartItemAndGetId(member, productId);

        ExtractableResponse<Response> response = requestDeleteCartItem(member, cartItemId);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());

        ExtractableResponse<Response> cartItemsResponse = requestGetCartItems(member);

        Optional<CartItemResponse> selectedCartItemResponse = cartItemsResponse.jsonPath()
                .getList(".", CartItemResponse.class)
                .stream()
                .filter(cartItemResponse -> cartItemResponse.getId().equals(cartItemId))
                .findFirst();

        assertThat(selectedCartItemResponse.isPresent()).isFalse();
    }
}
