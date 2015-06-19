package api.coupon.integration.coupon;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import api.BaseTest;
import api.coupon.Coupon;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CouponsIntegrationTest extends BaseTest {

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
        ResponseEntity<Coupon> response = getCouponResponseEntity(idCoupon);

        // Then
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        Coupon coupon = response.getBody();
        assertThat(coupon.getId(), is(idCoupon));

        return coupon;
    }

    private void verifierCouponInexistantByApi(int idCoupon) {
        // Given

        // When
        ResponseEntity<Coupon> response = getCouponResponseEntity(idCoupon);

        // Then
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    private Coupon postCouponByApi(String nom, String reduction) {
        // Given

        // When
        ResponseEntity<Coupon> response = postCouponResponseEntity(nom, reduction);

        // Then
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        assertThat(response.getBody().getId(), notNullValue());

        return response.getBody();
    }

}
