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

    static public void add(double amount){gold += amount;}

    static public void add(int amount){gold += amount;}

    static public String getGoldWithSuffix(){
        return getNumberWithSuffix(gold);
    }

    static public String getNumberWithSuffix(int number){
        return getNumberWithSuffix((double) number);
    }

    static public String getNumberWithSuffix(double _number){
        suffix = getSuffix(_number);
        if(!suffix.isEmpty()) {
            first = Character.toUpperCase(suffix.charAt(0));           //Make first letter capitalized
            if(!suffix.equals("K"))
                suffix = first + suffix.substring(1) + "llion";
            suffix = " " + suffix;
        }
        return formater.format(displayNumber) + suffix;
    }

    static public String getSuffix(double number){
        hundreds = tens = ones = "";            //Reset prefixes
        displayNumber = number;
        if(number < 1000)
            return "";
        if(number < 1000000){
            displayNumber /= 1000;
            return "K";
        }
        displayNumber /= 1000000;
        int i = 1;
        while(displayNumber /1000 >=1){               //Count amount of 000's
            i ++;
            displayNumber /= 1000;
        }
        if(i/100 > 0){                          //Get hundreds, tens, then ones prefix
            hundreds = getHundredsPrefix((int)i/100);
            i -= ((int)(i/100))*100;
        }
        if(i/10 >=1){
            tens = getTensPrefix((int)i/10);
            i -= ((int)(i/10))*10;
        }
        ones = getOnesPrefix((int)i);
        return ones + tens + hundreds;
    }

    static private String getOnesPrefix(int i){
        if(hundreds.equals("") && tens.equals("")){
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
        if(i == 0){
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
            return "quattuor";
        }
        if(i == 5){
            return "quinqua";
        }
        if(i == 6){
            return "sex";
        }
        if(i == 7){
            return "septen";
        }
        if(i == 8){
            return "octo";
        }
        if(i == 9){
            return "noven";
        }
        return "";
    }

    static private String getTensPrefix(int i){
        if(i == 0){
            return "";
        }
        if(i == 1){
            return "deci";
        }
        if(i == 2){
            return "viginti";
        }
        if(i == 3){
            return "triginti";
        }
        if(i == 4){
            return "quadraginti";
        }
        if(i == 5){
            return "quinquaginti";
        }
        if(i == 6){
            return "sexaginti";
        }
        if(i == 7){
            return "septuaginti";
        }
        if(i == 8){
            return "octoginti";
        }
        if(i == 9){
            return "nonaginti";
        }
        return "";
    }

    static private String getHundredsPrefix(int i){
        if(i == 0){
            return "";
        }
        if(i == 1){
            return "centi";
        }
        if(i == 2){
            return "ducenti";
        }
        if(i == 3){
            return "trecenti";
        }
        if(i == 4){
            return "quadringenti";
        }
        if(i == 5){
            return "quingenti";
        }
        if(i == 6){
            return "sexcenti";
        }
        if(i == 7){
            return "septingenti";
        }
        if(i == 8){
            return "octingenti";
        }
        if(i == 9){
            return "nongenti";
        }
        return "";
    }

    static void reset(){
        gold = 0;
    }
}
