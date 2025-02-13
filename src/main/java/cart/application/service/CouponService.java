package cart.application.service;

import cart.application.dto.coupon.CreateCouponRequest;
import cart.application.dto.coupon.FindCouponsResponse;
import cart.application.repository.CouponRepository;
import cart.domain.coupon.Coupon;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CouponService {

    private final CouponRepository couponRepository;

    public CouponService(final CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public long createCoupon(final CreateCouponRequest request) {
        Coupon coupon = request.toDomain();
        return couponRepository.create(coupon);
    }

    @Transactional(readOnly = true)
    public FindCouponsResponse findAllCoupons() {
        List<Coupon> coupons = couponRepository.findAll();
        return FindCouponsResponse.from(coupons);
    }
}
