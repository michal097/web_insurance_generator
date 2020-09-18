package pl.mikel.pdf;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextRenderer;
import pl.mikel.insurance.dao.InsuranceDao;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import static org.thymeleaf.templatemode.TemplateMode.HTML;

@Service
public class PdfGenerator {

    public PdfGenerator() {
    }


    private static final Charset CHARSET = StandardCharsets.UTF_8;


    public void generatePdf(InsuranceDao data, Long id) throws Exception {

        String outputFile = "src/main/resources/pdf/insurance_number" + id + ".pdf";
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(HTML);
        templateResolver.setCharacterEncoding(String.valueOf(CHARSET));

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.addTemplateResolver(templateResolver);


        Context context = new Context();
        context.setVariable("data", data);

        String renderedHtmlContent = templateEngine.process("template", context);
        String xHtml = convertToXhtml(renderedHtmlContent);

        ITextRenderer renderer = new ITextRenderer();

        String baseUrl = "src/main/resources/";
        renderer.setDocumentFromString(xHtml, baseUrl);
        renderer.layout();


        OutputStream outputStream = new FileOutputStream(outputFile);
        renderer.createPDF(outputStream);
        outputStream.close();
    }


    private String convertToXhtml(String html) {
        Tidy tidy = new Tidy();
        tidy.setInputEncoding(String.valueOf(CHARSET));
        tidy.setOutputEncoding(String.valueOf(CHARSET));
        tidy.setXHTML(true);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(html.getBytes(CHARSET));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        tidy.parseDOM(inputStream, outputStream);
        return outputStream.toString();
    }

    public boolean checkInsurancePdfIsPresent(Long id) throws IOException {
        try (Stream<Path> paths = Files.walk(Paths.get("src/main/resources/pdf/"))) {

        return paths.filter(Files::isRegularFile)
                    .anyMatch(path -> path.toString()
                            .replaceAll("[^\\d]","")
                            .equals(String.valueOf(id)));
        }

    }
}


