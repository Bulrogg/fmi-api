package api.data;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DataManager {

    private Map<Integer, CouponData> couponMap = new HashMap<>();

    private int nextCouponId = 1;

    public DataManager() {
        addCoupon("Reduction 1", "45 euros", true);
        addCoupon("Reduction 2", "5 euros", false);
        addCoupon("Reduction 3", "10 centimes", false);
    }

    public CouponData getCoupon(int couponId) {
        return couponMap.get(couponId);
    }

    public CouponData addCoupon(String nom, String reduction, Boolean estUtilise) {
        CouponData coupon = new CouponData(nextCouponId, nom, reduction, estUtilise, "champ tech qui sert juste au back");
        this.couponMap.put(coupon.getId(), coupon);
        nextCouponId++;
        return coupon;
    }

    public boolean verifieIdCouponExiste(int couponId) {
        return couponMap.containsKey(couponId);
    }

    public void supprimerCoupon(int couponId) {
        couponMap.remove(couponId);
    }

    public List<CouponData> getListeCouponNonUtilises() {
        List<CouponData> couponNonUtilises = new ArrayList<>();
        for (CouponData coupon : couponMap.values()) {
            if (!coupon.getEstUtilise()) {
                couponNonUtilises.add(coupon);
            }
        }
        return couponNonUtilises;
    }

}
