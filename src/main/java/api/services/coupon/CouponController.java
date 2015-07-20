package api.services.coupon;

import api.data.CouponData;
import api.data.DataManager;
import api.services.BaseMockController;
import com.wordnik.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(CouponController.class);

    @Autowired
    private DataManager dataManager;

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Récupère les coupons", notes = "Permet de récupérer tous les coupons non utilisés", response = Coupon.class, responseContainer="List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK - La liste de coupons est retournée au format JSON dans le response body"),
            @ApiResponse(code = 500, message = "Si une erreur se produit")
    })
    public List<Coupon> getAllCoupons() {
        LOGGER.info("IN - getAllCoupons");
        simulerUneLatenceEntre200Et(700);
        List<CouponData> couponsDataFiltres = dataManager.getListeCouponNonUtilises();
        List<Coupon> couponsFiltres = new ArrayList<>();
        for (CouponData couponData : couponsDataFiltres) {
            couponsFiltres.add(new Coupon(couponData));
        }
        LOGGER.info("OUT - getAllCoupons => {} ", couponsFiltres);
        return couponsFiltres;
    }

    @RequestMapping(value = "/{couponId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Récupère un coupon", notes = "Permet de récupérer un coupon par son identifiant")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK - Le coupon est retourné au format JSON dans le response body", response = Coupon.class),
            @ApiResponse(code = 404, message = "Coupon non trouvé"),
            @ApiResponse(code = 500, message = "Si une erreur se produit")
    })
    public Coupon getCoupon(
            @ApiParam(value = "Identifiant du coupon à récupérer", required = true) @PathVariable("couponId") int couponId
    ) {
        LOGGER.info("IN - getCoupon {}", couponId);
        simulerUneLatenceEntre200Et(700);
        if(!dataManager.verifieIdCouponExiste(couponId)) {
            LOGGER.info("OUT - getCoupon => Erreur 404");
            throw new CouponNotFoundException(couponId);
        }
        Coupon couponARetourner = new Coupon(dataManager.getCoupon(couponId));
        LOGGER.info("OUT - getCoupon => {}", couponARetourner);
        return couponARetourner;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Ajouter un coupon", notes = "Permet d'ajouter un coupon")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "CREATE - Le coupon créé est retourné", response = Coupon.class),
            @ApiResponse(code = 500, message = "Si une erreur se produit")
    })
    public Coupon addCoupon(
            @ApiParam(value = "Coupon à ajouter", required = true) @RequestBody Coupon coupon
    ) {
        LOGGER.info("IN - addCoupon {}", coupon);
        simulerUneLatenceEntre200Et(500);
        Coupon couponAjoute = new Coupon(dataManager.addCoupon(coupon.getNom(), coupon.getReduction(), coupon.getEstUtilise()));
        LOGGER.info("OUT - addCoupon => {}", couponAjoute);
        return couponAjoute;
    }

    @RequestMapping(value = "/{couponId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Supprimer un coupon", notes = "Permet de supprimer un coupon par son identifiant")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "NO CONTENT"),
            @ApiResponse(code = 404, message = "Coupon non trouvé"),
            @ApiResponse(code = 500, message = "Si une erreur se produit")
    })
    public boolean deleteCoupon(
            @ApiParam(value = "Identifiant du coupon à supprimer", required = true)  @PathVariable("couponId") int couponId
    ) {
        LOGGER.info("IN - deleteCoupon {}", couponId);
        simulerUneLatenceEntre200Et(300);
        if(!dataManager.verifieIdCouponExiste(couponId)) {
            LOGGER.info("OUT - deleteCoupon => Erreur 404");
            throw new CouponNotFoundException(couponId);
        }
        dataManager.supprimerCoupon(couponId);
        LOGGER.info("OUT - deleteCoupon");
        return true;
    }

}
