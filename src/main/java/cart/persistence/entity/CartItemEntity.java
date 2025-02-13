package cart.persistence.entity;

import cart.domain.cart.CartItem;

public class CartItemEntity {

    private Long id;
    private long memberId;
    private long productId;
    private int quantity;

    public CartItemEntity(final long memberId, final long productId, final int quantity) {
        this(null, memberId, productId, quantity);
    }

    public CartItemEntity(final Long id, final long memberId, final long productId, final int quantity) {
        this.id = id;
        this.memberId = memberId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public static CartItemEntity from(final CartItem cartItem) {
        return new CartItemEntity(
                cartItem.getId(),
                cartItem.getMember().getId(),
                cartItem.getProduct().getId(),
                cartItem.getQuantity()
        );
    }

    public Long getId() {
        return id;
    }

    public long getMemberId() {
        return memberId;
    }

    public long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
