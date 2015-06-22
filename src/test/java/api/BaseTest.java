package api;

import api.coupon.Coupon;
import org.joda.time.DateTime;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class BaseTest {

    private final static String BASE_URL = "http://localhost:8080/";

    protected final static String URL_DELETE_COUPON = BASE_URL + "v1/coupons/";
    protected final static String URL_GET_COUPON = BASE_URL + "v1/coupons/";
    protected final static String URL_POST_COUPON = BASE_URL + "v1/coupons/";

    protected RestTemplate template = new TestRestTemplate();

    private DateTime dateJusteAvantLeDernierAppel;
    private DateTime dateJusteApresLeDernierAppel;

    protected long getNbMsPourDernierAppel() {
        return dateJusteApresLeDernierAppel.getMillis() - dateJusteAvantLeDernierAppel.getMillis();
    }

    protected void deleteCouponByApi(int idCoupon) {
        dateJusteAvantLeDernierAppel = DateTime.now();
        template.delete(URL_DELETE_COUPON + idCoupon);
        dateJusteApresLeDernierAppel = DateTime.now();
    }

    protected ResponseEntity<Coupon> getCouponResponseEntity(int idCoupon) {
        dateJusteAvantLeDernierAppel = DateTime.now();
        ResponseEntity<Coupon> response = template.getForEntity(URL_GET_COUPON + idCoupon, Coupon.class);
        dateJusteApresLeDernierAppel = DateTime.now();
        return response;
    }

    protected ResponseEntity<Coupon> postCouponResponseEntity(String nom, String reduction, Boolean estUtilise) {
        dateJusteAvantLeDernierAppel = DateTime.now();
        ResponseEntity<Coupon> response = template.postForEntity(URL_POST_COUPON, new Coupon(nom, reduction, estUtilise), Coupon.class);
        dateJusteApresLeDernierAppel = DateTime.now();
        return response;
    }
}
