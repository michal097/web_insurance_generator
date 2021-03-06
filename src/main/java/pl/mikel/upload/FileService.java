package pl.mikel.upload;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {


    public final String DIRECTORY = "src/main/resources/uploaded_files/";
    public static String getFileName;

    public void uploadFile(MultipartFile file) throws Exception {

        try {
            Path copyLocation = Paths
                    .get(DIRECTORY + File.separator + StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename())));
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
            getFileName = file.getOriginalFilename();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Could not store file " + file.getOriginalFilename()
                    + ". Please try again!");
        }
    }

}
