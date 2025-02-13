package cart.persistence.entity;

import cart.domain.Product;

public class ProductEntity {

    private Long id;
    private String name;
    private int price;
    private String imageUrl;

    public ProductEntity(final String name, final int price, final String imageUrl) {
        this(null, name, price, imageUrl);
    }

    public ProductEntity(final Long id, final String name, final int price, final String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public static ProductEntity from(final Product product) {
        return new ProductEntity(product.getId(), product.getName(), product.getPrice(), product.getImageUrl());
    }

    public Product toDomain() {
        return new Product(id, name, price, imageUrl);

    }

    public Long getId() {
        return id;
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
}
