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

public class DxfPoint {
    double x, y, z;
    final double HEIGHT_BOX_WIDTH = 4.0;
    final double NUMBER_BOX_WIDTH = 2.0;
    final double POINT_CIRCLE_RADIUS = 0.2;
    // For offsets:
    // Signs are taken care of during string generation
    final double POINT_HEIGHT_BOX_X_OFFSET = 2 * POINT_CIRCLE_RADIUS;
    final double POINT_HEIGHT_BOX_Y_OFFSET = 0.0;
    final double POINT_NUMBER_BOX_X_OFFSET = 2 * POINT_CIRCLE_RADIUS;
    final double POINT_NUMBER_BOX_Y_OFFSET = 0.0;
    final double TEXT_HEIGHT = 0.75;
    String pointNumber;
    
    
    DxfPoint(String pointNumber, double x, double y) {
        this.pointNumber = pointNumber;
        this.x = x;
        this.y = y;
        this.z = 0d;
    }
    
    DxfPoint(String pointNumber, double x, double y, double z) {
        this.pointNumber = pointNumber;
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    String dxfPointString() {
        return
            "  0\n" +
            "POINT\n" +
            "  5\n" +
            Integer.toHexString(DxfDoc.hexEntityCounter) + "\n" +
            "100\n" +
            "AcDbEntity\n" +
            "  8\n" +
            "TACKE\n" + // Layer name
            "  6\n" +
            "ByLayer\n" +
            " 62\n" +
            "  256\n" +
            "370\n" +
            "   -1\n" +
            "100\n" +
            "AcDbPoint\n" +
            " 10\n" +
            String.valueOf(this.x) + "\n" +
            " 20\n" +   
            String.valueOf(this.y) + "\n" +
            " 30\n" +
            String.valueOf(this.z);
            // Note: we don't put \n here, but in ReaderWriter
    }
    
    String dxfPointCircleString() {
        return
            "  0\n" +
            "CIRCLE\n" +
            "  5\n" +
            Integer.toHexString(DxfDoc.hexEntityCounter) + "\n" +
            "100\n" +
            "AcDbEntity\n" +
            "  8\n" +
            "TACKE\n" + // Layer name
            "  6\n" +
            "ByLayer\n" +
            " 62\n" +
            "  256\n" +
            "370\n" +
            "   -1\n" +
            "100\n" +
            "AcDbCircle\n" +
            " 10\n" +
            String.valueOf(this.x) + "\n" +
            " 20\n" +   
            String.valueOf(this.y) + "\n" +
            " 40\n" +
            "0.2"; // Circle radius
            // Note: we don't put \n here, but in ReaderWriter
    }
    
    String dxfPointNumberString() {
        return
            "  0\n" +
            "MTEXT\n" +
            "  5\n" +
            Integer.toHexString(DxfDoc.hexEntityCounter) + "\n" +
            "100\n" +
            "AcDbEntity\n" +
            "  8\n" +
            "BROJEVI SNIMLJENIH TACAKA\n" + // Layer name
            "  6\n" +
            "ByLayer\n" +
            " 62\n" +
            "  256\n" +
            "370\n" +
            "   -1\n" +
            "100\n" +
            "AcDbMText\n" +
            " 10\n" +
            String.valueOf(this.x - POINT_NUMBER_BOX_X_OFFSET) + "\n" +
            " 20\n" +   
            String.valueOf(this.y + POINT_NUMBER_BOX_Y_OFFSET) + "\n" +
            " 30\n" +
            "0\n" + 
            " 40\n" +
            String.valueOf(TEXT_HEIGHT) + "\n" +
            " 41\n" +
            String.valueOf(NUMBER_BOX_WIDTH) + "\n" +
            " 71\n" + 
            "    9\n" + // Choice of fix point
            " 72\n" + 
            "    1\n" +
            "  1\n" + 
            String.valueOf(this.pointNumber) + "\n" + 
            "  7\n" +
            "standard\n" +
            "210\n" +
            "0\n" +
            "220\n" +
            "0\n" +
            "230\n" +
            "1\n" +
            " 50\n" +
            "0\n" +
            " 73\n" +
            "    2\n" +
            " 44\n" +
            "1";
            // Note: we don't put \n here, but in ReaderWriter
    }
    
    String dxfPointHeightString() {
        return
            "  0\n" +
            "MTEXT\n" +
            "  5\n" +
            Integer.toHexString(DxfDoc.hexEntityCounter) + "\n" +
            "100\n" +
            "AcDbEntity\n" +
            "  8\n" +
            "KOTE\n" + // Layer name
            "  6\n" +
            "ByLayer\n" +
            " 62\n" +
            "  256\n" +
            "370\n" +
            "   -1\n" +
            "100\n" +
            "AcDbMText\n" +
            " 10\n" +
            String.valueOf(this.x + POINT_HEIGHT_BOX_X_OFFSET) + "\n" +
            " 20\n" +   
            String.valueOf(this.y - POINT_HEIGHT_BOX_Y_OFFSET) + "\n" +
            " 30\n" +
            "0\n" + 
            " 40\n" +
            String.valueOf(TEXT_HEIGHT) + "\n" +
            " 41\n" +
            String.valueOf(HEIGHT_BOX_WIDTH) + "\n" +
            " 71\n" + 
            "    1\n" + // Choice of fix point
            " 72\n" + 
            "    1\n" +
            "  1\n" + 
            String.valueOf(this.z) + "\n" + 
            "  7\n" +
            "standard\n" +
            "210\n" +
            "0\n" +
            "220\n" +
            "0\n" +
            "230\n" +
            "1\n" +
            " 50\n" +
            "0\n" +
            " 73\n" +
            "    2\n" +
            " 44\n" +
            "1";
            // Note: we don't put \n here, but in ReaderWriter
    }
    
    public String getPointNumber() {
        return pointNumber;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }
    
    public void setPointNumber(String pointNumber) {
        this.pointNumber = pointNumber;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }
}

