package cart.domain.cart;

import cart.domain.Member;
import cart.domain.Product;
import cart.exception.forbidden.ForbiddenException;
import java.util.Objects;

public class CartItem {

    private static final int DEFAULT_QUANTITY = 1;

    private Long id;
    private int quantity;
    private final Product product;
    private final Member member;

    public CartItem(final Member member, final Product product) {
        this(null, DEFAULT_QUANTITY, product, member);

    }

    public CartItem(Long id, int quantity, Product product, Member member) {
        this.id = id;
        this.quantity = quantity;
        this.product = product;
        this.member = member;
    }

    public void checkOwner(Member member) {
        if (!Objects.equals(this.member.getId(), member.getId())) {
            throw new ForbiddenException();
        }
    }

    public void changeQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean hasQuantity(final int quantity) {
        return this.quantity == quantity;
    }

    public boolean hasPrice(final int price) {
        return product.hasPrice(price);
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public int calculateTotalProductPrice() {
        return product.getPrice() * quantity;
    }
}
