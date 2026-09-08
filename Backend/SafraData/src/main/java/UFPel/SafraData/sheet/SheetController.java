package UFPel.SafraData.sheet;

import UFPel.SafraData.file.FileService;
import UFPel.SafraData.sheet.operation.aggregation.AggregationRunner;
import UFPel.SafraData.sheet.operation.modification.ModificationParams;
import UFPel.SafraData.sheet.operation.modification.ModificationRunner;
import org.apache.coyote.Response;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController()
@RequestMapping("/sheet")
public class SheetController {
    private final FileService fileService;
    private final SheetService sheetService;
    private final AggregationRunner aggregationRunner;
    private final ModificationRunner modificationRunner;

    public SheetController(FileService fileService,
                           SheetService sheetService,
                           AggregationRunner aggregationRunner,
                           ModificationRunner modificationRunner) {
        this.fileService = fileService;
        this.sheetService = sheetService;
        this.aggregationRunner = aggregationRunner;
        this.modificationRunner = modificationRunner;
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

    @PostMapping("/modify")
    public ResponseEntity<SheetData> modify(@RequestParam("operation") String operation,
                                            @RequestParam("columns") List<String> columns,
                                            @RequestBody SheetData data){
        SheetData result = modificationRunner.run(operation, data, new ModificationParams(columns));
        return ResponseEntity.ok(result);
    }
}
