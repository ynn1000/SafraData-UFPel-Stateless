package file;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class FileService {
    public void fileHandler() throws FileNotFoundException {
        String path = "src/main/resources/PlanilhaAgro.xlsx";

        try (InputStream input = new FileInputStream(path);
             Workbook workbook = new XSSFWorkbook(input);) {
            System.out.println("arquivo carregado");

            DataFormatter formatter = new DataFormatter();
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                for (Cell cell : row) {
                    System.out.print(formatter.formatCellValue(cell) + " | ");
                }
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        new FileService().fileHandler();
    }
}
