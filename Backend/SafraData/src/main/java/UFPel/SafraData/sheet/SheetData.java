package UFPel.SafraData.sheet;

import java.util.List;

public record SheetData(List<String> headers, List<List<Object>> rows) {
    public int columnIndex(String columnName){
        int index = headers.indexOf(columnName);
        if(index == -1){
            throw new IllegalArgumentException("Coluna não encontrada: "  + columnName);
        }

        return index;
    }
}
