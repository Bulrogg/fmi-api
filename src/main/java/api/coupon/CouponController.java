package api.coupon;

import api.exception.CouponNotFoundException;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/coupons")
@Api(basePath = "/v1/coupons", value = "coupons", description = "Opération relatives à la gestion des coupons", produces = "application/json")
public class CouponController {

    private Map<Integer, Coupon> couponMap = new HashMap<>();

    private int nextCouponId = 1;

    public CouponController() {
        addCoupon("Reduction 1", "45 euros", true);
        addCoupon("Reduction 2", "5 euros", false);
        addCoupon("Reduction 3", "10 centimes", false);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Récupère les coupons", notes = "Permet de récupérer tous les coupons non utilisés")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK")
    })
    public List<Coupon> getAllCoupons() {
        log("getAllCoupons");
        simulerUneLatenceEntre200Et(700);
        return filtrerLesCouponsUtilises();
    }

    @RequestMapping(value = "/{couponId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Récupère un coupon", notes = "Permet de récupérer un coupon par son identifiant")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Coupon non trouvé"),
            @ApiResponse(code = 200, message = "OK")
    })
    public Coupon getCoupons(@PathVariable("couponId") int couponId) {
        log("getCoupon " + couponId);
        simulerUneLatenceEntre200Et(700);

        verifieIdCouponExiste(couponId);
        return couponMap.get(couponId);
    }

    // TODO : utiliser le @ResponsesStatus
    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Ajouter un coupon", notes = "Permet d'ajouter un coupon")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "CREATE")
    })
    public ResponseEntity<Coupon> addCoupon(@RequestBody Coupon coupon) {
        log("addCoupon " + coupon);
        simulerUneLatenceEntre200Et(500);

        Coupon couponAjoute = addCoupon(coupon.getNom(), coupon.getReduction(), coupon.getEstUtilise());
        return new ResponseEntity<>(couponAjoute, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{couponId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Supprimer un coupon", notes = "Permet de supprimer un coupon par son identifiant")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Coupon non trouvé"),
            @ApiResponse(code = 204, message = "NO CONTENT")
    })
    public void deleteCoupon(@PathVariable("couponId") int couponId) {
        log("deleteCoupon " + couponId);
        simulerUneLatenceEntre200Et(300);

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

    private void simulerUneLatenceEntre200Et(int nbMaxMs) {
        int tpsLatence = 200 + ((int) (Math.random() * (nbMaxMs - 200)));
        try {
            Thread.sleep(tpsLatence);
        } catch (InterruptedException e) {
        }
    }

    private List<Coupon> filtrerLesCouponsUtilises() {
        List<Coupon> coupons = new ArrayList<>(couponMap.values());
        List<Coupon> couponsFiltered = new ArrayList<>();
        for (Coupon coupon : coupons) {
            if (coupon.getEstUtilise() == false) {
                couponsFiltered.add(coupon);
            }
        }
        return couponsFiltered;
    }

}
