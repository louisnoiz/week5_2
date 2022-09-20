package com.example.week5_2;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.jsoup.internal.StringUtil;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;

@Route(value = "/string")
public class MyView2 extends FormLayout {
    public Word words = new Word();
    private TextField addWord, addSen;
    private TextArea goodSen, badSen;
    private Button addGoodWordBtn, addBadWordBtn, addSenBtn, showSenBtn;
    private ComboBox<String> goodWords, badWords;
    public MyView2(){
        addWord = new TextField("Add Word");
        addGoodWordBtn = new Button("Add Good Word");
        addBadWordBtn = new Button("Add Bad Word");
        goodWords = new ComboBox<>("GoodWords");
        goodWords.setItems(this.words.goodWords);
        badWords = new ComboBox<>("BadWords");
        badWords.setItems(this.words.badWords);
        VerticalLayout first = new VerticalLayout();
        first.add(addWord, addGoodWordBtn, addBadWordBtn, goodWords, badWords);

        addSen = new TextField("Add Sentence");
        addSenBtn = new Button("Add Sentence");
        goodSen = new TextArea("Good Sentences");
        badSen = new TextArea("Bad Sentences");
        showSenBtn = new Button("Show Sentence");
        VerticalLayout second = new VerticalLayout();
        second.add(addSen, addSenBtn, goodSen, badSen, showSenBtn);

        HorizontalLayout all = new HorizontalLayout();
        all.add(first, second);
        this.add(all);

        addGoodWordBtn.addClickListener(event ->{
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("word", addWord.getValue());

            ArrayList out = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/addGoodWord")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData(formData))
                    .retrieve()
                    .bodyToMono(ArrayList.class)
                    .block();
            goodWords.setItems(out);
        });

        addBadWordBtn.addClickListener(event ->{
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("word", addWord.getValue());

            ArrayList out = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/addBadWord")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData(formData))
                    .retrieve()
                    .bodyToMono(ArrayList.class)
                    .block();
            badWords.setItems(out);
        });

        addSenBtn.addClickListener(event ->{
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("Sentence", addSen.getValue());

            String out = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/proofSentence")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData(formData))
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            new Notification(out, 6000).open();
        });

        showSenBtn.addClickListener(event ->{
            Sentence out = WebClient.create()
                    .get()
                    .uri("http://localhost:8080/getSentence")
                    .retrieve()
                    .bodyToMono(Sentence.class)
                    .block();
            String good = StringUtil.join(out.goodSentences, ", ");
            String bad = StringUtil.join(out.badSentences, ", ");
            goodSen.setValue("["+good+"]");
            badSen.setValue("["+bad+"]");
        });
    }
}
