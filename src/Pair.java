/**
 * Created by lenovo on 3/31/2015.
 */
public class Pair{
    private double high;
    private double low;

    public Pair(double low, double high) {
        this.high = high;
        this.low = low;
    }

    public double getHigh() {
        return high;
    }

    public double getLow() {
        return low;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public void setLow(double low) {
        this.low = low;
    }

    boolean inRange(double a)
    {
        if (a >= this.low && a <= this.high) return true;
        return false;
    }

    public double getDifference(){
        return this.high - this.low;
    }

}
