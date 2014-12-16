package chord;

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

public class Fig1 {

	public static void main(String[] args)
            throws BiffException, IOException, WriteException {
        WritableWorkbook wworkbook;
        wworkbook = Workbook.createWorkbook(new File("fig1.xls"));
        
        WritableSheet wsheet = wworkbook.createSheet("First Sheet", 0);
        for (int i = 0; i < 40; i++) {
        	int percent = i+1;
        	Ring ring = new Ring(10000, 100*percent, 5);
        	float successfulQueryPercent = ring.runQueries(100000);
            wsheet.addCell(new Number(0, i, percent));
            wsheet.addCell(new Number(1, i, successfulQueryPercent));
            System.out.println("done for " + percent + "%");
        }
        wworkbook.write();
        wworkbook.close();
    }
}
