package api.cucumber.coupons;

import api.BaseTest;
import api.coupon.Coupon;
import cucumber.api.PendingException;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class CouponsSteps extends BaseTest {

    private ResponseEntity<Coupon> reponseDuDernierAppel;

    @Before
    public void beforeEachScenario() {
        reponseDuDernierAppel = null;
    }

    @Given("^je ne fais rien$")
    public void je_ne_fais_rien() throws Throwable {
        // rien à faire
    }

    @When("^j'appelle l'api pour recuperer le coupons \"([^\"]*)\"$")
    public void j_appelle_l_api_pour_recuperer_le_coupons(int idCoupon) throws Throwable {
        reponseDuDernierAppel = getCouponResponseEntity(idCoupon);
    }

    @Then("^je ne recois pas de coupon$")
    public void je_ne_recois_pas_de_coupon() throws Throwable {
        assertThat(reponseDuDernierAppel.getBody().getId(), nullValue());
        assertThat(reponseDuDernierAppel.getBody().getNom(), nullValue());
        assertThat(reponseDuDernierAppel.getBody().getReduction(), nullValue());
    }

    @And("^l'api me retourne bien un statut \"([^\"]*)\"$")
    public void l_api_me_retourne_bien_un_statut(int statut) throws Throwable {
        assertThat(reponseDuDernierAppel.getStatusCode().value(), is(statut));
    }

    @Given("^je crée le coupon non utilisé de nom \"([^\"]*)\" et de reduction \"([^\"]*)\"$")
    public void je_crée_le_coupon_non_utilise_de_nom_et_de_reduction(String nom, String reduction) throws Throwable {
        reponseDuDernierAppel = postCouponResponseEntity(nom, reduction, false);
    }

    @Given("^je crée le coupon utilisé de nom \"([^\"]*)\" et de reduction \"([^\"]*)\"$")
    public void je_crée_le_coupon_utilise_de_nom_et_de_reduction(String nom, String reduction) throws Throwable {
        reponseDuDernierAppel = postCouponResponseEntity(nom, reduction, true);
    }

    @When("^je recupere le dernier coupon que j'ai crée via l'api$")
    public void je_recupere_le_dernier_coupon_que_j_ai_crée_via_l_api() throws Throwable {
        reponseDuDernierAppel = getCouponResponseEntity(reponseDuDernierAppel.getBody().getId());
    }

    @And("^un coupon est bien retourné$")
    public void un_coupon_est_bien_retourné() throws Throwable {
        assertThat(reponseDuDernierAppel.getBody(), notNullValue());
    }

    @And("^son nom est bien \"([^\"]*)\"$")
    public void son_nom_est_bien(String nomAttendu) throws Throwable {
        assertThat(reponseDuDernierAppel.getBody().getNom(), is(nomAttendu));
    }

    @And("^sa réduction est bien \"([^\"]*)\"$")
    public void sa_réduction_est_bien(String reductionAttendu) throws Throwable {
        assertThat(reponseDuDernierAppel.getBody().getReduction(), is(reductionAttendu));
    }

    @And("^il est marqué comme utilisé$")
    public void il_est_marqué_comme_utilisé() throws Throwable {
        assertThat(reponseDuDernierAppel.getBody().getEstUtilise(), is(true));
    }

    @And("^il est marqué comme non utilisé$")
    public void il_est_marqué_comme_non_utilisé() throws Throwable {
        assertThat(reponseDuDernierAppel.getBody().getEstUtilise(), is(false));
    }

    @And("^son identifiant technique est correct$")
    public void son_identifiant_technique_est_correct() throws Throwable {
        assertThat(reponseDuDernierAppel.getBody().getId(), notNullValue());
        assertTrue(reponseDuDernierAppel.getBody().getId() > 0);
    }

    @When("^je supprime le dernier coupon que j'ai crée$")
    public void je_supprime_le_dernier_coupon_que_j_ai_crée() throws Throwable {
        deleteCouponByApi(reponseDuDernierAppel.getBody().getId());
    }

    @Then("^je ne peux plus récupérer le dernier coupons crée$")
    public void je_ne_peux_plus_récupérer_le_dernier_coupons_crée() throws Throwable {
        reponseDuDernierAppel = getCouponResponseEntity(reponseDuDernierAppel.getBody().getId());
        je_ne_recois_pas_de_coupon();
    }

    @And("^l'api m'a répondu en moins de \"([^\"]*)\" ms$")
    public void l_api_m_a_répondu_en_moins_de_ms(long tempsMax) throws Throwable {
        assertTrue(getNbMsPourDernierAppel() < tempsMax);
    }

}
