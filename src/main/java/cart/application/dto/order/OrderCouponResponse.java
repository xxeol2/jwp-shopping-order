package cart.application.dto.order;

import cart.domain.cart.CartItems;
import cart.domain.coupon.Coupon;
import cart.domain.coupon.CouponInfo;
import cart.domain.coupon.MemberCoupon;
import java.time.LocalDateTime;

public class OrderCouponResponse {

    private long id;
    private String name;
    private int minOrderPrice;
    private int maxDiscountPrice;
    private boolean isAvailable;
    private Integer discountPrice;
    private LocalDateTime expiredAt;

    private OrderCouponResponse() {
    }

    public OrderCouponResponse(final long id, final String name, final int minOrderPrice,
            final int maxDiscountPrice, final boolean isAvailable,
            final Integer discountPrice, final LocalDateTime expiredAt) {
        this.id = id;
        this.name = name;
        this.minOrderPrice = minOrderPrice;
        this.maxDiscountPrice = maxDiscountPrice;
        this.isAvailable = isAvailable;
        this.discountPrice = discountPrice;
        this.expiredAt = expiredAt;
    }

    public static OrderCouponResponse from(final MemberCoupon memberCoupon, final CartItems cartItems) {
        Coupon coupon = memberCoupon.getCoupon();
        CouponInfo couponInfo = coupon.getCouponInfo();
        return new OrderCouponResponse(
                memberCoupon.getId(),
                couponInfo.getName(),
                couponInfo.getMinOrderPrice(),
                couponInfo.getMaxDiscountPrice(),
                memberCoupon.isApplicable(cartItems),
                memberCoupon.getDiscountPrice(cartItems).orElse(null),
                memberCoupon.getExpiredAt());
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getMinOrderPrice() {
        return minOrderPrice;
    }

    public int getMaxDiscountPrice() {
        return maxDiscountPrice;
    }

    public boolean getIsAvailable() {
        return isAvailable;
    }

    public Integer getDiscountPrice() {
        return discountPrice;
    }

    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }
}
