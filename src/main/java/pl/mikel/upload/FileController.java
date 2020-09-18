package pl.mikel.upload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.mikel.importFile.ReadDataFromCsv;
import pl.mikel.insurance.dao.InsuranceDao;
import pl.mikel.insurance.repository.InsuranceRepository;
import pl.mikel.insurance.service.InsuranceService;
import pl.mikel.mail.service.MailService;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;


@Controller
public class FileController {


    private FileService fileService;
    private InsuranceRepository insuranceRepository;
    private MailService mailService;
    private InsuranceService insuranceService;


    @Autowired
    FileController (FileService fileService,
                    InsuranceRepository insuranceRepository,
                    MailService mailService,
                    InsuranceService insuranceService){

        this.fileService=fileService;
        this.insuranceRepository=insuranceRepository;
        this.mailService=mailService;
        this.insuranceService=insuranceService;

    }


    @GetMapping("/uploadClients")
    public ModelAndView uploadClients(){

        return new ModelAndView("upload");
    }


    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws Exception {

        String pathToFile = "src/main/resources/uploaded_files/"+file.getOriginalFilename();

        try {
            if(Objects.requireNonNull(file.getOriginalFilename())
                   .endsWith(".csv")) {

                fileService.uploadFile(file);
            }
            else throw new IllegalArgumentException("Njoe csv");
            List<InsuranceDao> testing =   ReadDataFromCsv.readData(pathToFile,mailService.getEmailAdress());


            for (InsuranceDao insuranceDao : testing) {

                insuranceDao.setPrice(
                                insuranceService.calculateInsurance(
                                insuranceDao.getYearOfProduction(),
                                insuranceDao.getFuel(),
                                insuranceDao.getCapacity(),
                                insuranceDao.getDoors(),
                                insuranceDao.getAgeOfUser(),
                                insuranceDao.getDamage(),
                                insuranceDao.getMileage(),
                                insuranceDao.getUsage()
                        ));
                               insuranceRepository.saveAll(testing);

            }

            redirectAttributes.addFlashAttribute("message",
                    "File "+ file.getOriginalFilename()+" has been uploaded to the server: " );

        }catch (Exception ex ){
            ex.printStackTrace();
            if(file.isEmpty()){
                redirectAttributes.addFlashAttribute("failed",
                        "No file was selected");
            }else
            redirectAttributes.addFlashAttribute("failed",
                    "Invalid file structure: " + file.getOriginalFilename());
        }
           return "redirect:/uploadClients";

    }

    @GetMapping("/downloadCsvDraft")
    public ResponseEntity<InputStreamResource> downloadCsvDraft() throws IOException {

        File file = new File("src/main/resources/pdf/testCSV.csv");
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=" + file.getName())
                .contentType(MediaType.APPLICATION_PDF).contentLength(file.length())
                .body(resource);
    }

}
