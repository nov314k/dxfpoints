package dxfpointscons;


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
    
    public ArrayList<DxfPoint> readCsv(String inputCsvFileName) throws IOException {
        File fin = new File(inputCsvFileName);
        BufferedReader br = null;
        String str = null;
        String strX = null;
        String strY = null;
        DxfPoint dxfPt;
        ArrayList<DxfPoint> dxfPts = new ArrayList<>();
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(fin), "UTF-8"));
            while ((str = br.readLine()) != null) {
                strX = str.split(",")[0];
                strY = str.split(",")[1];
                dxfPt = new DxfPoint(Double.parseDouble(strX), Double.parseDouble(strY));
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
    
    public void writeDxf(String inputCsvFileName) throws IOException {
        ArrayList<DxfPoint> dxfPts = readCsv(inputCsvFileName);
        DxfDoc dd = new DxfDoc();
        String des = dd.getDxfExtentString(inputCsvFileName);
        File fin = null;
        File fout = new File(dd.getDxfOutputFileName());
        BufferedReader br = null;
        BufferedWriter bw = null;
        String str = null;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fout, false), "UTF-8"));
            // Copy Part A
            fin = new File("a.txt");
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
            fin = new File("c.txt");
            br = new BufferedReader(new InputStreamReader(new FileInputStream(fin), "UTF-8"));
            while ((str = br.readLine()) != null) {
                bw.write(str);
                bw.write("\n");
            }
            br.close();
            // Write points
            for (DxfPoint el : dxfPts) {
                bw.write(el.dxfPointString());
                bw.write("\n");
                DxfPoint.hexPointCounter++;
            }
            // Copy Part E
            fin = new File("e.txt");
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
    }
}