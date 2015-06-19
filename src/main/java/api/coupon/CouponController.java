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

    private Map<Integer, Coupon> couponMap = new HashMap<>();
    private int nextCouponId = 1;

    public CouponController() {
        addCoupon("Reduction 1", "45 euros");
        addCoupon("Reduction 2", "5 euros");
        addCoupon("Reduction 3", "10 centimes");
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, produces="application/json")
    public List<Coupon> getAllCoupons() {
        log("getAllCoupons");

        return new ArrayList<>(couponMap.values());
    }

    @RequestMapping(value = "/{couponId}", method = RequestMethod.GET, produces="application/json")
    public Coupon getCoupons(@PathVariable("couponId") int couponId) {
        log("getCoupon " + couponId);

        verifieIdCouponExiste(couponId);
        return couponMap.get(couponId);
    }

    // curl -H "Content-Type: application/json" -X POST -d '{"nom":"reduction 4","reduction":"1000 euros"}' http://localhost:8080/v1/coupons/
    @RequestMapping(value = "/", method = RequestMethod.POST, consumes="application/json")
    public ResponseEntity<Coupon> addCoupon(@RequestBody Coupon coupon) {
        log("addCoupon " + coupon);
        Coupon couponAjoute = addCoupon(coupon.getNom(), coupon.getReduction());
        return new ResponseEntity<>(couponAjoute, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{couponId}", method = RequestMethod.DELETE)
    public void deleteCoupon(@PathVariable("couponId") int couponId) {
        log("deleteCoupon " + couponId);
        verifieIdCouponExiste(couponId);
        couponMap.remove(couponId);
    }

    private void verifieIdCouponExiste(Integer couponId) {
        if (!couponMap.containsKey(couponId)) {
            throw new CouponNotFoundException(couponId);
        }
    }

    private Coupon addCoupon(String nom, String reduction) {
        Coupon coupon = new Coupon(nextCouponId, nom, reduction);
        this.couponMap.put(coupon.getId(), coupon);
        log("Coupon ajouté : " + coupon.toString());

        nextCouponId++;
        log("nextCoupon = " + nextCouponId);

        return coupon;
    }

    private void log(String str) {
        System.out.println("----------------> " + str);
    }

}
