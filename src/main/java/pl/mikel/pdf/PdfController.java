package pl.mikel.pdf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.mikel.insurance.repository.InsuranceRepository;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Controller
public class PdfController {


  private PdfGenerator pdfGenerator;
  private InsuranceRepository insuranceRepository;
  @Autowired
    public PdfController(PdfGenerator pdfGenerator, InsuranceRepository insuranceRepository){
        this.pdfGenerator=pdfGenerator;
        this.insuranceRepository=insuranceRepository;
    }

    @RequestMapping("/generatePDF/{id}")
    public String generatePDF(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) throws Exception {
      pdfGenerator.generatePdf(insuranceRepository.findAllById(id).get(0), id);

      redirectAttributes.addFlashAttribute("message", "Insurance number "+id+" has been generated");

      return "redirect:/showById/{id}";

    }

    @GetMapping("/downloadInsurance/pdf/insurance_number/{id}")
    public ResponseEntity<InputStreamResource> downloadFile1(@PathVariable("id") Long id) throws IOException {
      String number = String.valueOf(id);
      File file = new File("src/main/resources/pdf/insurance_number"+number+".pdf");
      InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

      return ResponseEntity.ok()
              .header(HttpHeaders.CONTENT_DISPOSITION,
                      "attachment;filename=" + file.getName())
              .contentType(MediaType.APPLICATION_PDF).contentLength(file.length())
              .body(resource);
    }

}
