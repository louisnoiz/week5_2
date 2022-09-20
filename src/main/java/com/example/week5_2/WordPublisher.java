package com.example.week5_2;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
public class WordPublisher {
    protected Word words = new Word();
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @RequestMapping(value = "/addBad/{word}", method = RequestMethod.GET)
    public ArrayList<String> addBad(@PathVariable("word") String word){
        this.words.badWords.add(word);
        return words.badWords;
    }
    @RequestMapping(value = "/delBad/{word}", method = RequestMethod.GET)
    public ArrayList<String> delBad(@PathVariable("word") String word){
        this.words.badWords.remove(word);
        return words.badWords;
    }
    @RequestMapping(value = "/addGood/{word}", method = RequestMethod.GET)
    public ArrayList<String> addGood(@PathVariable("word") String word){
        this.words.goodWords.add(word);
        return words.goodWords;
    }
    @RequestMapping(value = "/delGood/{word}", method = RequestMethod.GET)
    public ArrayList<String> delGood(@PathVariable("word") String word){
        this.words.goodWords.remove(word);
        return words.goodWords;
    }
    @RequestMapping(value = "/proof/{sentence}", method = RequestMethod.GET)
    public String proof(@PathVariable("sentence") String sentence) {
        boolean checkBad = false;
        boolean checkGood = false;
        for (String word : this.words.badWords) {
            if (sentence.contains(word)) {
                checkBad = true;
            }
        }
        for (String word : this.words.goodWords) {
            if (sentence.contains(word)) {
                checkGood = true;
            }
        }
        if ((checkBad == true) && (checkGood == false)){
            rabbitTemplate.convertAndSend("Direct", "bad", sentence);
            return ("Found Bad Word");

        } else if ((checkGood == true) && (checkBad == false)) {
            rabbitTemplate.convertAndSend("Direct", "good", sentence);
            return ("Found Good Word");

        } else if ((checkGood == true) && (checkBad == true)){
            rabbitTemplate.convertAndSend("Fanout", "", sentence);
            return ("Found Bad & Good Word");

        }
        return "";
    }
    @RequestMapping(value = "/getSentence", method = RequestMethod.GET)
    public Sentence getSentence(){
        Object sentence = rabbitTemplate.convertSendAndReceive("Direct", "sentence", "");
        return (Sentence) sentence;
    }
    @RequestMapping(value = "/addGoodWord", method = RequestMethod.POST)
    public ArrayList<String> addGood(@RequestBody MultiValueMap<String, String> n){
        Map<String, String> d = n.toSingleValueMap();
        this.words.goodWords.add(d.get("word"));
        return this.words.goodWords;
    }

    @RequestMapping(value = "/addBadWord", method = RequestMethod.POST)
    public ArrayList<String> addBad(@RequestBody MultiValueMap<String, String> n){
        Map<String, String> d = n.toSingleValueMap();
        this.words.badWords.add(d.get("word"));
        return this.words.badWords;
    }

    @RequestMapping(value = "/proofSentence", method = RequestMethod.POST)
    public String proofSentence(@RequestBody MultiValueMap<String, String> n){
        Map<String, String> d = n.toSingleValueMap();
        String sentence = d.get("Sentence");
        boolean checkBad = false;
        boolean checkGood = false;
        String result = "";
        for (String word : this.words.badWords) {
            if (sentence.contains(word)) {
                checkBad = true;
            }
        }
        for (String word : this.words.goodWords) {
            if (sentence.contains(word)) {
                checkGood = true;
            }
        }
        if ((checkBad == true) && (checkGood == false)){
            rabbitTemplate.convertAndSend("Direct", "bad", d.get("Sentence"));
            result =  "Found Bad Word";

        } else if ((checkGood == true) && (checkBad == false)) {
            rabbitTemplate.convertAndSend("Direct", "good", d.get("Sentence"));
            result =  "Found Good Word";

        } else if ((checkGood == true) && (checkBad == true)){
            rabbitTemplate.convertAndSend("Fanout", "", d.get("Sentence"));
            result = "Found Bad & Good Word";

        } else {
            result = "Not Found";
        }

        return result;
    }



}
