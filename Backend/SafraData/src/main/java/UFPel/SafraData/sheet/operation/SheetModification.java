package UFPel.SafraData.sheet.operation;

import UFPel.SafraData.sheet.SheetData;

public interface SheetModification {
    String name();
    SheetData apply(SheetData sheetData);
}
