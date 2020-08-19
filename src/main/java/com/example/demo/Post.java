package com.example.demo;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Lob
    private String text;

    @ManyToOne (fetch = FetchType.EAGER)
    private User author;

    private String timeSubmitted;

    public Post() {
        setTimeSubmitted();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getTimeSubmitted() {
        return timeSubmitted;
    }

    public void setTimeSubmitted() {
        LocalDateTime localDateTime = LocalDateTime.now();

        DateTimeFormatter df = DateTimeFormatter.ofPattern("MM-dd-yyyy");

        this.timeSubmitted = timeSubmitted;
    }
}
