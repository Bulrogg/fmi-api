package api;

import api.coupon.Coupon;
import org.joda.time.DateTime;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.Assert.fail;

public class BaseTest {

    private final static String BASE_URL = "http://localhost:8080/";

    protected final static String URL_DELETE_COUPON = BASE_URL + "v1/coupons/";
    protected final static String URL_ALL_COUPONS = BASE_URL + "v1/coupons/";
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

    protected ResponseEntity<Coupon[]> getAllCoupons() {
        dateJusteAvantLeDernierAppel = DateTime.now();
        ResponseEntity<Coupon[]> response = template.getForEntity(URL_ALL_COUPONS, Coupon[].class);
        dateJusteApresLeDernierAppel = DateTime.now();
        return response;
    }

    protected void verifieQueLaListeDeCouponsContientLeCoupon(Integer idCouponRecherche, List<Coupon> coupons) {
        for(Coupon coupon : coupons) {
            if(coupon.getId().equals(idCouponRecherche)) {
                return;
            }
        }
        fail("Le coupon d'identifiant " + idCouponRecherche + " n'a pas été trouvé");
    }

    protected void verifieQueLaListeDeCouponsNeContientPasLeCoupon(Integer idCouponRecherche, List<Coupon> coupons) {
        for(Coupon coupon : coupons) {
            if(coupon.getId().equals(idCouponRecherche)) {
                fail("Le coupon d'identifiant " + idCouponRecherche + " a été trouvé");
            }
        }
    }
}
