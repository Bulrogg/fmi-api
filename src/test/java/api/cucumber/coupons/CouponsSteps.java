package api.cucumber.coupons;

import api.BaseTest;
import api.coupon.Coupon;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class CouponsSteps extends BaseTest {

    private ResponseEntity<Coupon[]> reponseDuDernierGetAll;

    private ResponseEntity<Coupon> reponseDuDernierGetUnitaire;

    @Before
    public void beforeEachScenario() {
        reponseDuDernierGetUnitaire = null;
    }

    @Given("^je ne fais rien$")
    public void je_ne_fais_rien() throws Throwable {
        // rien à faire
    }

    @When("^j'appelle l'api pour recuperer le coupons \"([^\"]*)\"$")
    public void j_appelle_l_api_pour_recuperer_le_coupons(int idCoupon) throws Throwable {
        reponseDuDernierGetUnitaire = getCouponResponseEntity(idCoupon);
    }

    @Then("^je ne recois pas de coupon$")
    public void je_ne_recois_pas_de_coupon() throws Throwable {
        assertThat(reponseDuDernierGetUnitaire.getBody().getId(), nullValue());
        assertThat(reponseDuDernierGetUnitaire.getBody().getNom(), nullValue());
        assertThat(reponseDuDernierGetUnitaire.getBody().getReduction(), nullValue());
    }

    @And("^l'api me retourne bien un statut \"([^\"]*)\"$")
    public void l_api_me_retourne_bien_un_statut(int statut) throws Throwable {
        assertThat(reponseDuDernierGetUnitaire.getStatusCode().value(), is(statut));
    }

    @Given("^je crée le coupon non utilisé de nom \"([^\"]*)\" et de reduction \"([^\"]*)\"$")
    public void je_crée_le_coupon_non_utilise_de_nom_et_de_reduction(String nom, String reduction) throws Throwable {
        reponseDuDernierGetUnitaire = postCouponResponseEntity(nom, reduction, false);
    }

    @Given("^je crée le coupon utilisé de nom \"([^\"]*)\" et de reduction \"([^\"]*)\"$")
    public void je_crée_le_coupon_utilise_de_nom_et_de_reduction(String nom, String reduction) throws Throwable {
        reponseDuDernierGetUnitaire = postCouponResponseEntity(nom, reduction, true);
    }

    @When("^je recupere le dernier coupon que j'ai crée via l'api$")
    public void je_recupere_le_dernier_coupon_que_j_ai_crée_via_l_api() throws Throwable {
        reponseDuDernierGetUnitaire = getCouponResponseEntity(reponseDuDernierGetUnitaire.getBody().getId());
    }

    @And("^un coupon est bien retourné$")
    public void un_coupon_est_bien_retourné() throws Throwable {
        assertThat(reponseDuDernierGetUnitaire.getBody(), notNullValue());
    }

    @And("^son nom est bien \"([^\"]*)\"$")
    public void son_nom_est_bien(String nomAttendu) throws Throwable {
        assertThat(reponseDuDernierGetUnitaire.getBody().getNom(), is(nomAttendu));
    }

    @And("^sa réduction est bien \"([^\"]*)\"$")
    public void sa_réduction_est_bien(String reductionAttendu) throws Throwable {
        assertThat(reponseDuDernierGetUnitaire.getBody().getReduction(), is(reductionAttendu));
    }

    @And("^il est marqué comme utilisé$")
    public void il_est_marqué_comme_utilisé() throws Throwable {
        assertThat(reponseDuDernierGetUnitaire.getBody().getEstUtilise(), is(true));
    }

    @And("^il est marqué comme non utilisé$")
    public void il_est_marqué_comme_non_utilisé() throws Throwable {
        assertThat(reponseDuDernierGetUnitaire.getBody().getEstUtilise(), is(false));
    }

    @And("^son identifiant technique est correct$")
    public void son_identifiant_technique_est_correct() throws Throwable {
        assertThat(reponseDuDernierGetUnitaire.getBody().getId(), notNullValue());
        assertTrue(reponseDuDernierGetUnitaire.getBody().getId() > 0);
    }

    @When("^je supprime le dernier coupon que j'ai crée$")
    public void je_supprime_le_dernier_coupon_que_j_ai_crée() throws Throwable {
        deleteCouponByApi(reponseDuDernierGetUnitaire.getBody().getId());
    }

    @Then("^je ne peux plus récupérer le dernier coupons crée$")
    public void je_ne_peux_plus_récupérer_le_dernier_coupons_crée() throws Throwable {
        reponseDuDernierGetUnitaire = getCouponResponseEntity(reponseDuDernierGetUnitaire.getBody().getId());
        je_ne_recois_pas_de_coupon();
    }

    @And("^l'api m'a répondu en moins de \"([^\"]*)\" ms$")
    public void l_api_m_a_répondu_en_moins_de_ms(long tempsMax) throws Throwable {
        assertTrue(getNbMsPourDernierAppel() < tempsMax);
    }

    @When("^je récupère la liste des coupons$")
    public void je_récupère_la_liste_des_coupons() throws Throwable {
        reponseDuDernierGetAll = getAllCoupons();
    }

    @And("^le dernier coupon ajouté ne se trouve pas dans la liste de coupon$")
    public void le_dernier_coupon_ajouté_ne_se_trouve_pas_dans_la_liste_de_coupon() throws Throwable {
        verifieQueLaListeDeCouponsNeContientPasLeCoupon(reponseDuDernierGetUnitaire.getBody().getId(), Arrays.asList(reponseDuDernierGetAll.getBody()));
    }

    @And("^le dernier coupon ajouté se trouve bien dans la liste de coupon$")
    public void le_dernier_coupon_ajouté_se_trouve_bien_dans_la_liste_de_coupon() throws Throwable {
        verifieQueLaListeDeCouponsContientLeCoupon(reponseDuDernierGetUnitaire.getBody().getId(), Arrays.asList(reponseDuDernierGetAll.getBody()));
    }
}
