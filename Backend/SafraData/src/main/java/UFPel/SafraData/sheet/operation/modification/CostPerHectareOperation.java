package UFPel.SafraData.sheet.operation.modification;

import UFPel.SafraData.sheet.SheetData;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CostPerHectareOperation implements SheetModification {

    @Override
    public String name() {
        return "custo-por-hectare";
    }

    @Override
    public SheetData apply(SheetData sheetData, ModificationParams params) {
        String columnX = params.columnAt(0);
        String columnY = params.columnAt(1);
        int indexX = sheetData.columnIndex(columnX);
        int indexY = sheetData.columnIndex(columnY);

        double[] numerator = sheetData
                .rows()
                .stream()
                .map(row -> row.get(indexX))
                .map(this::requireNumeric)
                .mapToDouble(Double::doubleValue)
                .toArray();

        double[] denominator = sheetData
                .rows()
                .stream()
                .map(row -> row.get(indexY))
                .map(this::requireNumeric)
                .mapToDouble(Double::doubleValue)
                .toArray();

        List<Object> values = new ArrayList<>();
        for(int i = 0; i <= numerator.length; i++) {
            values.add(numerator[i] / denominator[i]);
        }

        List<List<Object>> rows = new ArrayList<>();

        for(int i = 0; i < sheetData.rows().size(); i++) {
            List<Object> row = sheetData.rows().get(i);
            List<Object> newRow = new ArrayList<>(row);
            newRow.add(values.get(i));
            rows.add(newRow);
        }

        List<String> headers = new ArrayList<>(sheetData.headers());
        headers.add("Custo por Hectare");

        return new SheetData(headers, rows);
    }

    private Double requireNumeric(Object value) {
        if(value instanceof Double d) return d;
        throw new IllegalArgumentException("A operação só pode ser realizada com valores numéricos. Valor fornecido: " + value);
    }
}
