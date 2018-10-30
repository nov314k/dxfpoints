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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class ReaderWriter {
    
    final String PART_A = "partA.txt";
    final String PART_C = "partC.txt";
    final String PART_E = "partE.txt";
    
    public ArrayList<DxfPoint> readCsv(String inputCsvFileName) throws IOException {
        ArrayList<DxfPoint> dxfPts = new ArrayList<>();
        BufferedReader br = null;
        DxfPoint dxfPt;
        File fin = new File(inputCsvFileName);
        String str = null;
        String strN = null;
        String strX = null;
        String strY = null;
        String strZ = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(fin), "UTF-8"));
            while ((str = br.readLine()) != null) {
                strN = str.split(",")[0];
                strX = str.split(",")[1];
                strY = str.split(",")[2];
                strZ = str.split(",")[3];
                dxfPt = new DxfPoint(
                        strN,
                        Double.parseDouble(strX),
                        Double.parseDouble(strY),
                        Double.parseDouble(strZ));
                dxfPts.add(dxfPt);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(br != null){            
                br.close();
            }
        }    
        return dxfPts;
    }
    
    public DxfDoc writeDxf(String inputCsvFileName, String outputDxfFileName) throws IOException {
        ArrayList<DxfPoint> dxfPts = readCsv(inputCsvFileName);
        BufferedReader br = null;
        BufferedWriter bw = null;
        DxfDoc dd;
        File fin = null;
        File fout;
        
        if (outputDxfFileName.isEmpty()) {
            dd = new DxfDoc();    
        } else {
            // .dxf extension is automatically added
            dd = new DxfDoc(outputDxfFileName + ".dxf");
        }
        fout = new File(dd.getDxfOutputFileName());
        String des = dd.getDxfExtentsString(inputCsvFileName);
        String str = null;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fout, false), "UTF-8"));
            // Copy Part A
            fin = new File(PART_A);
            br = new BufferedReader(new InputStreamReader(new FileInputStream(fin), "UTF-8"));
            while ((str = br.readLine()) != null) {
                bw.write(str);
                bw.write("\n");
            }
            br.close();
            // Write DXF Extents String
            bw.write(des);
            bw.write("\n");
            // Copy Part C
            fin = new File(PART_C);
            br = new BufferedReader(new InputStreamReader(new FileInputStream(fin), "UTF-8"));
            while ((str = br.readLine()) != null) {
                bw.write(str);
                bw.write("\n");
            }
            br.close();
            // Write points and two text boxes
            for (DxfPoint el : dxfPts) {
                // Points
                bw.write(el.dxfPointString());
                bw.write("\n");
                DxfDoc.hexEntityCounter++;
                // Circles are no longer required
                //bw.write(el.dxfPointCircleString());
                //bw.write("\n");
                //DxfDoc.hexEntityCounter++;
                // Point Numbers
                bw.write(el.dxfPointNumberString());
                bw.write("\n");
                DxfDoc.hexEntityCounter++;
                // Point Heights
                bw.write(el.dxfPointHeightString());
                bw.write("\n");
                DxfDoc.hexEntityCounter++;
            }
            // Copy Part E
            fin = new File(PART_E);
            br = new BufferedReader(new InputStreamReader(new FileInputStream(fin), "UTF-8"));
            while ((str = br.readLine()) != null) {
                bw.write(str);
                bw.write("\n");
            }
            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(br != null){            
                br.close();
            }
            if(bw != null){
                bw.close();
            }
        }
        return dd;
    }
}