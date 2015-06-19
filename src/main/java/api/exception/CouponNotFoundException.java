package api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Coupon inexistant")
public class CouponNotFoundException extends RuntimeException {

    public CouponNotFoundException(long couponId) {
        super("Coupon d'id " + couponId + " n'a pas été trouvé");
    }
}