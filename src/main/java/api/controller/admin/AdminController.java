package api.controller.admin;

import api.data.CouponData;
import api.data.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private DataManager dataManager;

    @RequestMapping(value = "/gestion-coupons", method = RequestMethod.GET)
    public String coupons(Map<String, Object> model) {
        model.put("coupons", dataManager.getListCoupons());
        model.put("couponAddForm", new CouponData());
        return "admin/gestion-coupons";
    }

    @RequestMapping(value = "/addCoupon", method = RequestMethod.POST)
    public String addCoupon(@ModelAttribute("couponAddForm") CouponData couponData) {
        dataManager.addCouponData(couponData);
        return "redirect:/admin/gestion-coupons";
    }

    @RequestMapping(value = "/deleteCoupon/{id}", method = RequestMethod.GET)
    public String deleteCoupon(@PathVariable("id") int id) {
        dataManager.supprimerCoupon(id);
        return "redirect:/admin/gestion-coupons";
    }

    @RequestMapping(value = "/updateCoupon", method = RequestMethod.PUT)
    public String updateCoupon(@ModelAttribute("couponUpdateForm") CouponData couponData) {
        dataManager.updateCouponData(couponData);
        return "redirect:/admin/gestion-coupons";
    }

}
