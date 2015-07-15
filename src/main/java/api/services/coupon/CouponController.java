package api.services.coupon;

import api.data.CouponData;
import api.data.DataManager;
import api.services.BaseMockController;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/coupons")
@Api(basePath = "/v1/coupons", value = "coupons", description = "Opération relatives à la gestion des coupons", produces = "application/json")
public class CouponController extends BaseMockController {

    @Autowired
    private DataManager dataManager;

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Récupère les coupons", notes = "Permet de récupérer tous les coupons non utilisés")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK")
    })
    public List<Coupon> getAllCoupons() {
        log("getAllCoupons");
        simulerUneLatenceEntre200Et(700);
        List<CouponData> couponsDataFiltres = dataManager.getListeCouponNonUtilises();
        List<Coupon> couponsFiltres = new ArrayList<>();
        for (CouponData couponData : couponsDataFiltres) {
            couponsFiltres.add(new Coupon(couponData));
        }
        return couponsFiltres;
    }

    @RequestMapping(value = "/{couponId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Récupère un coupon", notes = "Permet de récupérer un coupon par son identifiant")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Coupon non trouvé"),
            @ApiResponse(code = 200, message = "OK")
    })
    public Coupon getCoupon(@PathVariable("couponId") int couponId) {
        log("getCoupon " + couponId);
        simulerUneLatenceEntre200Et(700);
        if(!dataManager.verifieIdCouponExiste(couponId)) {
            throw new CouponNotFoundException(couponId);
        }
        return new Coupon(dataManager.getCoupon(couponId));
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
        Coupon couponAjoute = new Coupon(dataManager.addCoupon(coupon.getNom(), coupon.getReduction(), coupon.getEstUtilise()));
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
        if(!dataManager.verifieIdCouponExiste(couponId)) {
            throw new CouponNotFoundException(couponId);
        }
        dataManager.supprimerCoupon(couponId);
    }

}
