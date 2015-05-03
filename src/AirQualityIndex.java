import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * Created by lenovo on 3/31/2015.
 */


public class AirQualityIndex {


    /*
    * USAGE
    * Pass an array of doubles of size 8 into the constructor
    * lets call this array arr for now
    * arr[0] represents conc of PM 10 (enter -1 if data not available)
    * arr[1] represents conc of PM 2.5 (enter -1 if data not available)
    * arr[2] represents conc of N02 (enter -1 if data not available)
    * arr[3] represents conc of O3 (enter -1 if data not available)
    * arr[4] represents conc of C0 (enter -1 if data not available)
    * arr[5] represents conc of S02 (enter -1 if data not available)
    * arr[6] represents conc of NH3 (enter -1 if data not available)
    * arr[7] represents conc of Pb (enter -1 if data not available)
    *
    * all the calculation is done using private helper methods. So you don't have to care about calculations.
    *
    * call getAqiResult(). This function would return reference to an array of size 8. This array contains aqi for each gas specified in "arr" (order mentioned above).
    * In case conc provided for any gas is -1 then aqi is also -1;
    *
    *
    *
    * */

    // ARRAY LIST FOR RECEIVING DATA
    private double[] dataList = null;

    //result array
    private double[] aqiResult = null;

    // ARRAY LIST FOR HOLDING AQI INTERVALS AND CONCENTRATION INTERVALS
    public static final int POLLUTANTCOUNT = 8;
    private static List aqiInterval = new ArrayList<Pair>(POLLUTANTCOUNT);
    private static Pair[][] concentration = new Pair[6][8];

    //it is advisable to call this static function  while activity is starting i.e. call this in onStart().
    public static void init()
    {
        /* 1st row */
        concentration[0][0] = new Pair(0,50);
        concentration[0][1] = new Pair(0,30);
        concentration[0][2] = new Pair(0,40);
        concentration[0][3] = new Pair(0,50);
        concentration[0][4] = new Pair(0,1.0);
        concentration[0][5] = new Pair(0,40);
        concentration[0][6] = new Pair(0,200);
        concentration[0][7] = new Pair(0,0.5);

        //2nd row
        concentration[1][0] = new Pair(51,100);
        concentration[1][1] = new Pair(31,60);
        concentration[1][2] = new Pair(41,80);
        concentration[1][3] = new Pair(51,100);
        concentration[1][4] = new Pair(1.1,2.0);
        concentration[1][5] = new Pair(41,80);
        concentration[1][6] = new Pair(201,400);
        concentration[1][7] = new Pair(0.5,1.0);

        //3rd row
        concentration[2][0] = new Pair(101,250);
        concentration[2][1] = new Pair(61,90);
        concentration[2][2] = new Pair(81,180);
        concentration[2][3] = new Pair(101,168);
        concentration[2][4] = new Pair(2.1,10);
        concentration[2][5] = new Pair(81,380);
        concentration[2][6] = new Pair(401,800);
        concentration[2][7] = new Pair(1.1,2.0);

        //4th row
        concentration[3][0] = new Pair(251,350);
        concentration[3][1] = new Pair(91,120);
        concentration[3][2] = new Pair(181,280);
        concentration[3][3] = new Pair(169,208);
        concentration[3][4] = new Pair(10,17);
        concentration[3][5] = new Pair(381,800);
        concentration[3][6] = new Pair(801,1200);
        concentration[3][7] = new Pair(2.1,3.0);
        //5th row
        concentration[4][0] = new Pair(351,430);
        concentration[4][1] = new Pair(121,250);
        concentration[4][2] = new Pair(281,400);
        concentration[4][3] = new Pair(209,748);
        concentration[4][4] = new Pair(17,34);
        concentration[4][5] = new Pair(801,1600);
        concentration[4][6] = new Pair(1200,1800);
        concentration[4][7] = new Pair(3.1,3.5);
        //6th row
        concentration[5][0] = new Pair(430,Double.POSITIVE_INFINITY);
        concentration[5][1] = new Pair(250,Double.POSITIVE_INFINITY);
        concentration[5][2] = new Pair(400,Double.POSITIVE_INFINITY);
        concentration[5][3] = new Pair(748,Double.POSITIVE_INFINITY);
        concentration[5][4] = new Pair(34,Double.POSITIVE_INFINITY);
        concentration[5][5] = new Pair(1600,Double.POSITIVE_INFINITY);
        concentration[5][6] = new Pair(1800,Double.POSITIVE_INFINITY);
        concentration[5][7] = new Pair(3.5,Double.POSITIVE_INFINITY);

        /* Hard Coding Data AQI */
        aqiInterval.add(new Pair(0.0, 50.0));
        aqiInterval.add(new Pair(51,100));
        aqiInterval.add(new Pair(101, 200));
        aqiInterval.add(new Pair(201, 300));
        aqiInterval.add(new Pair(301, 400));
        aqiInterval.add(new Pair(401, 500));
    }


    public AirQualityIndex(double[] dataList) {
        init();
        this.dataList = dataList;
        aqiResult = new double[this.POLLUTANTCOUNT];
        this.calculate(this.dataList);
    }


    //calculating aqi for a list of pollutants
    private void calculate(double[] dataList)
    {
        for (int i = 0; i < this.POLLUTANTCOUNT; i++)
        {
            if (dataList[i] >= 0.0)
            {
                aqiResult[i] = aqi(dataList[i], i);
            }
            else
            {
                aqiResult[i] = -1;
            }
        }
    }


    //function for calculating aqi for a pollutant
    private double aqi(double conc, int j)
    {
        Pair concDiff = null;
        int i;
        for (i = 0; i < 6; i++)
        {
            concDiff = concentration[i][j];
            if (concDiff.inRange(conc)){
                break;
            }
        }

        Pair aqiDiff = (Pair)aqiInterval.get(i);

        //actual calculation
        double ans = ( (aqiDiff.getDifference() / concDiff.getDifference()) * (conc - concDiff.getLow()) ) + aqiDiff.getLow();

        return ans;
    }

    public double[] getAqiResult()
    {
        return this.aqiResult;
    }
}