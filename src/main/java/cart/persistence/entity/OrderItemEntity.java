package cart.persistence.entity;

import cart.domain.Product;
import cart.domain.order.OrderItem;

public class OrderItemEntity {

    private final Long id;
    private final long orderId;
    private final long productId;
    private final String name;
    private final int price;
    private final String imageUrl;
    private final int quantity;

    public OrderItemEntity(final long orderId, final long productId, final String name, final int price,
            final String imageUrl, final int quantity) {
        this(null, orderId, productId, name, price, imageUrl, quantity);
    }

    public OrderItemEntity(final Long id, final long orderId, final long productId, final String name, final int price,
            final String imageUrl, final int quantity) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
    }

    public static OrderItemEntity of(final OrderItem orderItem, final long orderId) {
        Product product = orderItem.getProduct();
        return new OrderItemEntity(orderItem.getId(), orderId, product.getId(), product.getName(), product.getPrice(),
                product.getImageUrl(), orderItem.getQuantity());
    }

    public OrderItem toDomain() {
        Product product = new Product(productId, name, price, imageUrl);
        return new OrderItem(id, product, quantity);
    }

    public Long getId() {
        return id;
    }

    public long getOrderId() {
        return orderId;
    }

    public long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getQuantity() {
        return quantity;
    }
}
