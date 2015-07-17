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

    public CouponData getCoupon(int couponId) {
        return couponMap.get(couponId);
    }

    public CouponData addCoupon(String nom, String reduction, Boolean estUtilise) {
        CouponData coupon = new CouponData(nextCouponId, nom, reduction, estUtilise, "bla bla " + nextCouponId);
        couponMap.put(coupon.getId(), coupon);
        nextCouponId++;
        return coupon;
    }

    public CouponData addCouponData(CouponData couponData) {
        CouponData coupon = new CouponData(nextCouponId, couponData.getNom(), couponData.getReduction(), couponData.getEstUtilise(), couponData.getUnAutreChampsPurBack());
        couponMap.put(coupon.getId(), coupon);
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

    public List<CouponData> getListCoupons() {
        return new ArrayList<>(couponMap.values());
    }

}
