package com.skhanra52;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        /* Each ball is identified with either a B,I,N,G,O and followed by the number.
        A "B" ball will have a number from 1 to 15 and "I" ball will get a number between 16 through 30 and so on.
        Someone will draw a ball out of the container, and announce it.
        Every player will have five-by-five card, with some randomly generated set of these number on it.
        The first column has "B" number and second column have "I" number and so on.
        You win by being the first person to match five called number in a row, either horizontally,vertically,
        or diagonally.
         */


        List<String> bingoPool = new ArrayList<>(75);
        int start = 1;

        for(char c : "BINGO".toCharArray()){
            for(int i = start; i<(start + 15); i++){
                bingoPool.add(""+c+i);
//                System.out.println(""+c+i);
            }
            start +=15;
        }
        Collections.shuffle(bingoPool);
        for(int i=0;i<15;i++){
            System.out.print(bingoPool.get(i)+" ,");
        }
        System.out.println();
        System.out.println("---------------------------------------");

        List<String> firstOnes = bingoPool.subList(0,15);
        System.out.println(firstOnes);
        System.out.println("----------------------------------------");
        firstOnes.sort(Comparator.naturalOrder());
        firstOnes.replaceAll(s -> {
            if ((s.indexOf('G') == 0) || (s.indexOf('O') == 0)) {
                String updated = s.charAt(0) + "-" + s.substring(1);
                System.out.println(updated + " ");
                return updated;
            }
            return s;
        });
        System.out.println(firstOnes);
    }
}