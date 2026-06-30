package br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.util;

import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.DAO.MedicalRecordDAO;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.entities.Doctor;
import br.edu.ifg.luziania.sistemaDeProntuariosInexistentes.model.entities.Patient;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MedicalRecordHandler {
    private Patient patient;
    private Doctor doctor;
    private MedicalRecordDAO medicalRecord = new MedicalRecordDAO();

    public MedicalRecordHandler(Patient patient, Doctor doctor) {
        this.patient = patient;
        this.doctor = doctor;
    }

    public String getPath() {
        return "src/main/java/br/edu/ifg/luziania/sistemaDeProntuariosInexistentes/medicalRecords/"
                + patient.getName()
                + "_"
                + patient.getCpf()
                + "_"
                + doctor.getSpecialty()
                + ".pdf";
    }

    public void GeneratePDF(String examType, String result) { /*parametros*/
        String path = getPath();
        File pdf = new File(path);

        if (pdf.exists()) {
            return;
        }

        try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();

            document.addPage(page);

            PDPageContentStream content = new PDPageContentStream(document, page);

            PDType1Font font = new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN);
            float fontSize = 12;

            content.setFont(font, fontSize);

            content.beginText();

            content.newLineAtOffset(50, 750);

            content.showText("RESULTADO DE EXAME");
            content.newLineAtOffset(0, -30);

            content.showText("Paciente: " + patient.getName());
            content.newLineAtOffset(0, -20);

            content.showText("CPF: " + patient.getCpf());
            content.newLineAtOffset(0, -20);

            content.showText("Tipo de exame: " + examType);
            content.newLineAtOffset(0, -20);

            content.showText("Resultado:");
            content.newLineAtOffset(0, -20);

            // Quebra automática apenas para o resultado
            float maxWidth = 500; // largura disponível na página

            String[] palavras = result.split("\\s+");
            StringBuilder linhaAtual = new StringBuilder();

            for (String palavra : palavras) {
                String testeLinha = linhaAtual.isEmpty()
                        ? palavra
                        : linhaAtual + " " + palavra;

                float largura = font.getStringWidth(testeLinha) / 1000 * fontSize;

                if (largura > maxWidth) {
                    content.showText(linhaAtual.toString());
                    content.newLineAtOffset(0, -20);

                    linhaAtual = new StringBuilder(palavra);
                } else {
                    linhaAtual = new StringBuilder(testeLinha);
                }
            }

            // Escreve a última linha
            if (!linhaAtual.isEmpty()) {
                content.showText(linhaAtual.toString());
            }

            content.endText();

            content.close();

            document.save(new File(path));

            document.close();

        } catch (IOException e) {
            LogWriter.write("[ERRO | GERADOR DE EXAME] Erro ao gerar exame no formato PDF. " + e.getMessage());
        }
    }

    public void OpenPDF(String path) {
        try {
            File pdf = new File(path);

            if (pdf.exists()) {
                Runtime.getRuntime().exec(new String[]{"xdg-open", pdf.getAbsolutePath()});
            }

        } catch (IOException e) {
            LogWriter.write("[ERRO | NAVEGAÇÃO] Erro ao abrir um PDF. " + e.getMessage());
        }
    }

    public void InsertOnDB() {

        // id_appointment
        // path



    }
}

//    MedicalRecordHandler gerador = new MedicalRecordHandler(Session.getCurrentPatient(), "Hemograma Completo do Ânus", "Tá de boa fi");
//    File pdf = new File(gerador.GeneratePDF());
//    Desktop.getDesktop().open(pdf);
