package pl.mikel.insurance.Controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.mikel.insurance.dao.InsuranceDao;
import pl.mikel.insurance.repository.InsuranceRepository;
import pl.mikel.mail.service.MailService;
import pl.mikel.pdf.PdfGenerator;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Controller
public class LoadInsuranceDetailsController {

    private InsuranceRepository insuranceRepository;
    private MailService mailService;
    private PdfGenerator pdfGenerator;

    LoadInsuranceDetailsController(InsuranceRepository insuranceRepository, MailService mailService, PdfGenerator pdfGenerator) {

        this.insuranceRepository = insuranceRepository;
        this.mailService = mailService;
        this.pdfGenerator = pdfGenerator;
    }


    @RequestMapping("/myAllCalculates/page/{pagination}")
    public String showMyAllCalculates(@PathVariable("pagination") int pagination, Model model) {

        int recordsPerPage = 5;

        Pageable paging = PageRequest.of(pagination, recordsPerPage, Sort.by("id"));
        //in case user enter in URL value which is bigger than pagination pages
        if ((insuranceRepository.countAllByActualUser(mailService.getEmailAdress()) / recordsPerPage) < pagination) {
            return "error";
        }

        Page<InsuranceDao> paged = insuranceRepository.findAllByActualUser(mailService.getEmailAdress(), paging);

        model.addAttribute("pagination", paged.getContent());
        model.addAttribute("pagin", paged.getTotalPages());
        model.addAttribute("pag", paged.getNumber());

        return "menuCalculatesPagination";
    }

    @RequestMapping("/myLastCalculate")
    public String showMyLastCalculate(Model model) {
        List<InsuranceDao> myLastCalculate = insuranceRepository.findFirstByActualUserOrderByIdDesc(mailService.getEmailAdress());
        model.addAttribute("search", myLastCalculate);

        return "menuCalculates";
    }

    @RequestMapping("/showById/{id}")
    public String searchInsuranceById(@PathVariable("id") long id, Model model) throws IOException {
        InsuranceDao insuranceDao = insuranceRepository.findAllByIdAndActualUser(id, mailService.getEmailAdress()).get(0);

        model.addAttribute("search", insuranceDao);
        model.addAttribute("is_pdf_present", pdfGenerator.checkInsurancePdfIsPresent(id));

        return "insuranceDetails";
    }

    @GetMapping
    @RequestMapping("/deleteById/{id}")
    @Transactional
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {


        insuranceRepository.deleteAllByIdAndActualUser(id, mailService.getEmailAdress());
        redirectAttributes.addFlashAttribute("successfullyDeleted", "Insurance number: " + id + " has been deleted");

        return "redirect:/myAllCalculates/page/0";
    }

}
