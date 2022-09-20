package com.example.week5_2;

import java.io.Serializable;
import java.util.ArrayList;

public class Word implements Serializable {
    public ArrayList<String> badWords = new ArrayList<>();
    public ArrayList<String> goodWords = new ArrayList<>();

    public Word(){
        this.goodWords.add("happy");
        this.goodWords.add("enjoy");
        this.goodWords.add("life");
        this.goodWords.add("like");
        this.badWords.add("fuck");
        this.badWords.add("olo");
    }
    public Word(ArrayList badWords, ArrayList goodWords){
        this.badWords = badWords;
        this.goodWords = goodWords;
    }

    public ArrayList<String> getBadWords() {
        return badWords;
    }

    public void setBadWords(ArrayList<String> badWords) {
        this.badWords = badWords;
    }

    public ArrayList<String> getGoodWords() {
        return goodWords;
    }

    public void setGoodWords(ArrayList<String> goodWords) {
        this.goodWords = goodWords;
    }
}
