package cart.application.repository;

import cart.domain.coupon.Coupon;
import java.util.Optional;

public interface CouponRepository {

    long create(Coupon coupon);

    Optional<Coupon> findById(long id);
}