package UFPel.SafraData.sheet.operation.modification;

import java.util.List;

public class ModificationParams {
    private final List<String> columnNames;

    public ModificationParams(List<String> columnNames) {
        this.columnNames = columnNames;
    }

    public String columnAt(int index){
        if(index >= columnNames.size()) throw new IllegalArgumentException("Número de colunas inválido. Número de colunas: " + columnNames.size());

        return columnNames.get(index);
    }

    public List<String> all(){
        return columnNames;
    }
}
