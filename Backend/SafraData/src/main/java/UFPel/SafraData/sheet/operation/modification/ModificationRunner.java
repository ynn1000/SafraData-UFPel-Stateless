package UFPel.SafraData.sheet.operation.modification;

import UFPel.SafraData.sheet.SheetData;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ModificationRunner {
    private final Map<String, SheetModification> operations;

    public ModificationRunner(List<SheetModification> ops) {
        this.operations = ops.stream().collect(Collectors.toMap(SheetModification::name, op -> op));
    }

    public SheetData run(String opName, SheetData sheetData, ModificationParams params) {
        SheetModification op = operations.get(opName);
        if (op == null) {
            throw new IllegalArgumentException("Operação não encontrada: " + opName);
        }
        return op.apply(sheetData, params);
    }
}
