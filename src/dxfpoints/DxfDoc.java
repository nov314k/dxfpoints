/* 
 * Copyright (C) 2018 Novak Petrovic
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package dxfpoints;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class DxfDoc {
    // TODO Put this in settings file (and all other settings)
    final double EXTENTS_MARGIN = 1.5;
    public static int hexEntityCounter = 0x50;
    private String dxfOutputFileName = "";
    
    DxfDoc() {
        Calendar calCurrent = Calendar.getInstance(TimeZone.getTimeZone("Europe/Belgrade"));
        // TODO Solve the problem with values < 10 and padding zero
        int month = calCurrent.get(Calendar.MONTH) + 1;
        this.dxfOutputFileName += 
                calCurrent.get(Calendar.YEAR) + "-" +
                month + "-" +
                calCurrent.get(Calendar.DAY_OF_MONTH) + "-" +
                calCurrent.get(Calendar.HOUR_OF_DAY) + "-" +
                calCurrent.get(Calendar.MINUTE) + "-" +
                // TODO Maybe change so it incorporates input CSV file name
                "points.dxf";          
    }
    
    DxfDoc(String dxfOutputFileName) {
        this.dxfOutputFileName = dxfOutputFileName;          
    }

    public String getDxfExtentsString(String inputCsvFileName) throws IOException {
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
            "  9\n" + 
            "$EXTMIN\n" +
            " 10\n" +
            String.valueOf(minX - EXTENTS_MARGIN) + "\n" +
            " 20\n" +
            String.valueOf(minY - EXTENTS_MARGIN) + "\n" +
            " 30\n" +
            "0\n" +
            "  9\n" +
            "$EXTMAX\n" +
            " 10\n" +
            String.valueOf(maxX + EXTENTS_MARGIN) + "\n" +
            " 20\n" + 
            String.valueOf(maxY + EXTENTS_MARGIN) + "\n" +
            " 30\n" +
            "0";
            // Note: we don't put \n here, but in ReaderWriter
    }

    public String getDxfOutputFileName() {
        return dxfOutputFileName;
    }
    
    public void setDxfOutputFileName(String dxfOutputFileName) {
        this.dxfOutputFileName = dxfOutputFileName;
    }
}
