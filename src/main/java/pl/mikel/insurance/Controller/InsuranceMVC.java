package pl.mikel.insurance.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.mikel.insurance.dao.InsuranceDao;
import pl.mikel.insurance.repository.InsuranceRepository;
import pl.mikel.insurance.service.InsuranceService;
import pl.mikel.mail.service.MailService;
import javax.validation.Valid;
import java.util.List;

@Controller
public class InsuranceMVC {

    private InsuranceRepository insuranceRepository;
    private InsuranceService insuranceService;
    private MailService mailService;

    @Autowired
    public InsuranceMVC(InsuranceRepository insuranceRepository,
                        InsuranceService insuranceService,
                        MailService mailService) {

        this.insuranceRepository = insuranceRepository;
        this.insuranceService=insuranceService;
        this.mailService=mailService;

    }

    @GetMapping("/show")
    public String showInsurance(Model model) {

        model.addAttribute("insurance", new InsuranceDao());
        return "test";
    }

    @PostMapping("/show")
    public String addNewInsurance( @ModelAttribute("insurance") @Valid InsuranceDao insurance,
                                                          BindingResult bindingResult) {

        insurance.setActualUser(mailService.getEmailAdress());

        if (bindingResult.hasErrors()) {
            return "test";
        }
        else{
           String name =  insurance.getClientName().toLowerCase();
           String surmane = insurance.getClientSurname().toLowerCase();
           
            insurance.setClientName(name.substring(0,1).toUpperCase() + name.substring(1));
            insurance.setClientSurname(surmane.substring(0,1).toUpperCase() + surmane.substring(1));

            insurance.setPrice(insuranceService.calculateInsurance(
                    insurance.getYearOfProduction(),
                    insurance.getFuel(),
                    insurance.getCapacity(),
                    insurance.getDoors(),
                    insurance.getAgeOfUser(),
                    insurance.getDamage(),
                    insurance.getMileage(),
                    insurance.getUsage()
            ));
            insuranceRepository.save(insurance);

            return "redirect:/showCalculate";
        }
    }

    @RequestMapping(value = "/showCalculate", method = RequestMethod.GET)
        public String showMyCalculate(Model model){

        List<InsuranceDao> showCalculate = insuranceRepository.findFirstByActualUserOrderByIdDesc(mailService.getEmailAdress());

        model.addAttribute("search", showCalculate);

        return "index";

    }

}
