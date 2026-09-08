package UFPel.SafraData.sheet.operation.modification;

import UFPel.SafraData.sheet.SheetData;

public interface SheetModification {
    String name();
    SheetData apply(SheetData sheetData, ModificationParams modificationParams);
}
