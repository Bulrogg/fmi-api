package api.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static api.data.CouponConstructor.coupon;

@Component
public class DataInitializer {

    @Autowired
    private DataManager dataManager;

    // TODO arriver à unjecter directement le dataManager dans le CouponConstructor pour pouvoir écrire .create()

    @PostConstruct
    private void init() {
        coupon().withName("Reduction 1").withReduction("45 euros").utilise().withPurBack("bla bla 1").create(dataManager);
        coupon().withName("Reduction 2").withReduction("5 euros").nonUtilise().withPurBack("bla bla 2").create(dataManager);
        coupon().withName("Reduction 3").withReduction("10 centimes").nonUtilise().withPurBack("bla bla 3").create(dataManager);
    }

}
