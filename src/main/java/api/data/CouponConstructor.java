package api.data;

public class CouponConstructor {

    private CouponData couponData;

    public CouponConstructor() {
        couponData = new CouponData();
    }

    public static CouponConstructor coupon() {
        return new CouponConstructor();
    }

    public CouponConstructor withName(String name) {
        couponData.setNom(name);
        return this;
    }

    public CouponConstructor withReduction(String reduction) {
        couponData.setReduction(reduction);
        return this;
    }

    public CouponConstructor utilise() {
        couponData.setEstUtilise(true);
        return this;
    }

    public CouponConstructor nonUtilise() {
        couponData.setEstUtilise(false);
        return this;
    }

    public CouponData create(DataManager dataManager) {
        return dataManager.addCouponData(couponData);
    }

}
