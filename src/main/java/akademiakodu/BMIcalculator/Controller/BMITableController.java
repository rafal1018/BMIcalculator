package akademiakodu.BMIcalculator.Controller;

import akademiakodu.BMIcalculator.Model.Result;
import akademiakodu.BMIcalculator.Model.User;
import akademiakodu.BMIcalculator.Service.BMIService;
import akademiakodu.BMIcalculator.Util.GeneratePdfReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.ByteArrayInputStream;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BMITableController {

    @Autowired
    private BMIService BMIService;

    @RequestMapping(value = "/admin/home/all/pdfreport{id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> citiesReport(Integer id) {
        Sort sort1 = new Sort(Sort.Direction.ASC, "weight");

        List<Result> results = BMIService.findUserById(id).getResults();

        ByteArrayInputStream bis = GeneratePdfReport.resultsReport(results);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=citiesreport.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

    @RequestMapping(value = "/admin/home/all{id}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getResultById(@RequestParam(value = "filter", required = false) String filter,
                                      Integer id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = BMIService.findUserByEmail(auth.getName());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userID", user.getId());
        if (filter != null) {
            List<Result> list = BMIService.findUserById(user.getId()).getResults();
            switch (filter) {
                case "Date desc":
//                    modelAndView.addObject("results", userService.findUserById(user.getId()) != null ?
//                            userService.findUserById(user.getId()).getResults(Sort.by("weight").ascending()) : null);
                    modelAndView.addObject("results", list.stream()
                            .sorted(Comparator.comparing(Result::getDate).reversed())
                            .collect(Collectors.toList()));
                    break;
                case "Date asc":
                    modelAndView.addObject("results", list.stream()
                            .sorted(Comparator.comparing(Result::getDate))
                            .collect(Collectors.toList()));
                    break;
                case "Weight desc":
                    modelAndView.addObject("results", list.stream()
                            .sorted(Comparator.comparing(Result::getWeight).reversed())
                            .collect(Collectors.toList()));
                    break;
                case "Weight asc":
                    modelAndView.addObject("results", list.stream()
                            .sorted(Comparator.comparing(Result::getWeight))
                            .collect(Collectors.toList()));
                    break;
                case "BMI desc":
                    modelAndView.addObject("results", list.stream()
                            .sorted(Comparator.comparing(Result::getResult).reversed())
                            .collect(Collectors.toList()));
                    break;
                case "BMI asc":
                    modelAndView.addObject("results", list.stream()
                            .sorted(Comparator.comparing(Result::getResult))
                            .collect(Collectors.toList()));
                    break;
            }
        }
        modelAndView.setViewName("admin/all");
        return modelAndView;
    }
}
