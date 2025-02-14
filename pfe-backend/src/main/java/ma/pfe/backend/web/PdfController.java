package ma.pfe.backend.web;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import ma.pfe.backend.donnee.EmploiPdf;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@AllArgsConstructor
@RequestMapping("/pdf")
@CrossOrigin("*")
public class PdfController {
    private EmploiPdf pdf;
    @GetMapping("/filieres/{nomFiliere}")
    void emploiByFiliere(HttpServletResponse response, @PathVariable String nomFiliere) throws IOException {
         pdf.uneFilierePdf(response,nomFiliere);
    }

    @GetMapping("/filieres")
    void emploisAllFilieres(HttpServletResponse response) throws IOException {
        pdf.ALLFilieres(response);
        }

}
