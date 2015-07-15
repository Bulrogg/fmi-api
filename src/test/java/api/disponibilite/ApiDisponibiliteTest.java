package api.disponibilite;

import api.BaseTest;
import api.services.coupon.Coupon;

import static junit.framework.Assert.*;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class ApiDisponibiliteTest extends BaseTest {

    private int nbEndpointEnErreur = 0;

    @Test
    public void testEndPoint() {
        List<String> rapportDeTest = new ArrayList<>();
        rapportDeTest.add("Rapport de disponibilité des endpoints");
        rapportDeTest.add(testGetAllCoupons());
        rapportDeTest.add(testPostACoupons());
        rapportDeTest.add(testGetACoupons());
        rapportDeTest.add(testDeleteACoupons());
        for (String rapport : rapportDeTest) {
            logger(rapport);
        }
        assertEquals("Existance de " + nbEndpointEnErreur + " endPoint(s) non disponible", 0, nbEndpointEnErreur);
    }

    private String testGetAllCoupons() {
        ResponseEntity<Coupon[]> response = getAllCoupons();
        return assertAndLog(response.getStatusCode(), HttpStatus.OK, "/v1/coupons/", "GET");
    }

    private String testPostACoupons() {
        ResponseEntity<Coupon> response = postCouponResponseEntity("nom testPostACoupons", "reduction testPostACoupons", true);
        return assertAndLog(response.getStatusCode(), HttpStatus.CREATED, "/v1/coupons/", "POST");
    }

    private String testGetACoupons() {
        ResponseEntity<Coupon> postResponse = postCouponResponseEntity("nom testPostACoupons", "reduction testPostACoupons", true);
        ResponseEntity<Coupon> response = getCouponResponseEntity(postResponse.getBody().getId());
        return assertAndLog(response.getStatusCode(), HttpStatus.OK, "/v1/coupons/{couponId}", "GET");
    }

    private String testDeleteACoupons() {
        ResponseEntity<Coupon> postResponse = postCouponResponseEntity("nom testPostACoupons", "reduction testPostACoupons", true);
        ResponseEntity<String> response = deleteCouponResponseEntity(postResponse.getBody().getId());
        return assertAndLog(response.getStatusCode(), HttpStatus.NO_CONTENT, "/v1/coupons/{couponId}", "DELETE");
    }

    private String assertAndLog(HttpStatus actual, HttpStatus expected, String endPoint, String verbe) {
        String suffixe = endPoint + " [" + verbe + "]";
        String prefix = "OK";
        if (!expected.equals(actual)) {
            prefix = "KO";
            nbEndpointEnErreur++;
        }
        return prefix + " - " + suffixe;
    }

    private void logger(String str) {
        System.out.println("------------> " + str);
    }

}
