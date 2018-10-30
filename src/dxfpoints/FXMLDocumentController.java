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
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

public class FXMLDocumentController implements Initializable {
    ArrayList<DxfPoint> dxfPts;
    @FXML
    Button btnClose = new Button();
    @FXML
    Button btnGenerateDxf = new Button();
    @FXML
    Button btnLoadCsv = new Button();
    FileChooser fileChooser = new FileChooser();
    ReaderWriter rw = new ReaderWriter(); 
    @FXML
    TextArea taContentsOfCsvFile = new TextArea();
    @FXML
    TextArea taContentsOfDxfFile = new TextArea();
    @FXML
    TextArea taStatusMessages = new TextArea();
    @FXML
    TextField tfInputCsvFileName = new TextField();
    @FXML
    TextField tfOutputCsvFileName = new TextField();
    
    @FXML
    private void handleBtnClose(ActionEvent event) throws IOException {
        System.exit(0);
    }
    
    @FXML
    private void handleBtnGenerateDxf(ActionEvent event) throws IOException {
        BufferedReader br;
        String existingStatusMessages = taStatusMessages.getText();
        String inputCsvFileName = tfInputCsvFileName.getText();
        String outputDxfFileName = tfOutputCsvFileName.getText();
        DxfDoc dd = rw.writeDxf(inputCsvFileName, outputDxfFileName);
        
        taStatusMessages.setText(existingStatusMessages + "\nDXF file generated:\n" + dd.getDxfOutputFileName());
        // TODO Change variable names here to suitable ones
        inputCsvFileName = "";
        existingStatusMessages = "";
        try {
            br = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(dd.getDxfOutputFileName()), "UTF-8"));
            while ((inputCsvFileName = br.readLine()) != null) {
                existingStatusMessages += inputCsvFileName + "\n";
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // TODO Fix this!
            //if(br != null){            
            //    br.close();
            //}
        }
        taContentsOfDxfFile.setText(existingStatusMessages);
    }
    
    @FXML
    private void handleBtnLoadCsv(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select CSV File");
        fileChooser.setInitialDirectory(new File("."));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showOpenDialog(null);
        String str1 = file.getAbsolutePath();
        tfInputCsvFileName.setText(str1);
        String esm = taStatusMessages.getText();
        taStatusMessages.setText(esm + "\nCSV file loaded:\n" + str1);
        dxfPts = rw.readCsv(str1);
        String str2 = "";
        for (DxfPoint el: dxfPts) {
            // str2 += "#";
            str2 += el.getPointNumber();
            str2 += ": xyz= ";
            str2 += el.getX() + ", ";
            str2 += el.getY() + ", ";
            str2 += el.getZ() + "\n";
        }
        // TODO Make it so it reads from the CSV text area
        taContentsOfCsvFile.setText(str2);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                tfInputCsvFileName.requestFocus();
            }
        });
    }    
}
