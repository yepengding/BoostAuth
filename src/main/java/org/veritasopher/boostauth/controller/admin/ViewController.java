package org.veritasopher.boostauth.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Admin View Controller
 *
 * @author Yepeng Ding
 */
@Controller("adminViewController")
@RequestMapping("/admin/view")
public class ViewController {

    @GetMapping("/manage/preregister")
    public String registry(Model model) {
        return "view/admin/preregistration";
    }

    @GetMapping("/manage/group")
    public String group(Model model) {
        return "view/admin/group";
    }

}
