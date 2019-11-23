package akademiakodu.BMIcalculator.Controller;

import akademiakodu.BMIcalculator.Model.Info;
import akademiakodu.BMIcalculator.Model.Result;
import akademiakodu.BMIcalculator.Model.User;
import akademiakodu.BMIcalculator.Service.BMIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class BMIController {

    @Autowired
    private BMIService BMIService;

    @RequestMapping(value = "/admin/home", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView setBMI(@RequestParam(value = "weight", required = false) Double weight,
                               @RequestParam(value = "height", required = false) Double height,
                               @Valid Result result) {

        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = BMIService.findUserByEmail(auth.getName());
        modelAndView.addObject("userID", user.getId());

        if (weight != null && height != null) {
            result.setUser(user);
            DecimalFormat decimalFormat = new DecimalFormat();
            decimalFormat.setMaximumFractionDigits(1);
            Double score = (10000 * weight) / (height * height);

            String formatScore = decimalFormat.format(score).replace(',', '.');
            Double newScore = Double.valueOf(formatScore);

            result.setResult(newScore);
            modelAndView.addObject("BMIresult", result.getResult());

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyy/MM/dd HH:mm:ss");
            LocalDateTime localDateTime = LocalDateTime.now();
            result.setDate(dateTimeFormatter.format(localDateTime));

            if (newScore <= 18.5) {
                result.setInfo(Info.UNDERWEIGHT.getName());
                modelAndView.addObject("BMIMessage", Info.UNDERWEIGHT.getName());
            } else if (newScore > 18.5 && newScore <= 24.9) {
                result.setInfo(Info.HEALTHY.getName());
                modelAndView.addObject("BMIMessage", Info.HEALTHY.getName());
            } else if (newScore >= 25 && newScore <= 29.9) {
                result.setInfo(Info.OVERWEIGHT.getName());
                modelAndView.addObject("BMIMessage", Info.OVERWEIGHT.getName());
            } else if (newScore >= 30 && newScore <= 39.9) {
                result.setInfo(Info.OBESE.getName());
                modelAndView.addObject("BMIMessage", Info.OBESE.getName());
            }
            try {
                BMIService.addResult(result);
            } catch (Exception e) {
                System.out.print(e);
            }
        }
        modelAndView.setViewName("admin/home");
        return modelAndView;
    }
}
