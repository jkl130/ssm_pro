package cn.sfturing.entity;

/**
 * @author anjsh
 * @date 2021/10/21 14:41
 */
public class OrderDateAndTime {
    private String transactDate;

    private boolean s;

    private boolean z;

    private boolean x;

    public boolean isS() {
        return s;
    }

    public void setS(boolean s) {
        this.s = s;
    }

    public boolean isZ() {
        return z;
    }

    public void setZ(boolean z) {
        this.z = z;
    }

    public boolean isX() {
        return x;
    }

    public void setX(boolean x) {
        this.x = x;
    }

    public String getTransactDate() {
        return transactDate;
    }

    public void setTransactDate(String transactDate) {
        this.transactDate = transactDate;
    }
}
