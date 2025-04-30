/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project1.game_model;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author iremayvaz
 */
public class Dice {

    public static int roll() { // Zarı at
        return new Random().nextInt(6) + 1;
    }

    public static int[] rollMultiple(int count) { // Birlik sayısına göre zar at
        int[] results = new int[count];

        for (int i = 0; i < count; i++) {
            results[i] = roll();
        }

        Arrays.sort(results);

        for (int i = 0; i < results.length / 2; i++) {
            int temp = results[i];
            results[i] = results[results.length - 1 - i];
            results[results.length - 1 - i] = temp;
        }

        return results;
    }
}
