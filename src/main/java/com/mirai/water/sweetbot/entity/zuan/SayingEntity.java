package com.mirai.water.sweetbot.entity.zuan;

import javax.persistence.*;

@Entity
@Table(name = "saying")
public class SayingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "question", length = 5000)
    private String question;

    @Column(name = "body", nullable = false, length = 5000)
    private String body;

    @Column(name = "type", nullable = false, length = 50)
    private String type;

    @Column(name = "author", length = 50)
    private String author;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}