package ru.job4j.dream.store;

import ru.job4j.dream.model.Post;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class MainStore {
    public static void main(String[] args) {
        Store store = DbStore.instOf();
        store.save(new Post(0, "Java Job", "", new Timestamp(System.currentTimeMillis())));
        store.save(new Post(1, "Vasya", "", new Timestamp(System.currentTimeMillis())));
        for (Post post : store.findAllPosts()) {
            System.out.println(post.getId() + " " + post.getName());
        }
    }
}
