package api.disponibilite;

import api.BaseTest;
import api.coupon.Coupon;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import static org.hamcrest.CoreMatchers.is;

public class ApiUnitaireDisponibiliteTest extends BaseTest {

    @Test
    public void testGetAllCoupons() {
        ResponseEntity<Coupon[]> response = getAllCoupons();
        assertAndLogTempsDeReponse(response.getStatusCode(), HttpStatus.OK, "/v1/coupons/", "GET");
    }

    @Test
    public void testPostACoupons() {
        ResponseEntity<Coupon> response = postCouponResponseEntity("nom testPostACoupons", "reduction testPostACoupons", true);
        assertAndLogTempsDeReponse(response.getStatusCode(), HttpStatus.CREATED, "/v1/coupons/", "POST");
    }

    @Test
    public void testGetACoupons() {
        ResponseEntity<Coupon> postResponse = postCouponResponseEntity("nom testPostACoupons", "reduction testPostACoupons", true);
        ResponseEntity<Coupon> response = getCouponResponseEntity(postResponse.getBody().getId());
        assertAndLogTempsDeReponse(response.getStatusCode(), HttpStatus.OK, "/v1/coupons/{couponId}", "GET");
    }

    @Test
    public void testDeleteACoupons() {
        ResponseEntity<Coupon> postResponse = postCouponResponseEntity("nom testPostACoupons", "reduction testPostACoupons", true);
        ResponseEntity<String> response = deleteCouponResponseEntity(postResponse.getBody().getId());
        assertAndLogTempsDeReponse(response.getStatusCode(), HttpStatus.NO_CONTENT, "/v1/coupons/{couponId}", "DELETE");
    }

    private void assertAndLogTempsDeReponse(HttpStatus actual, HttpStatus expected, String endPoint, String verbe) {
        String tempsDeReponse = "(" + getNbMsPourDernierAppel() + "ms)";
        String suffixeLogEndpoint = endPoint + " [" + verbe + "] - " + tempsDeReponse;
        assertThat("KO - " + suffixeLogEndpoint, actual, is(expected));
    }

}
