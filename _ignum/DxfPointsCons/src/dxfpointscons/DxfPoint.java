package dxfpointscons;

public class DxfPoint {
    double x, y, z;
    public static int hexPointCounter = 0x4D;
    
    DxfPoint(double x, double y) {
        this.x = x;
        this.y = y;
        this.z = 0d;
    }
    
    DxfPoint(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    String dxfPointString() {
        return
            "  0\n" +
            "POINT\n" +
            "  5\n" +
            Integer.toHexString(hexPointCounter) + "\n" +
            "100\n" +
            "AcDbEntity\n" +
            "  8\n" +
            "0\n" +
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
            String.valueOf(this.y);
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

