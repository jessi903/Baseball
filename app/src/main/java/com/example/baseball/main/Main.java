package com.example.baseball.main;

import android.content.Context;
import android.widget.Toast;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public class Main {
    public static boolean isCorrect = false;
    private Context context;
    private Map<Integer, Integer> ansMap = new LinkedHashMap<>();

    public Main(Context context) {
        this.context = context;
    }

    public String process(int ans, int ran) {
        int strike = 0;
        int ball = 0;

        Map<Integer, Integer> ranMap = new LinkedHashMap<>();

        //Set random numbers for each digit
        ranMap.put(0, ran/1000);
        ranMap.put(1, (ran%1000) / 100);
        ranMap.put(2, (ran % 100) / 10);
        ranMap.put(3, ran % 10);

        // Check strike
        for (int i = 0; i < 4; i++) {
            if (ansMap.get(i) == ranMap.get(i)) {
                strike++;
            }
        }

        //Check ball
        if (strike != 4) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if ((i != j) && (ansMap.get(i) == ranMap.get(j))) {
                        ball++;
                    }
                }
            }
        } else {
            // Check the answer
            isCorrect = true;
            Toast.makeText(context, "Congratulations!", Toast.LENGTH_SHORT).show();
        }
        String result = strike + " strike, " + ball + " ball";

        if (strike == 0 && ball == 0) {
            result = "OUT";
        }

        return result;
    }

    // Check user value - digit
    public int isValid(String inStr) {

        int retInt;
        try {
            retInt = Integer.parseInt(inStr);
        } catch (NumberFormatException e) {
            Toast.makeText(context, "Please insert numbers.", Toast.LENGTH_SHORT).show();
            return -1;
        }
        if (retInt > 9999 || retInt < 1000) {
            Toast.makeText(context, "Please insert four-digit integers.", Toast.LENGTH_SHORT).show();
            return -1;
        }

        // Check each digit
        if (!hasSameNum(retInt)) {
            return -1;
        }

        return retInt;
    }

    //Check same numbers
    private boolean hasSameNum(int retInt) {
        // Divide user input into each digit
        ansMap.put(0, retInt/1000);
        ansMap.put(1, (retInt % 1000) /100);
        ansMap.put(2, (retInt % 100) / 10);
        ansMap.put(3, retInt % 10);

        // Check if they are all different
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if ((j != i) && (ansMap.get(i) == ansMap.get(j))) {
                    Toast.makeText(context, "Please insert different numbers for each digit.", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        }
        return true;
    }

    //Generate random numbers
    public int genRanNum() {
        int retVal = 0;
        //Generate until the random values is higher than 1000
        while (retVal < 1000) {
            retVal = new Random().nextInt(10000);

            //Each digit has different number
            if (!hasSameNum(retVal)) {
                //Regenerate
                retVal = -1;
            }
        }
        System.out.println("Generated Random Number : " + retVal);
        return retVal;
    }

}