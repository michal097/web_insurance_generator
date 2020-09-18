package pl.mikel.hibernate.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.mikel.hibernate.search.HibernateSearchService;
import pl.mikel.insurance.dao.InsuranceDao;
import pl.mikel.mail.service.MailService;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class SearchController {

    private HibernateSearchService hibernateSearchService;
    private MailService mailService;

    @Autowired
    SearchController(HibernateSearchService hibernateSearchService, MailService mailService) {
        this.hibernateSearchService = hibernateSearchService;
        this.mailService = mailService;
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(@RequestParam(value = "search", required = false) String searchPhrase, Model model) {

        List<InsuranceDao> searchResult = null;
        List<InsuranceDao> insuranceSearch = null;

        try {

            searchResult = hibernateSearchService.search(searchPhrase.toLowerCase());
            insuranceSearch = searchResult.stream()
                    .filter(insuranceDao -> insuranceDao.getActualUser()
                            .equals(mailService.getEmailAdress()))
                    .collect(Collectors.toList());

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        model.addAttribute("search", insuranceSearch);

        return "searchEngine";
    }

}
