/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dxfpointscons;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class DxfPointsCons {

    public static void main(String[] args) throws IOException {
        String inputCsvFileName = "test.csv";
        ReaderWriter rw = new ReaderWriter();
        rw.writeDxf(inputCsvFileName);
    }   
}