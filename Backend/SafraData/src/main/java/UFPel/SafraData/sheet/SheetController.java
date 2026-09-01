package UFPel.SafraData.sheet;

import UFPel.SafraData.file.FileService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController()
@RequestMapping("/sheet")
public class SheetController {
    private final FileService fileService;
    private final SheetService sheetService;

    public SheetController(FileService fileService, SheetService sheetService) {
        this.fileService = fileService;
        this.sheetService = sheetService;
    }

    @PostMapping("/upload")
    public ResponseEntity<SheetData> uploadSheet(@RequestParam("file") MultipartFile file) throws IOException {
        Workbook wb = fileService.openFile(file);
        SheetData data = sheetService.readSheet(wb);

        return ResponseEntity.ok(data);
    }
}
