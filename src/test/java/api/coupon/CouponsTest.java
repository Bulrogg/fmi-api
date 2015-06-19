package api.coupon;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class CouponsTest {

    private final static String BASE_URL = "http://localhost:8080/";

    private final static String URL_DELETE_COUPON = BASE_URL + "v1/coupons/";
    private final static String URL_GET_COUPON = BASE_URL + "v1/coupons/";
    private final static String URL_POST_COUPON = BASE_URL + "v1/coupons/";

    RestTemplate template = new TestRestTemplate();

    @Test
    public void getCoupon_retourne_404_si_inconnu() {
        int idCouponInexistant = -1;
        verifierCouponInexistantByApi(idCouponInexistant);
    }

    @Test
    public void scenario_ajout_recupere_supprime_essaieDeRecuperer() throws Exception {
        // Given
        String nomDuCouponAAjouter = "Le coupon du test";
        String reductionDuCouponAAjouter = "La réduction du coupon du test";

        // When
        Coupon couponAjoute = postCouponByApi(nomDuCouponAAjouter, reductionDuCouponAAjouter);

        Coupon couponPosteRecupere = getCouponByApi(couponAjoute.getId());

        deleteCouponByApi(couponAjoute.getId());

        verifierCouponInexistantByApi(couponAjoute.getId());

        // Then
        assertThat(couponAjoute.getNom(), is(nomDuCouponAAjouter));
        assertThat(couponAjoute.getReduction(), is(reductionDuCouponAAjouter));

        assertThat(couponPosteRecupere.getNom(), is(nomDuCouponAAjouter));
        assertThat(couponPosteRecupere.getReduction(), is(reductionDuCouponAAjouter));
    }

    private Coupon getCouponByApi(int idCoupon) {
        // Given

        // When
        ResponseEntity<Coupon> response = template.getForEntity(URL_GET_COUPON + idCoupon, Coupon.class);

        // Then
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        Coupon coupon = response.getBody();
        assertThat(coupon.getId(), is(idCoupon));

        return coupon;
    }

    private void verifierCouponInexistantByApi(int idCoupon) {
        // Given

        // When
        ResponseEntity<Coupon> response = template.getForEntity(URL_GET_COUPON + idCoupon, Coupon.class);

        // Then
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    private Coupon postCouponByApi(String nom, String reduction) {
        // Given
        Coupon couponAAjouter = new Coupon(nom, reduction);

        // When
        ResponseEntity<Coupon> response = template.postForEntity(URL_POST_COUPON, couponAAjouter, Coupon.class);

        // Then
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        assertThat(response.getBody().getId(), notNullValue());

        return response.getBody();
    }

    private void deleteCouponByApi(int idCoupon) {
        template.delete(URL_DELETE_COUPON + idCoupon);
    }

}
