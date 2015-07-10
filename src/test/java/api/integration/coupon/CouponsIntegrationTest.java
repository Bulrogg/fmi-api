package api.integration.coupon;

import api.BaseTest;
import api.coupon.Coupon;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class CouponsIntegrationTest extends BaseTest {

    protected final static long TEMPS_MAX_POUR_REPONSE = 1500;

    @Test
    public void une_erreur_404_est_bien_retournee_si_on_essaie_de_recuperer_un_coupon_inexistant() {
        int idCouponInexistant = -1;
        verifierCouponInexistantByApi(idCouponInexistant);
    }

    @Test
    public void scenario_on_peut_recuperer_unitairement_un_coupon_utilise() throws Exception {
        // Given
        String nomDuCouponAAjouter = "Le coupon du test scenario_on_peut_recuperer_unitairement_un_coupon_utilise";
        String reductionDuCouponAAjouter = "La réduction du coupon du test scenario_on_peut_recuperer_unitairement_un_coupon_utilise";
        Boolean estUtiliseDuCouponAAjouter = true;

        // When
        Coupon couponAjoute = postCouponByApi(nomDuCouponAAjouter, reductionDuCouponAAjouter, estUtiliseDuCouponAAjouter);

        Coupon couponPosteRecupere = getCouponByApi(couponAjoute.getId());

        // Then
        assertThat(couponAjoute.getNom(), is(nomDuCouponAAjouter));
        assertThat(couponAjoute.getReduction(), is(reductionDuCouponAAjouter));
        assertThat(couponAjoute.getEstUtilise(), is(estUtiliseDuCouponAAjouter));

        assertThat(couponPosteRecupere.getNom(), is(nomDuCouponAAjouter));
        assertThat(couponPosteRecupere.getReduction(), is(reductionDuCouponAAjouter));
        assertThat(couponPosteRecupere.getEstUtilise(), is(estUtiliseDuCouponAAjouter));
    }

    @Test
    public void scenario_on_peut_recuperer_unitairement_un_coupon_non_utilise() throws Exception {
        // Given
        String nomDuCouponAAjouter = "Le coupon du test scenario_on_peut_recuperer_unitairement_un_coupon_non_utilise";
        String reductionDuCouponAAjouter = "La réduction du coupon du test scenario_on_peut_recuperer_unitairement_un_coupon_non_utilise";
        Boolean estUtiliseDuCouponAAjouter = false;

        // When
        Coupon couponAjoute = postCouponByApi(nomDuCouponAAjouter, reductionDuCouponAAjouter, estUtiliseDuCouponAAjouter);

        Coupon couponPosteRecupere = getCouponByApi(couponAjoute.getId());

        // Then
        assertThat(couponAjoute.getNom(), is(nomDuCouponAAjouter));
        assertThat(couponAjoute.getReduction(), is(reductionDuCouponAAjouter));
        assertThat(couponAjoute.getEstUtilise(), is(estUtiliseDuCouponAAjouter));

        assertThat(couponPosteRecupere.getNom(), is(nomDuCouponAAjouter));
        assertThat(couponPosteRecupere.getReduction(), is(reductionDuCouponAAjouter));
        assertThat(couponPosteRecupere.getEstUtilise(), is(estUtiliseDuCouponAAjouter));
    }

    @Test
    public void scenario_ajout_recupere_supprime_essaieDeRecuperer() throws Exception {
        // Given
        String nomDuCouponAAjouter = "Le coupon du test";
        String reductionDuCouponAAjouter = "La réduction du coupon du test";
        Boolean estUtiliseDuCouponAAjouter = false;

        // When
        Coupon couponAjoute = postCouponByApi(nomDuCouponAAjouter, reductionDuCouponAAjouter, estUtiliseDuCouponAAjouter);

        Coupon couponPosteRecupere = getCouponByApi(couponAjoute.getId());

        deleteACouponByApi(couponAjoute.getId());

        verifierCouponInexistantByApi(couponAjoute.getId());

        // Then
        assertThat(couponAjoute.getNom(), is(nomDuCouponAAjouter));
        assertThat(couponAjoute.getReduction(), is(reductionDuCouponAAjouter));
        assertThat(couponAjoute.getEstUtilise(), is(estUtiliseDuCouponAAjouter));

        assertThat(couponPosteRecupere.getNom(), is(nomDuCouponAAjouter));
        assertThat(couponPosteRecupere.getReduction(), is(reductionDuCouponAAjouter));
    }

    @Test
    public void lorsque_l_on_recupere_tous_les_coupons_les_coupons_utilises_sont_filtres() throws Exception {
        // Given
        Coupon couponUtiliseAjoute = postCouponByApi("nom test coupon utilisé", "reduction test coupon utilisé", true);
        Coupon couponNonUtiliseAjoute = postCouponByApi("nom test coupon non utilisé", "reduction test coupon non utilisé", false);

        // When
        List<Coupon> couponsRetournes = getAllCouponsByApi();

        // Then
        verifieQueLaListeDeCouponsContientLeCoupon(couponNonUtiliseAjoute.getId(), couponsRetournes);
        verifieQueLaListeDeCouponsNeContientPasLeCoupon(couponUtiliseAjoute.getId(), couponsRetournes);
    }

    private Coupon getCouponByApi(int idCoupon) {
        // Given

        // When
        ResponseEntity<Coupon> response = getCouponResponseEntity(idCoupon);

        // Then
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertTrue(getNbMsPourDernierAppel() < TEMPS_MAX_POUR_REPONSE);
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
        assertTrue("temps de réponse trop long", getNbMsPourDernierAppel() < TEMPS_MAX_POUR_REPONSE);
    }

    private Coupon postCouponByApi(String nom, String reduction, Boolean estUtilise) {
        // Given

        // When
        ResponseEntity<Coupon> response = postCouponResponseEntity(nom, reduction, estUtilise);

        // Then
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        assertThat(response.getBody().getId(), notNullValue());
        assertTrue("temps de réponse trop long", getNbMsPourDernierAppel() < TEMPS_MAX_POUR_REPONSE);

        return response.getBody();
    }

    private List<Coupon> getAllCouponsByApi() {
        // Given

        // When
        ResponseEntity<Coupon[]> response = getAllCoupons();

        // Then
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), notNullValue());
        assertTrue("temps de réponse trop long", getNbMsPourDernierAppel() < TEMPS_MAX_POUR_REPONSE);

        return Arrays.asList(response.getBody());
    }

    private void deleteACouponByApi(int idCoupon) {
        // Given

        // When
        ResponseEntity<String> response = deleteCouponResponseEntity(idCoupon);

        // Then
        assertThat(response.getStatusCode(), is(HttpStatus.NO_CONTENT));
        assertTrue("temps de réponse trop long", getNbMsPourDernierAppel() < TEMPS_MAX_POUR_REPONSE);
    }

}
