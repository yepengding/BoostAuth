package org.veritasopher.boostauth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * View Controller
 *
 * @author Yepeng Ding
 */
@Controller
@RequestMapping("/view")
public class ViewController {

    @GetMapping("/manage")
    public String manage(Model model) {
        return "view/management";
    }

}
