package api.controller.admin;

import api.data.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private DataManager dataManager;

    @RequestMapping("/gestion-coupons")
    public String coupons(Map<String, Object> model) {
        model.put("coupons", dataManager.getListCoupons());
        return "/WEB-INF/jsp/admin/gestion-coupons.jsp";
    }

}
