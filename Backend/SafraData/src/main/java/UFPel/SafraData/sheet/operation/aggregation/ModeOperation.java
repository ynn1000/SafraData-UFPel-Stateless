package UFPel.SafraData.sheet.operation.aggregation;

import UFPel.SafraData.sheet.SheetData;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
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

        Map<Double, Long> frequencies = values.stream()
                .collect(Collectors.groupingBy(v -> v, Collectors.counting()));

        long maxFrequency = frequencies.values().stream()
                .mapToLong(Long::longValue)
                .max()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Não há valores para calcular a moda da coluna. Coluna fornecida: " + columnName));

        List<Double> modes = frequencies.entrySet().stream()
                .filter(entry -> entry.getValue() == maxFrequency)
                .map(Map.Entry::getKey)
                .sorted()
                .toList();


        return modes.size() == 1 ? modes.get(0) : modes;
    }

    private Double requireNumeric(Object value) {
        if(value instanceof Double d) return d;
        throw new IllegalArgumentException("A operação moda só pode ser realizada usando valores" +
                "numéricos. Valores utilizados: " + value);
    }
}
