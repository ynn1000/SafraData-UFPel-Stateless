package UFPel.SafraData.sheet.operation.aggregation;

import UFPel.SafraData.sheet.SheetData;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.util.List;

public class MedianOperation implements SheetAggregation{
    @Override
    public String name() {
        return "mediana";
    }

    @Override
    public Object calculate(SheetData sheetData, String columnName) {
        int index = sheetData.columnIndex(columnName);
        DescriptiveStatistics stats = new DescriptiveStatistics();

        List<Double> values = sheetData.
                rows().
                stream().
                map(row -> row.get(index)).
                map(this::requireNumeric).
                toList();

        values.forEach(stats::addValue);

        return stats.getPercentile(50.0);
    }

    private Double requireNumeric(Object value) {
        if(value instanceof Double d) return d;
        throw new IllegalArgumentException("A operação mediana só pode ser realizada com valores " +
                "numéricos. Valores utilizados: " + value);
    }
}
