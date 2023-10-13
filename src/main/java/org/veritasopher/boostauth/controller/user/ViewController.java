package org.veritasopher.boostauth.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * User View Controller
 *
 * @author Yepeng Ding
 */
@Controller("viewController")
@RequestMapping("/view")
public class ViewController {

    @GetMapping("/preregister")
    public String preregister(Model model) {
        return "view/user/preregistration";
    }

}
