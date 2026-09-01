package UFPel.SafraData.sheet;

import java.util.List;

public record SheetData(List<String> headers, List<List<Object>> rows) {}
