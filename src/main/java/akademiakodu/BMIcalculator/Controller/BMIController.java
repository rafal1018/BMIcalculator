package akademiakodu.BMIcalculator.Controller;

import akademiakodu.BMIcalculator.Model.Info;
import akademiakodu.BMIcalculator.Model.Result;
import akademiakodu.BMIcalculator.Model.User;
import akademiakodu.BMIcalculator.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.text.DecimalFormat;

@Controller
public class BMIController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/admin/home", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView getBMIParam(@RequestParam(value = "weight", required = false) Double weight,
                                    @RequestParam(value = "height", required = false) Double height,
                                    @Valid Result result) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        if (weight != null && height != null) {
            result.setUser(user);
            DecimalFormat decimalFormat = new DecimalFormat();
            decimalFormat.setMaximumFractionDigits(2);
//            Double score = Double.parseDouble(decimalFormat.format(10000*weight/(height*height)));
            Double score = (10000 * weight) / (height * height);
            result.setResult(score);
            modelAndView.addObject("BMIresult", decimalFormat.format(result.getResult()));
//            modelAndView.addObject("BMIresult", score);
            try {
                userService.addResult(result);
            } catch (Exception e) {
                System.out.print(e);
            }
            if (score <= 18.5) {
                modelAndView.addObject("BMIMessage", Info.UNDERWEIGHT.getName());
            } else if (score > 18.5 && score <= 24.9) {
                modelAndView.addObject("BMIMessage", Info.HEALTHY.getName());
            } else if (score > 25 && score <= 29.9) {
                modelAndView.addObject("BMIMessage", Info.OVERWEIGHT.getName());
            } else if (score > 30 && score <= 39.9) {
                modelAndView.addObject("BMIMessage", Info.OBESE.getName());
            }
        }
        modelAndView.setViewName("admin/home");
        return modelAndView;
    }

    @RequestMapping(value = "/admin/all{}id", method = RequestMethod.GET)
    public ModelAndView getResultById(Integer id) {
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.findUserById(id);
        modelAndView.addObject("getResult", userService.findUserById(id) != null ?
                userService.findUserById(id).getResults() : null);
        modelAndView.setViewName("admin/all");
        return modelAndView;
    }
}
