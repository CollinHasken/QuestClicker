package com.tophattiger.Helper.Data;

import java.text.DecimalFormat;

/**
 * Created by Collin on 7/30/2015.
 */
public class Gold {

    private static double gold, displayNumber;
    private static char first;
    private static String hundreds,tens,ones,suffix;
    private static DecimalFormat formater = new DecimalFormat("#.##");

    static public void setGold(double _gold){
        gold = _gold;
    }

    static public double getGold(){return gold;}

    static public Boolean isGreater(double amount){return gold>amount;}

    static public Boolean isGreater(int amount){return gold>amount;}

    static public Boolean isGreaterEqual(double amount){return gold>=amount;}

    static public Boolean isGreaterEqual(int amount){return gold>=amount;}

    static public Boolean isLess(double amount){return gold<amount;}

    static public Boolean isLess(int amount){return gold<amount;}

    static public Boolean isLessEqual(double amount){return gold<=amount;}

    static public Boolean isLessEqual(int amount){return gold<=amount;}

    static public void subtract(double amount){gold -= amount;}

    static public void subtract(int amount){gold -= amount;}

    static public void add(double amount){
        gold += amount;
        DataManagement.JsonData.goldCollected += amount;
        DataManagement.JsonData.idleGoldCollected += amount;
    }

    static public void add(int amount){
        gold += amount;
        DataManagement.JsonData.goldCollected += amount;
    }

    /**
     * Get the gold with the proper suffix.
     * @return String with the gold amount and suffix
     */
    static public String getGoldWithSuffix(){
        return getNumberWithSuffix(gold);
    }

    /**
     * Convert number to be a number with the correct suffix
     * @param number Number to turn into suffix
     * @return String with the number and suffix
     */
    static public String getNumberWithSuffix(int number){
        return getNumberWithSuffix((double) number);
    }

    /**
     * Convert number to be a number with the correct suffix
     * @param _number Number to turn into suffix
     * @return String with the number and suffix
     */
    static public String getNumberWithSuffix(double _number) {
        suffix = getSuffix(_number);
        if (!suffix.isEmpty()) {
            first = Character.toUpperCase(suffix.charAt(0));           //Make first letter capitalized
            if (!suffix.equals("K"))
                suffix = first + suffix.substring(1) + "llion";     //Add the suffix and llion if the suffix is not just K
            suffix = " " + suffix;
        }
        String number = formater.format(displayNumber);
        if (number.indexOf(".") > 1){
            number = number.substring(0, 6 - number.indexOf("."));
        }
        return number + suffix;
    }

    static public String getSuffix(double number){
        hundreds = tens = ones = "";            //Reset prefixes
        displayNumber = number;
        if(number < 1000)
            return "";                          //If no suffix is required, return empty
        if(number < 1000000){
            displayNumber /= 1000;              //Return K if the number is less than 1 million
            return "K";
        }
        displayNumber /= 1000000;               //Number is greater than 1 million
        int i = 1;
        while(displayNumber /1000 >=1){               //Count amount of 000's
            i ++;
            displayNumber /= 1000;
        }
        if(i/100 > 0){                          //Get hundreds, tens, then ones prefix
            hundreds = getHundredsPrefix(i/100);
            i -= ((i/100))*100;
        }
        if(i/10 >=1){
            tens = getTensPrefix(i/10);
            i -= ((i/10))*10;
        }
        ones = getOnesPrefix(i);
        return ones + tens + hundreds;
    }

    /**
     * Retrieves the correct prefix for the ones place
     * @param i Number in ones place
     * @return String for prefix for ones place
     */
    static private String getOnesPrefix(int i){
        if(hundreds.equals("") && tens.equals("")){ //If the number is less than 10 ~
            if(i == 1){
                return "mil";
            }
            if(i == 2){
                return "bil";
            }
            if(i == 3){
                return "tri";
            }
            if(i == 4){
                return "quadri";
            }
            if(i == 5){
                return "quinquei";
            }
            if(i == 6){
                return "sexi";
            }
            if(i == 7){
                return "septi";
            }
            if(i == 8){
                return "octi";
            }
            if(i == 9){
                return "noni";
            }
        }
        if(i == 0){ //If number is greater than 10 ~, return corresponding prefix
            return "";
        }
        if(i == 1){
            return "un";
        }
        if(i == 2){
            return "duo";
        }
        if(i == 3){
            return "tre";
        }
        if(i == 4){
            return "quat";
        }
        if(i == 5){
            return "quin";
        }
        if(i == 6){
            return "sex";
        }
        if(i == 7){
            return "sept";
        }
        if(i == 8){
            return "oct";
        }
        if(i == 9){
            return "nov";
        }
        return "";
    }

    /**
     * Retrieves the correct prefix for the tens place
     * @param i Number in tens place
     * @return String for prefix for tens place
     */
    static private String getTensPrefix(int i){
        if(i == 0){         //The number is greater than or equal to 10 ~
            return "";
        }
        if(i == 1){
            return "deci";
        }
        if(i == 2){
            return "vigi";
        }
        if(i == 3){
            return "trigi";
        }
        if(i == 4){
            return "quadra";
        }
        if(i == 5){
            return "quinqua";
        }
        if(i == 6){
            return "sexa";
        }
        if(i == 7){
            return "septua";
        }
        if(i == 8){
            return "octogi";
        }
        if(i == 9){
            return "nona";
        }
        return "";
    }

    /**
     * Retrieves the correct prefix for the hundreds place
     * @param i Number in hundreds place
     * @return String for prefix for hundreds place
     */
    static private String getHundredsPrefix(int i){
        if(i == 0){         //The number is greater than or equal to 100 ~
            return "";
        }
        if(i == 1){
            return "centi";
        }
        if(i == 2){
            return "duce";
        }
        if(i == 3){
            return "trece";
        }
        if(i == 4){
            return "quadri";
        }
        if(i == 5){
            return "quinge";
        }
        if(i == 6){
            return "sexce";
        }
        if(i == 7){
            return "septi";
        }
        if(i == 8){
            return "octi";
        }
        if(i == 9){
            return "nonge";
        }
        return "";
    }

    static void reset(){
        gold = 0;
    }
}
