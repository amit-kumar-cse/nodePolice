package chord;



/**
 *
 * @author Anuj Garg
 */
import java.io.File;
import java.io.IOException;

//import jxl.Cell;
//import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
//import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class ExcelDemo {

    public static void main(String[] args)
            throws BiffException, IOException, WriteException {
        WritableWorkbook wworkbook;
        wworkbook = Workbook.createWorkbook(new File("output.xls"));
        
        WritableSheet wsheet = wworkbook.createSheet("First Sheet", 0);
        for (int i = 0; i < 100; i++) {
            wsheet.addCell(new Number(0, i, i));
            wsheet.addCell(new Number(1, i, 100-i));
        }
        wworkbook.write();
        wworkbook.close();
            
        /*
        Workbook workbook = Workbook.getWorkbook(new File("output.xls"));
        Sheet sheet = workbook.getSheet(0);
        Cell cell1 = sheet.getCell(1, 2);
        System.out.println(cell1.getContents());
        Cell cell2 = sheet.getCell(3, 4);
        System.out.println(cell2.getContents());
        workbook.close();
                */
    }
}

