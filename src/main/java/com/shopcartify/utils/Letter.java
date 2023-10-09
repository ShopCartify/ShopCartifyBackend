package com.shopcartify.utils;

public class Letter {
    public static Character [] getCapitalLetters(){
        Character [] capitalLetters = new Character[26];
        int index = 0;
        for (int i = 65; i <= 90; i++) {
            char character = (char) i;
            capitalLetters[index++] = character;
        }
        return capitalLetters;
    }
    public static Character [] getSmallLetters(){
        Character [] smallLetters = new Character[26];
        int index = 0;
        for (int i = 97; i <= 122; i++) {
            char character = (char) i;
            smallLetters[index++] = character;
        }
        return smallLetters;
    }
}
