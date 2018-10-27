/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dxfpointscons;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class DxfDoc {
    private String dxfOutputFileName = "";
    
    DxfDoc() {
        Calendar calCurrent = Calendar.getInstance(TimeZone.getTimeZone("Europe/Belgrade"));
        this.dxfOutputFileName += 
                calCurrent.get(Calendar.YEAR) + "-" +
                calCurrent.get(Calendar.MONTH) + "-" +
                calCurrent.get(Calendar.DAY_OF_MONTH) + "-" +
                calCurrent.get(Calendar.HOUR) + "-" +
                calCurrent.get(Calendar.MINUTE) + "-" +
                // TODO Change so it incorporates input CSV file name
                "points.dxf";          
    }

    public String getDxfExtentString(String inputCsvFileName) throws IOException {
        ReaderWriter rw = new ReaderWriter();
        ArrayList<DxfPoint> dxfPts = rw.readCsv(inputCsvFileName);
        // Assumes there are points in CSV file
        double minX = dxfPts.get(0).getX();
        double minY = dxfPts.get(0).getY();
        double maxX = dxfPts.get(0).getX();
        double maxY = dxfPts.get(0).getY();
        for (DxfPoint it : dxfPts) {
            if (it.getX() < minX) minX = it.getX();
            if (it.getY() < minY) minY = it.getY();
            if (it.getX() > maxX) maxX = it.getX();
            if (it.getY() > maxY) maxY = it.getY();
        }
        return 
            "$EXTMIN\n" + " 10\n" +
            String.valueOf(minX) + "\n" + " 20\n" +
            String.valueOf(minY) + "\n" +
            " 30\n" + "0\n" + "  9\n" + "$EXTMAX\n" + " 10\n" +
            String.valueOf(maxX) + "\n" + " 20\n" + 
            String.valueOf(maxY) + "\n" + " 30\n" + "0";
    }

    public String getDxfOutputFileName() {
        return dxfOutputFileName;
    }
}
