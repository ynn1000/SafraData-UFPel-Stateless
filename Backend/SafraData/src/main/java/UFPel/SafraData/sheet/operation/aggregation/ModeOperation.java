package UFPel.SafraData.sheet.operation.aggregation;

import UFPel.SafraData.sheet.SheetData;

import java.util.List;

public class ModeOperation implements SheetAggregation{
    @Override
    public String name() {
        return "moda";
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
        return null;
    }

    private Double requireNumeric(Object value) {
        if(value instanceof Double d) return d;
        throw new IllegalArgumentException("A operação média só pode ser realizada usando valores" +
                "numéricos. Valores utilizados: " + value);
    }
}
