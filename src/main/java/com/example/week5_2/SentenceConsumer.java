package com.example.week5_2;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
@Service
public class SentenceConsumer {
    protected Sentence sentences = new Sentence();
    @RabbitListener(queues = "BadWordQueue")
    public void addBadSentence(String sentence){
        sentences.badSentences.add(sentence);
        System.out.println("In addBadSentence Method : "+ sentences.badSentences);
    }
    @RabbitListener(queues = "GoodWordQueue")
    public void addGoodSentence(String sentence){
        sentences.goodSentences.add(sentence);
        System.out.println("In addGoodSentence Method : "+ sentences.goodSentences);
    }
    @RabbitListener(queues = "GetQueue")
    public Sentence getSentences(){
        return this.sentences;
    }
}
