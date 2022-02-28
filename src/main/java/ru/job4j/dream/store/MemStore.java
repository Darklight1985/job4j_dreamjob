package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemStore {

    private static final MemStore INST = new MemStore();

    private static AtomicInteger postId = new AtomicInteger(3);
    private static AtomicInteger candidateId = new AtomicInteger(3);
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private MemStore() {
        posts.put(1, new Post(1, "Junior Java Job", "Вакансия Junior",
                new Timestamp(System.currentTimeMillis())));
        posts.put(2, new Post(2, "Middle Java Job", "Вакансия Middle",
                new Timestamp(System.currentTimeMillis())));
        posts.put(3, new Post(3, "Senior Java Job", "Вакансия Senior",
                new Timestamp(System.currentTimeMillis())));
        candidates.put(1, new Candidate(1, "Junior Java", 1, LocalDateTime.now()));
        candidates.put(2, new Candidate(2, "Middle Java", 2, LocalDateTime.now()));
        candidates.put(3, new Candidate(3, "Senior Java", 3, LocalDateTime.now()));
    }

    public void save(Post post) {
        if (post.getId() == 0) {
            post.setId(postId.incrementAndGet());
        }
        posts.put(post.getId(), post);
    }

    public void deleteCan(int id) {
        candidates.remove(candidateId.get());
    }

    public Post findById(int id) {
        return posts.get(id);
    }

    public Candidate findCanById(int id) {
        return candidates.get(id);
    }

    public void save(Candidate candidate) {
        if (candidate.getId() == 0) {
            candidate.setId(candidateId.incrementAndGet());
        }
        candidates.put(candidate.getId(), candidate);
    }

    public static MemStore instOf() {
        return INST;
    }

    public Collection<Post> findAllPosts() {
        return posts.values();
    }

    public Collection<Candidate> findAllCandidates() {
        return candidates.values();
    }
}
