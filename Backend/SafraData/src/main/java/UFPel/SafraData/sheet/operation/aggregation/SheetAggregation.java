package UFPel.SafraData.sheet.operation.aggregation;

import UFPel.SafraData.sheet.SheetData;

public interface SheetAggregation {
    String name();
    Object calculate(SheetData sheetData, String columnName);
}
