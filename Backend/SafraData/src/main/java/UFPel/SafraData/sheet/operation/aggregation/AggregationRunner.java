package UFPel.SafraData.sheet.operation.aggregation;

import UFPel.SafraData.sheet.SheetData;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AggregationRunner {
    private final Map<String, SheetAggregation> operations;

    public AggregationRunner(List<SheetAggregation> ops) {
        this.operations = ops.stream().collect(Collectors.toMap(SheetAggregation::name, op -> op));
    }

    public Object run(String opName, SheetData sheetData, String columnName){
        SheetAggregation op = operations.get(opName);
        if(op == null){
            throw new IllegalArgumentException("Operação não encontrada: " + opName);
        }
        return op.calculate(sheetData, columnName);
    }
}
