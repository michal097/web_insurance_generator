package pl.mikel.insurance.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import pl.mikel.insurance.dao.InsuranceDao;
import pl.mikel.insurance.repository.InsuranceRepository;
import pl.mikel.insurance.service.InsuranceService;
import pl.mikel.mail.service.MailService;
import pl.mikel.security.repository.UserRoleRepository;
import java.util.List;

//TEST
@RestController
public class InsuranceRestController {
    /*
    List<InsuranceDao> insurances;
    @Autowired
    public InsuranceRepository insuranceRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    InsuranceService insuranceService;

    @Autowired
    MailService mailService;


    @RequestMapping("/showInsurance")
    public List<InsuranceDao> showAlles() {
        insurances = insuranceRepository.findAll();
        return insurances;
    }

    @RequestMapping("/showInsurance/{id}")
    public List<InsuranceDao> insurances(@PathVariable Long id) {


        insurances = insuranceRepository.findAllByIdAndActualUser(id, mailService.getEmailAdress());

        return insurances;
    }

    @RequestMapping("/page/{number}")
    public List<InsuranceDao> numberOfPage(@PathVariable int number) {


        Pageable paging = PageRequest.of(number, 5, Sort.by("id"));

        Page<InsuranceDao> paged = insuranceRepository.findAllByActualUser(mailService.getEmailAdress(), paging);


        System.out.println(paged.getTotalElements());

        return paged.getContent();
    }
*/
}
