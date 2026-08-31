package file;

import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;

@Service
public class FileService {
    public void validateFile(MultipartFile file){
        if(file.isEmpty()){
            throw new IllegalArgumentException("Arquivo inválido.");
        }

        String fileName = file.getOriginalFilename();
        boolean isFilenameValid = fileName != null && (fileName.toLowerCase().endsWith(".xls") || fileName.toLowerCase().endsWith(".xlsx"));

        if(!isFilenameValid){
            throw new IllegalArgumentException("Apenas arquivos com extensão .xls ou .xlsx são aceitos.");
        }
    }

    public Workbook openFile(MultipartFile file){
        validateFile(file);
        try(InputStream input = file.getInputStream()){
            return WorkbookFactory.create(input);
        } catch(IOException e){
            throw new RuntimeException("Erro ao abrir o arquivo: " + e.getMessage(), e);
        }
    }
}
