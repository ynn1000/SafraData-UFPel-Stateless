package UFPel.SafraData.sheet.operation.aggregation;

import UFPel.SafraData.sheet.SheetData;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AverageOperation implements SheetAggregation {
    @Override
    public String name() {
        return "media";
    }

    @Override
    public Object calculate(SheetData sheetData, String columnName) {
        int index = sheetData.columnIndex(columnName);

        List<Double> values = sheetData.
                rows().
                stream().
                map(row -> row.get(index)).
                map(this::requireNumeric).
                toList();

        return values.
                stream().
                mapToDouble(Double::doubleValue).
                average().
                orElse(0.0);
    }

    private Double requireNumeric(Object value) {
        if (value instanceof Double d) return d;
        throw new IllegalArgumentException("A operação média só pode ser realizada usando " +
                "valores numéricos. Valores usados: " + value);
    }
}
