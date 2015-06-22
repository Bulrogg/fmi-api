package api.coupon;

import api.exception.CouponNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/coupons")
public class CouponController {

    private final static long TEMPS_AVANT_DE_REPONDRE = 100;

    private Map<Integer, Coupon> couponMap = new HashMap<>();
    private int nextCouponId = 1;

    public CouponController() {
        addCoupon("Reduction 1", "45 euros", true);
        addCoupon("Reduction 2", "5 euros", false);
        addCoupon("Reduction 3", "10 centimes", false);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, produces="application/json")
    public List<Coupon> getAllCoupons() {
        log("getAllCoupons");
        simulerTempsDeCalcul();

        return new ArrayList<>(couponMap.values());
    }

    @RequestMapping(value = "/{couponId}", method = RequestMethod.GET, produces="application/json")
    public Coupon getCoupons(@PathVariable("couponId") int couponId) {
        log("getCoupon " + couponId);
        simulerTempsDeCalcul();

        verifieIdCouponExiste(couponId);
        return couponMap.get(couponId);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes="application/json")
    public ResponseEntity<Coupon> addCoupon(@RequestBody Coupon coupon) {
        log("addCoupon " + coupon);
        simulerTempsDeCalcul();

        Coupon couponAjoute = addCoupon(coupon.getNom(), coupon.getReduction(), coupon.getEstUtilise());
        return new ResponseEntity<>(couponAjoute, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{couponId}", method = RequestMethod.DELETE)
    public void deleteCoupon(@PathVariable("couponId") int couponId) {
        log("deleteCoupon " + couponId);
        simulerTempsDeCalcul();

        verifieIdCouponExiste(couponId);
        couponMap.remove(couponId);
    }

    private void verifieIdCouponExiste(Integer couponId) {
        if (!couponMap.containsKey(couponId)) {
            throw new CouponNotFoundException(couponId);
        }
    }

    private Coupon addCoupon(String nom, String reduction, Boolean estUtilise) {
        Coupon coupon = new Coupon(nextCouponId, nom, reduction, estUtilise);
        this.couponMap.put(coupon.getId(), coupon);
        log("Coupon ajouté : " + coupon.toString());

        nextCouponId++;
        log("nextCoupon = " + nextCouponId);
        return coupon;
    }

    private void log(String str) {
        System.out.println("----------------> " + str);
    }

    private void simulerTempsDeCalcul() {
        try {
            Thread.sleep(TEMPS_AVANT_DE_REPONDRE);
        } catch (InterruptedException e) {
        }
    }

}
