package api.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DataInitializer {

    @Autowired
    private DataManager dataManager;

    @PostConstruct
    private void init() {
        // TODO faire des DSL sympa pour injection de coupon ex : coupon().withName("dd").withReduction("dd").utilise().create()
        dataManager.addCouponData(new CouponData(null, "Reduction 1", "45 euros", true, "bla bla 1"));
        dataManager.addCouponData(new CouponData(null, "Reduction 2", "5 euros", false, "bla bla 2"));
        dataManager.addCouponData(new CouponData(null, "Reduction 3", "10 centimes", false, "bla bla 3"));
    }

}
