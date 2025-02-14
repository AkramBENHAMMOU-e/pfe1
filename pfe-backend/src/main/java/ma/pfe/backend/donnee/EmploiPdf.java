package ma.pfe.backend.donnee;

import com.lowagie.text.Font;
import com.lowagie.text.Rectangle;
import com.lowagie.text.*;
import com.lowagie.text.alignment.HorizontalAlignment;
import com.lowagie.text.alignment.VerticalAlignment;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import ma.pfe.backend.entites.Filiere;
import ma.pfe.backend.entites.Seance;
import ma.pfe.backend.enumeration.Periode;
import ma.pfe.backend.repository.SeanceRepos;
import ma.pfe.backend.service.IFilierService;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.IOException;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

@Service
@AllArgsConstructor
public class EmploiPdf {
    private ImportData importData;
    private SeanceRepos seanceRepos;
    private IFilierService iFilierService;


    Periode[] periodes;
    List<DayOfWeek> jours;


    public void uneFilierePdf(HttpServletResponse response, String nomFiliere) throws IOException {
        importData.importData();
       Filiere filiere = iFilierService.getFiliereBynom(nomFiliere);

        Document myPDFDoc = new Document(PageSize.A2);

        final PdfWriter pdfWriter = PdfWriter.getInstance(myPDFDoc, response.getOutputStream());
        myPDFDoc.open();  // Open the Document

        AddPageClasse(myPDFDoc,filiere);
        myPDFDoc.close();
        pdfWriter.close();
    }

    public void ALLFilieres(HttpServletResponse response) throws IOException {
        importData.importData();
        Document myPDFDoc = new Document(PageSize.A2);

        final PdfWriter pdfWriter = PdfWriter.getInstance(myPDFDoc, response.getOutputStream());
        myPDFDoc.open();  // Open the Document
        for(Filiere filiere : ImportData.filieres){

            AddPageClasse(myPDFDoc,filiere);

        }

        myPDFDoc.close();
        pdfWriter.close();


    }

    public void AddPageClasse(Document myPDFDoc, Filiere filiere) throws IOException{
        periodes = Periode.values();
        jours = new ArrayList<>();
        jours.add(DayOfWeek.MONDAY);
        jours.add(DayOfWeek.TUESDAY);
        jours.add(DayOfWeek.WEDNESDAY);
        jours.add(DayOfWeek.THURSDAY);
        jours.add(DayOfWeek.FRIDAY);
        jours.add(DayOfWeek.SATURDAY);

        Table table = new Table(6,6); // 6 columns


        FontFactory.register("Fonts/QuattrocentoSans-Italic.ttf");


        FontFactory.register("Fonts/Calibri Regular.ttf");



        Font fontDay = FontFactory.getFont("Calibri", BaseFont.WINANSI, 17,Font.BOLD);

        Font Calibri2 = FontFactory.getFont("Calibri", BaseFont.WINANSI, 13,Font.BOLD);

        Font Quatt = FontFactory.getFont("Calibri",BaseFont.WINANSI , 13);



        float[] columnWidths = { 25f, 40f, 40f, 40f, 40f,40f };
        table.setWidths(columnWidths);
        table.setPadding(5f);
        table.setWidth(100f);

        ArrayList<String> headerTable = new ArrayList<>();
        headerTable.add("");
        headerTable.add("08h00    10h:00");
        headerTable.add("10h15    12h15");
        headerTable.add("13h30    15h30");
        headerTable.add("15h45    17h45");
        headerTable.add("18h00    19h30");

        headerTable.forEach(e -> {
            Paragraph paragraph = new Paragraph(e,fontDay);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            Cell current = new Cell(paragraph);
            current.setHeader(true);
            current.setHorizontalAlignment(HorizontalAlignment.CENTER);
            current.setBackgroundColor(Color.LIGHT_GRAY);
            table.addCell(current);
        });

        LinkedHashMap<Integer, List<List<Paragraph>>> listRows = new LinkedHashMap<>();
        int rowNumber = 1;


        for(DayOfWeek jour:jours){

            List<Paragraph> CellList = new ArrayList<>();

            List<List<Paragraph>> jourList = new ArrayList<>();
            Paragraph Day;
            if(rowNumber==1){
                Day = new Paragraph("\n\n\nLundi\n\n\n\n",Calibri2);
            } else if (rowNumber==2) {
                Day =new Paragraph("\n\n\nMardi\n\n\n\n",Calibri2);

            } else if (rowNumber==3) {
                Day = new Paragraph("\n\nMercredi\n\n\n",Calibri2);
            } else if (rowNumber==4) {
                Day = new Paragraph("\n\nJeudi\n\n\n",Calibri2);
            } else if(rowNumber==5){
                Day = new Paragraph("\n\nVendredi\n\n\n",Calibri2);
            }
            else{
                Day = new Paragraph("\n\nSamedi\n\n\n",Calibri2);
            }
            Day.setAlignment(Element.ALIGN_CENTER);
            Day.setFont(fontDay);


            ArrayList<Paragraph> dayCell = new ArrayList<>();
            dayCell.add(Day);
            jourList.add(dayCell);
            for(Periode p :periodes){
                CellList = new ArrayList<>();
                List<Seance> elms = seanceRepos.getSeanceByJourAndPeriodeAndFiliere(jour,p, filiere.getID());
                System.out.println("Elements "+elms.size());
                if(elms.size()>0){
                        Paragraph sc = new Paragraph(elms.get(0).getNomSeance(), Calibri2);
                        sc.setAlignment(Element.ALIGN_CENTER);
                        Paragraph Salle = new Paragraph(elms.get(0).getSalle().getNomSalle(), Quatt);
                        Salle.setAlignment(Element.ALIGN_CENTER);
                        CellList.add(sc);
                        CellList.add(Salle);

                }
                else{
                    CellList.add(new Paragraph(""));
                }
                jourList.add(CellList);

            }
            System.out.print(jourList+"\t");
            listRows.put(rowNumber, jourList);
            rowNumber++;
        }
        listRows.forEach((index,userDetailRow) -> {
            userDetailRow.forEach(paragraphs -> {
                Cell cell = new Cell();
                paragraphs.forEach(paragraph ->{
                    cell.add(paragraph);
                    cell.setHorizontalAlignment(HorizontalAlignment.CENTER);
                    cell.setVerticalAlignment(VerticalAlignment.CENTER);
                });
                table.addCell(cell);
            });
        });
        myPDFDoc.addTitle("Emplois du temps");
        FontFactory.register("Fonts/COMIC.ttf");
        FontFactory.register("Fonts/times new roman.ttf");
        Font font2 = FontFactory.getFont("Times New Roman", 14);
        Font font4 = FontFactory.getFont("Calibri", 18,Font.BOLD);
        int year = new Date().getYear();
        String text0 = "Université Couaib Doukkali \n Faculté des sciences \n El Jadida";
        String text = "Année universitaire "+Integer.toString(year+1900)+" / "+Integer.toString(year+1+1900);
        String text2 = "EMPLOI DU TEMPS";
        String text4 = "FILIERE : "+filiere.getNom();
        PdfPTable table1 = new PdfPTable(2);
        table1.setWidthPercentage(100);
        PdfPCell cellText0 = new PdfPCell(new Phrase(text0));
        cellText0.setHorizontalAlignment(Element.ALIGN_LEFT);
        cellText0.setBorder(Rectangle.NO_BORDER);
        table1.addCell(cellText0);
        PdfPCell cellText = new PdfPCell(new Phrase(text));
        cellText.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cellText.setBorder(Rectangle.NO_BORDER);
        table1.addCell(cellText);
        myPDFDoc.add(table1);
        Paragraph paragraph2 = new Paragraph(text2,font2);
        paragraph2.setAlignment(Element.ALIGN_CENTER);
        Paragraph paragraph4 = new Paragraph(text4,font4);
        paragraph4.setAlignment(Element.ALIGN_CENTER);
        myPDFDoc.add(new Paragraph(Chunk.NEWLINE));
        myPDFDoc.add(paragraph2);
        myPDFDoc.add(paragraph4);
        myPDFDoc.add(new Paragraph(Chunk.NEWLINE));
        myPDFDoc.add(table);
        myPDFDoc.newPage();
    }
}
