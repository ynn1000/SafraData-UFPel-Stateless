package UFPel.SafraData.sheet;

import UFPel.SafraData.file.FileService;
import UFPel.SafraData.sheet.operation.aggregation.AggregationRunner;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController()
@RequestMapping("/sheet")
public class SheetController {
    private final FileService fileService;
    private final SheetService sheetService;
    private final AggregationRunner aggregationRunner;

    public SheetController(FileService fileService, SheetService sheetService, AggregationRunner aggregationRunner) {
        this.fileService = fileService;
        this.sheetService = sheetService;
        this.aggregationRunner = aggregationRunner;
    }

    @PostMapping("/upload")
    public ResponseEntity<SheetData> uploadSheet(@RequestParam("file") MultipartFile file) throws IOException {
        Workbook wb = fileService.openFile(file);
        SheetData data = sheetService.readSheet(wb);

        return ResponseEntity.ok(data);
    }

    @PostMapping("/aggregate")
    public ResponseEntity<Object> aggregate(@RequestParam("operation") String operation,
                                            @RequestParam("column") String column,
                                            @RequestBody SheetData data){
        Object result = aggregationRunner.run(operation, data, column);
        return  ResponseEntity.ok(result);
    }
}
