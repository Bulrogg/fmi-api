package api;

import api.services.coupon.Coupon;
import org.joda.time.DateTime;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.fail;

public class BaseTest {

    protected RestTemplate template = new TestRestTemplate();

    private final static String ENDPOINT_COUPONS = "/v1/coupons";

    private DateTime dateJusteAvantLeDernierAppel;
    private DateTime dateJusteApresLeDernierAppel;

    private Properties testProperties;

    public BaseTest() {
        Resource resource = new ClassPathResource("/test.properties");
        try {
            testProperties = PropertiesLoaderUtils.loadProperties(resource);
        } catch (IOException e) {
            System.out.println("erreur : " + e.getMessage());
        }

    }

    private String getBaseUrl() {
        return testProperties.getProperty("test.api.base.url");
    }

    protected ResponseEntity<String> deleteCouponResponseEntity(int idCoupon) {
        dateJusteAvantLeDernierAppel = DateTime.now();
        String url = getBaseUrl() + ENDPOINT_COUPONS + "/" + idCoupon;
        ResponseEntity<String> response = template.exchange(url, HttpMethod.DELETE, null, String.class);
        dateJusteApresLeDernierAppel = DateTime.now();
        return response;
    }

    protected ResponseEntity<Coupon> getCouponResponseEntity(int idCoupon) {
        dateJusteAvantLeDernierAppel = DateTime.now();
        String url = getBaseUrl() + ENDPOINT_COUPONS + "/" +  + idCoupon;
        ResponseEntity<Coupon> response = template.getForEntity(url, Coupon.class);
        dateJusteApresLeDernierAppel = DateTime.now();
        return response;
    }

    protected ResponseEntity<Coupon> postCouponResponseEntity(String nom, String reduction, Boolean estUtilise) {
        dateJusteAvantLeDernierAppel = DateTime.now();
        String url = getBaseUrl() + ENDPOINT_COUPONS;
        ResponseEntity<Coupon> response = template.postForEntity(url, new Coupon(nom, reduction, estUtilise), Coupon.class);
        dateJusteApresLeDernierAppel = DateTime.now();
        return response;
    }

    protected ResponseEntity<Coupon[]> getAllCoupons() {
        dateJusteAvantLeDernierAppel = DateTime.now();
        String url = getBaseUrl() + ENDPOINT_COUPONS;
        ResponseEntity<Coupon[]> response = template.getForEntity(url, Coupon[].class);
        dateJusteApresLeDernierAppel = DateTime.now();
        return response;
    }

    protected void verifieQueLaListeDeCouponsContientLeCoupon(Integer idCouponRecherche, List<Coupon> coupons) {
        for (Coupon coupon : coupons) {
            if (coupon.getId().equals(idCouponRecherche)) {
                return;
            }
        }
        fail("Le coupon d'identifiant " + idCouponRecherche + " n'a pas été trouvé");
    }

    protected void verifieQueLaListeDeCouponsNeContientPasLeCoupon(Integer idCouponRecherche, List<Coupon> coupons) {
        for (Coupon coupon : coupons) {
            if (coupon.getId().equals(idCouponRecherche)) {
                fail("Le coupon d'identifiant " + idCouponRecherche + " a été trouvé");
            }
        }
    }

    protected long getNbMsPourDernierAppel() {
        return dateJusteApresLeDernierAppel.getMillis() - dateJusteAvantLeDernierAppel.getMillis();
    }

}
