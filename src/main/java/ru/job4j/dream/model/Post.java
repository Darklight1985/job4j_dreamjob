package ru.job4j.dream.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Post {
    private int id;
    private String name;
    private String decription;
    private LocalDateTime created;

    public Post(int id, String name, String decription, LocalDateTime created) {
        this.id = id;
        this.name = name;
        this.decription = decription;
        this.created = created;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return id == post.id && Objects.equals(name, post.name) && Objects.equals(decription, post.decription) && Objects.equals(created, post.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, decription, created);
    }
}
