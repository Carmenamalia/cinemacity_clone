package com.springapps.cinemacityclone.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

@Service
public class PdfGenerationService {

    public void generatePdf(String movieName,String filePath){
        Document document = new Document();

        try{
            PdfWriter.getInstance(document,new FileOutputStream(new File(filePath)));
            document.open();

            Paragraph movieNameParagraph = new Paragraph("Movie: " + movieName);
            document.add(movieNameParagraph);

            document.close();
        }catch (DocumentException e){
            e.printStackTrace();
        }catch (FileNotFoundException e){
           throw new RuntimeException(e);
        }
    }
}
