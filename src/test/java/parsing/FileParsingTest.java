package parsing;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileParsingTest {

    private ClassLoader cl = FileParsingTest.class.getClassLoader();

    @Test
    @DisplayName("Чтение pdf файла из архива")
    void zipTest() throws Exception {
        try (InputStream is = cl.getResourceAsStream("HW_10.zip");
             ZipInputStream zs = new ZipInputStream(is)) {
            ZipEntry entry;
            while ((entry = zs.getNextEntry()) != null) {
                if (entry.getName().contains("HW_pdf.pdf")) {
                    PDF pdf = new PDF(zs);
                    Assertions.assertTrue(pdf.text.startsWith("Test"));
                }
            }
        }
    }

    @Test
    @DisplayName("Чтение xlsx файла из архива")
    void zipTest2() throws Exception {
        try (InputStream is = cl.getResourceAsStream("HW_10.zip");
             ZipInputStream zs = new ZipInputStream(is)) {
            ZipEntry entry;
            while ((entry = zs.getNextEntry()) != null) {
                if (entry.getName().contains("HW_10(xlsx).xlsx")) {
                    XLS xls = new XLS(zs);
                    Assertions.assertEquals(xls.excel.getSheetAt(0).getRow(0).getCell(0)
                            .getStringCellValue(), "HW_10 - test xlsx");
                }
            }
        }
    }

    @Test
    @DisplayName("Чтение csv файла из архива")
    void zipTest3() throws Exception {
        try (InputStream is = cl.getResourceAsStream("HW_10.zip");
             ZipInputStream zs = new ZipInputStream(is)) {
            ZipEntry entry;
            while ((entry = zs.getNextEntry()) != null) {
                if (entry.getName().contains("HW_10 - test(csc).csv")) {
                    CSVReader csvReader = new CSVReader(new InputStreamReader(zs));
                    List<String[]> csvContent = csvReader.readAll();
                    Assertions.assertArrayEquals(new String[]{"HW 10", " test csv"}, csvContent.get(0));
                }
            }
        }
    }
}
