package UFPel.SafraData.sheet;

import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SheetService {
    public SheetData readSheet(Workbook wb){
        Sheet sheet = wb.getSheetAt(0);
        DataFormatter dataFormatter = new DataFormatter();

        List<String> header = new ArrayList<>();
        Row headerRow = sheet.getRow(0);
        for(Cell cell : headerRow){
            header.add(dataFormatter.formatCellValue(cell));
        }

        List<List<Object>> rows = new ArrayList<>();
        for(Row row : sheet){
            if(row.getRowNum() == 0 || isRowEmpty(row)) continue;
            rows.add(extractRowValues(row, header.size()));
        }

        return new SheetData(header, rows);
    }

    private boolean isRowEmpty(Row row){
        for(Cell cell : row){
            if(cell.getCellType() != CellType.BLANK) return false;
        }
        return true;
    }

    private List<Object> extractRowValues(Row row, int columnNum){
        List<Object> values = new ArrayList<>();
        for(int i = 0; i < columnNum; i++){
            values.add(extractCellValue(row.getCell(i)));
        }

        return  values;
    }

    private Object extractCellValue(Cell cell){
        if(cell == null) return null;

        return switch(cell.getCellType()){
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> DateUtil.isCellDateFormatted(cell) ?  cell.getDateCellValue() : cell.getNumericCellValue();
            case BOOLEAN -> cell.getBooleanCellValue();
            case FORMULA -> cell.getNumericCellValue();
            default -> null;
        };
    }
}
