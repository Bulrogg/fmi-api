package api;

import api.coupon.Coupon;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class BaseTest {

    private final static String BASE_URL = "http://localhost:8080/";

    protected final static String URL_DELETE_COUPON = BASE_URL + "v1/coupons/";
    protected final static String URL_GET_COUPON = BASE_URL + "v1/coupons/";
    protected final static String URL_POST_COUPON = BASE_URL + "v1/coupons/";

    protected RestTemplate template = new TestRestTemplate();

    protected void deleteCouponByApi(int idCoupon) {
        template.delete(URL_DELETE_COUPON + idCoupon);
    }

    protected ResponseEntity<Coupon> getCouponResponseEntity(int idCoupon) {
        return template.getForEntity(URL_GET_COUPON + idCoupon, Coupon.class);
    }

    protected ResponseEntity<Coupon> postCouponResponseEntity(String nom, String reduction) {
        return template.postForEntity(URL_POST_COUPON, new Coupon(nom, reduction), Coupon.class);
    }
}
