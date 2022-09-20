package com.example.week5_2;

import java.io.Serializable;
import java.util.ArrayList;

public class Sentence implements Serializable {
    public ArrayList<String>  badSentences = new ArrayList<>();
    public ArrayList<String> goodSentences = new ArrayList<>();
    public Sentence(){}
    public Sentence(ArrayList<String> badSentences, ArrayList<String> goodSentences){
        this.badSentences = badSentences;
        this.goodSentences = goodSentences;
    }

    public ArrayList<String> getBadSentences() {
        return badSentences;
    }

    public void setBadSentences(ArrayList<String> badSentences) {
        this.badSentences = badSentences;
    }

    public ArrayList<String> getGoodSentences() {
        return goodSentences;
    }

    public void setGoodSentences(ArrayList<String> goodSentences) {
        this.goodSentences = goodSentences;
    }
}
