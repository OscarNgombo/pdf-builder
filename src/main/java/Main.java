import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

import java.io.File;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {

        Logger.getLogger("org.apache.pdfbox").setLevel(Level.SEVERE);
        File outputDir = new File("out");
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        convertHtmlToPdf("p-nine-report.html", "out/Sample BNG P9 Report.pdf");
        convertHtmlToPdf("account-statements.html", "out/Sample Account Statements Report.pdf");
    }

    /**
     * Converts an HTML file from the resources folder to a PDF file.
     *
     * @param htmlResourceName The name of the HTML file located in `src/main/resources`.
     * @param outputPdfPath    The path where the generated PDF file will be saved.
     */
    private static void convertHtmlToPdf(String htmlResourceName, String outputPdfPath) {
        try {
            URL resourceUrl = Main.class.getClassLoader().getResource(htmlResourceName);
            if (resourceUrl == null) {
                System.err.println("Could not find resource: " + htmlResourceName);
                return;
            }

            File htmlFile = new File(resourceUrl.toURI());

            System.out.println("Starting PDF conversion for: " + htmlResourceName);

            try (OutputStream os = Files.newOutputStream(Paths.get(outputPdfPath))) {
                PdfRendererBuilder builder = new PdfRendererBuilder();
                builder.withFile(htmlFile);
                builder.toStream(os);
                builder.run();
            }

            System.out.println("Successfully created PDF: " + outputPdfPath);

        } catch (Exception e) {
            // IMPROVED ERROR HANDLING: Print the full stack trace for better debugging.
            System.err.println("Error during PDF conversion for " + htmlResourceName + ":");
            e.printStackTrace();
        }
    }
}