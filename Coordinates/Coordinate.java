import java.util.*;

/**
 * convert the coordinate from various forms into degree decimal
 *
 * @author Minh Tran
 */
public class Coordinate{
    private static double latitude;
    private static double longtitude;
    private static Boolean valid;

    public static void main(String[] args) {

        List<String> strLatitude = new ArrayList<String>();
        List<String> strLongtitude = new ArrayList<String>();

        final Boolean DEBUG = false;

        Scanner sc = new Scanner(System.in);
        while(sc.hasNextLine()){
            String inputLine = sc.nextLine();

            if (DEBUG)
                System.out.println(inputLine);

            valid = true;

            int north = (inputLine.indexOf('N')!=-1)? inputLine.indexOf('N'):inputLine.indexOf('n');
            int south = (inputLine.indexOf('S')!=-1)? inputLine.indexOf('S'):inputLine.indexOf('u');
            int east = (inputLine.indexOf('E')!=-1)? inputLine.indexOf('E'):inputLine.indexOf('a');
            int west = (inputLine.indexOf('W')!=-1)? inputLine.indexOf('W'):inputLine.indexOf('w');


            Boolean negatives= inputLine.indexOf('-')!=-1;
            Boolean directional= (north!=-1 || south!=-1);
            Boolean dms=((inputLine.indexOf('d')!=-1) || (inputLine.indexOf('m')!=-1));
            Boolean dms_symbol=((inputLine.indexOf('°')!=-1) || (inputLine.indexOf('′')!=-1));

            latitude = 0;
            longtitude = 0;

            if (directional && negatives) {
                valid = false;
            }

            if(dms){
                int s1 = (inputLine.indexOf("s ")!=-1)? (inputLine.indexOf("s ")+1):(inputLine.indexOf('m')+1);

                String[] coordinate = {inputLine.substring(0,s1), inputLine.substring(s1)};
                for(String str : coordinate){
                    int d = str.indexOf('d');
                    int m = str.indexOf('m');
                    int s = str.indexOf("s ");

                    if(d == -1 || m == -1){
                        valid = false;
                    }

                    if(s!= -1 && !(d<m && m<s)){
                        valid = false;
                    }

                    if(s == -1 && d>(m-1)){
                        valid = false;
                    }

                    if(str.indexOf('-') > d){
                        valid = false;
                    }
                }
            }


            if(dms_symbol){
                int s1 = (inputLine.indexOf('″')!=-1)? (inputLine.indexOf('″')+1):(inputLine.indexOf('′')+1);

                String[] coordinate = {inputLine.substring(0,s1), inputLine.substring(s1)};
                for(String str : coordinate){
                    int d = str.indexOf('°');
                    int m = str.indexOf('′');
                    int s = str.indexOf('″');

                    if(d == -1 || m == -1){
                        valid = false;
                    }

                    if(s!= -1 && !(d<m && m<s)){
                        valid = false;
                    }

                    if(s == -1 && d>(m-1)){
                        valid = false;
                    }

                    if(str.indexOf('-') > d){
                        valid = false;
                    }
                }
            }

            if (valid){
                List<String> strCoor = getCoor(inputLine);

                if (strCoor.size() %2 ==0 && strCoor.size()>0){
                    int indNS = (north!=-1)? north:south;
                    int indEW = (east!=-1)? east:west;
                    Boolean longFirst = indNS > indEW;

                    int midpoint = strCoor.size()/2;

                    String temp = new String();

                    if(south!=-1){
                        if (longFirst){
                            temp = '-' + strCoor.get(midpoint);
                            strCoor.set(midpoint, temp);
                        } else {
                            temp = '-' + strCoor.get(0);
                            strCoor.set(0, temp);
                        }
                    }

                    if(west!=-1){
                        if (!longFirst){
                            temp = '-' + strCoor.get(midpoint);
                            strCoor.set(midpoint, temp);
                        } else {
                            temp = '-' + strCoor.get(0);
                            strCoor.set(0, temp);
                        }
                    }


                    for(int i =0; i < strCoor.size(); i++){
                        if (i < midpoint){
                            if (longFirst) strLongtitude.add(strCoor.get(i));
                            else strLatitude.add(strCoor.get(i));
                        } else{
                            if (longFirst) strLatitude.add(strCoor.get(i));
                            else strLongtitude.add(strCoor.get(i));
                        }
                    }

                } else {
                    valid = false;
                }

                strCoor.clear();
            }

            validCoor(strLatitude);
            validCoor(strLongtitude);

            if(valid){
                latitude = calcCoor(strLatitude);
                longtitude = calcCoor(strLongtitude);

                if (!(latitude > -90 && latitude <= 90))
                    valid = false;

                if(!(longtitude > -180 && longtitude <= 180)){
                    while (!(-180 < longtitude && longtitude <= 180)){
                        if (longtitude <0)
                            longtitude+=360;
                        else
                            longtitude-=360;
                    }
                }
            }

            if (valid){
                System.out.println(String.format("%.6f",latitude) + " " + String.format("%.6f",longtitude));
            } else {
                System.out.println("Unable to process: " + inputLine);
            }
            
            if (DEBUG) System.out.println("---------------------------------------");

            strLatitude.clear();
            strLongtitude.clear();
        }
        
        sc.close();
    }

    /**
     * getCoor method-Extract the coordinate String into a list contains coordinate components
     *
     * @param str original input line to extract into the list
     *
     * @return List coordinate number compiled into a list after extracted from the input
     */
    private static List<String> getCoor(String str){
        //Call reformatCoor to change the inputLine to coordinate String
        str = reformatCoor(str);

        String[] arrCoor = str.trim().split(" ");

        List<String> lsCoor = new ArrayList<String>();
        for(String strCoor:arrCoor){
            if (!strCoor.equals("")){
                lsCoor.add(strCoor);
            }
        }

        return lsCoor; 
    }

    /**
     * reformatCoor method-Extract the input line into coordinate string by
     * removing unneccessary character
     *
     * @param str original String from input line
     *
     * @return String the reformatted inputline into coordinate string
     */
    private static String reformatCoor(String str){
        String coor = "";

        for (int i = 0; i < str.length(); i++){
            char c = str.charAt(i);
            if (c==' ' || c=='.' || c=='-' || (c>='0' && c<='9'))
                coor = coor + c;
        }

        return coor;
    }

    /**
     * validCoor method-Further checking the input form after dividing them into a list of number
     * Eliminate the error of having more numbers or no numbers in the input line.
     *
     * @param lsCoor coordinate number compiled into a list after extracted from the input
     */
    private static void validCoor(List<String> lsCoor){
        //eliminate the error where there are more numbers than in form dms
        if (lsCoor.size()>3)
            valid = false;

        //Eliminate the error where no number is taken
        if (lsCoor.isEmpty())
            valid = false;

        //Eliminate error of neagative sign on the minutes and seconds
        for (int j = 1; j < lsCoor.size(); j++){
            String str = lsCoor.get(j);
            if (str.indexOf('-')!=-1)
                valid = false;
        }
    }

    /**
     * calcCoor method-Convert the dms formate to the degree decimal place
     *
     * @param lsCoor coordinate number compiled into a list after extracted from the input
     *
     * @return double the degree decimal vaue of the coordinate
     */
    private static double calcCoor(List<String> lsCoor){
        double[] dblCoor = new double[lsCoor.size()];
        int j = 1;
        double coor = 0;
        int sign = 1; // To subtract instead of add when it is negative

        for (int i = 0; i < lsCoor.size(); i++){
            String str = lsCoor.get(i);
            dblCoor[i] = Double.parseDouble(str);
            dblCoor[i] = dblCoor[i]/j;
            coor += sign * dblCoor[i];

            sign = (dblCoor[0] >= 0)? 1:-1;
            j = j*60;
        }
        return coor;
    }
}

