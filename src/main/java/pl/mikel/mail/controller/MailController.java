package pl.mikel.mail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pl.mikel.insurance.dao.InsuranceDao;
import pl.mikel.insurance.repository.InsuranceRepository;
import pl.mikel.mail.service.MailService;
import pl.mikel.pdf.PdfGenerator;
import java.util.List;
import java.util.Optional;


@Controller
public class MailController {

    private MailService mailService;
    private TemplateEngine templateEngine;
    private InsuranceRepository insuranceRepository;
    private PdfGenerator pdfGenerator;

    @Autowired
    public MailController(MailService mailService,
                          TemplateEngine templateEngine,
                          InsuranceRepository insuranceRepository,
                          PdfGenerator pdfGenerator) {

        this.mailService = mailService;
        this.templateEngine = templateEngine;
        this.insuranceRepository = insuranceRepository;
        this.pdfGenerator = pdfGenerator;

    }

    @RequestMapping("/mail/sendInsuranceById/{id}")
    public String sendMail(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) throws Exception {

        List<InsuranceDao> email = insuranceRepository.findFirstByActualUserOrderByIdDesc(mailService.getEmailAdress());

        if (email.size() > 0) {

            Context context = new Context();
            context.setVariable("header", "Insurance generator");
            context.setVariable("title", "Calculate is ready i attachment");
            context.setVariable("description", "For future calculations visit the website ");
            String body = templateEngine.process("mailTemplate", context);

            model.addAttribute("search", email);


            Optional<InsuranceDao> findIdInRepo = insuranceRepository.findById(id);

                if (findIdInRepo.isPresent()) {

                    if(!pdfGenerator.checkInsurancePdfIsPresent(id)) {
                        pdfGenerator.generatePdf(email.get(0), id);
                    }
                mailService.sendSimpleMail(mailService.getEmailAdress(),
                        "Insurance number " + id, body, id);

                redirectAttributes.addFlashAttribute("message", "Insurance has been correctly generated and sent to the email address: " + mailService.getEmailAdress());

            } else {
                    redirectAttributes.addFlashAttribute("failed", "Insurance could not be sent to the email address: " + mailService.getEmailAdress());
                    }
         }
        return "redirect:/showById/{id}";
    }
}




