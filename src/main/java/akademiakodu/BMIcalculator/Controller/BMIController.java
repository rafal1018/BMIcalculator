package akademiakodu.BMIcalculator.Controller;

import akademiakodu.BMIcalculator.Model.User;
import akademiakodu.BMIcalculator.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BMIController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/admin/home", method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
        modelAndView.setViewName("admin/home");
        return modelAndView;
    }

//    @RequestMapping(value = "/admin/home", method = RequestMethod.GET)
//    public ModelAndView getBMIParam(@RequestParam(value = "weight", required = false) Double weight,
//                                    @RequestParam(value = "height", required = false)Double height,
//                                    BindingResult validator){
//        ModelAndView modelAndView = new ModelAndView();
//
//        if (weight != null && height != null) {
//            result.setUser(user);
//            result.setResult(weight / (height * height));
//            modelAndView.addObject("BMIresult", result.getResult());
//            try {
//                userService.addResult(result);
//            } catch (Exception e) {
//                System.out.print(e);
//            }
//        }
//        if (!validator.hasFieldErrors()) {
//            modelAndView.addObject("add", "yes");
//            modelAndView.addObject("result", result);
//        } else {
//            modelAndView.addObject("result", result);
//            modelAndView.addObject("error", "yes");
//        }
//
//        modelAndView.setViewName("admin/home");
//        return modelAndView;
//    }
}
