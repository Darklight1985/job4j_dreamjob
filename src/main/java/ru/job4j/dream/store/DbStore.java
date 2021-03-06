package ru.job4j.dream.store;

import org.apache.commons.dbcp2.BasicDataSource;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.City;
import ru.job4j.dream.model.Post;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.dream.model.User;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class DbStore implements Store {

    private static final Logger LOG = LoggerFactory.getLogger(DbStore.class.getName());
    private final BasicDataSource pool = new BasicDataSource();

    private static final class Lazy {
        private static final Store INST = new DbStore();
    }

    private DbStore() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(
                new InputStreamReader(
                        DbStore.class.getClassLoader()
                                .getResourceAsStream("db.properties")
                )
        )) {
            cfg.load(io);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        pool.setUrl(cfg.getProperty("jdbc.url"));
        pool.setUsername(cfg.getProperty("jdbc.username"));
        pool.setPassword(cfg.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    public Collection<Post> findAllPosts() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM post")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(new Post(it.getInt("id"),
                            it.getString("name"), it.getString("description"),
                            it.getTimestamp("created")));
                }
            }
        } catch (Exception e) {
            LOG.debug("Exception: ", e.toString());
        }
        return posts;
    }

    @Override
    public Collection<Post> findAllPostsDay() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime minusDay = now.minusDays(1);
        List<Post> result = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "SELECT * FROM post "
                             + "WHERE post.created between ? and ?")
        ) {
            ps.setTimestamp(1, Timestamp.valueOf(minusDay));
            ps.setTimestamp(2, Timestamp.valueOf(now));
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    result.add(
                            new Post(
                                    it.getInt("id"),
                                    it.getString("name"),
                                    it.getString("description"),
                                    it.getTimestamp("created")
                            )
                    );
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public Collection<Candidate> findAllCanDay() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime minusDay = now.minusDays(1);
        List<Candidate> result = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "SELECT candidate.id, candidate.name, c.name as city,"
                             + " candidate.created from candidate "
                             + "JOIN city c on candidate.cityId = c.id "
                             + "WHERE candidate.created between ? and ?")
        ) {
            ps.setTimestamp(1, Timestamp.valueOf(minusDay));
            ps.setTimestamp(2, Timestamp.valueOf(now));
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    result.add(
                            new Candidate(
                                    it.getInt("id"),
                                    it.getString("name"),
                                    it.getString("city"),
                                    it.getTimestamp("created")
                            )
                    );
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public Collection<Candidate> findAllCanWithCity() {
        List<Candidate> result = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "SELECT candidate.id, candidate.name, c.name as city, "
                             + "candidate.created from candidate "
                             + "JOIN city c on candidate.cityId = c.id ")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    result.add(
                            new Candidate(
                                    it.getInt("id"),
                                    it.getString("name"),
                                    it.getString("city"),
                                    it.getTimestamp("created")
                            )
                    );
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    public Collection<Candidate> findAllCandidates() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM candidate")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    candidates.add(new Candidate(it.getInt("id"),
                            it.getString("name"),
                            it.getInt("cityId"),
                            it.getTimestamp("created").toLocalDateTime()
                            ));
                }
            }
        } catch (Exception e) {
            LOG.debug("Exception: ", e.toString());
        }
        return candidates;
    }

    @Override
    public Collection<City> findAllCity() {
        List<City> result = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM city;")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    result.add(
                            new City(
                                    it.getInt("id"),
                                    it.getString("name")
                            )
                    );
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public Collection<User> findAllUsers() {
        return null;
    }

    public void save(Post post) {
        if (post.getId() == 0) {
            create(post);
        } else {
            update(post);
        }
    }

    public void save(Candidate candidate) {
        if (candidate.getId() == 0) {
            create(candidate);
        } else {
            update(candidate);
        }
    }

    @Override
    public void save(User user) {
        if (user.getId() == 0) {
            create(user);
        } else {
            update(user);
        }
    }

    public void save(City city) {
        if (city.getId() == 0) {
            create(city);
        } else {
            update(city);
        }
    }

    private Post create(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =
                     cn.prepareStatement(
                             "INSERT INTO post(name, description, created) VALUES (?, ?, ?)",
                             PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getDescription());
            ps.setTimestamp(3, post.getCreated());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.debug("Exception: ", e.toString());
        }
        return post;
    }

    private Candidate create(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =
                     cn.prepareStatement(
                             "INSERT INTO candidate(name, cityId, created) VALUES (?, ?, ?)",
                             PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, candidate.getName());
            ps.setInt(2, candidate.getCityId());
            ps.setTimestamp(3, Timestamp.valueOf(candidate.getCreated()));
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.debug("Exception: ", e.toString());
        }
        return candidate;
    }

    private User create(User user) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =
                     cn.prepareStatement(
                             "INSERT INTO users(name, email, password) VALUES (?, ?, ?)",
                             PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.debug("Exception: ", e.toString());
        }
        return user;
    }

    private City create(City city) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =
                     cn.prepareStatement(
                             "INSERT INTO city(name) VALUES (?)",
                             PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, city.getName());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    city.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.debug("Exception: ", e.toString());
        }
        return city;
    }

    private void update(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =
                     cn.prepareStatement(
                             "UPDATE post SET name = ?, description = ?, created = ?"
                                     + " WHERE id = ?")
        ) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getDescription());
            ps.setTimestamp(3, post.getCreated());
            ps.setInt(4, post.getId());
            ps.execute();
        } catch (Exception e) {
            LOG.debug("Exception: ", e.toString());
        }
    }

    private void update(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =
                     cn.prepareStatement(
                             "UPDATE candidate SET name = ?, cityId = ?, created = ?"
                                     + " WHERE id = ?")
        ) {
            ps.setString(1, candidate.getName());
            ps.setInt(2, candidate.getCityId());
            ps.setTimestamp(3, Timestamp.valueOf(candidate.getCreated()));
            ps.setInt(4, candidate.getId());
            ps.execute();
        } catch (Exception e) {
            LOG.debug("Exception: ", e.toString());
        }
    }

    private void update(User user) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =
                     cn.prepareStatement(
                             "UPDATE users SET name = ?, email = ?, password =?"
                                     + " WHERE id = ?")
        ) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setInt(4, user.getId());
            ps.execute();
        } catch (Exception e) {
            LOG.debug("Exception: ", e.toString());
        }
    }

    private void update(City city) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =
                     cn.prepareStatement(
                             "UPDATE city SET name = ?"
                                     + " WHERE id = ?")
        ) {
            ps.setString(1, city.getName());
            ps.setInt(2, city.getId());
            ps.execute();
        } catch (Exception e) {
            LOG.debug("Exception: ", e.toString());
        }
    }

    public Post findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM post WHERE id = ?")
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return new Post(it.getInt("id"), it.getString("name"),
                            it.getString("description"),
                            it.getTimestamp("created"));
                }
            }
        } catch (Exception e) {
            LOG.debug("Exception: ", e.toString());
        }
        return null;
    }

    public Candidate findCanById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM candidate WHERE id = ?")
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return new Candidate(it.getInt("id"), it.getString("name"),
                            it.getInt("cityID"), it.getTimestamp("created").toLocalDateTime());
                }
            }
        } catch (Exception e) {
            LOG.debug("Exception: ", e.toString());
        }
        return null;
    }

    @Override
    public User findUserById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM users WHERE id = ?")
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return new User(it.getInt("id"), it.getString("name"),
                            it.getString("email"), it.getString("password"));
                }
            }
        } catch (Exception e) {
            LOG.debug("Exception: ", e.toString());
        }
        return null;
    }

    public City findCityById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM city WHERE id = ?")
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return new City(it.getInt("id"), it.getString("name"));
                }
            }
        } catch (Exception e) {
            LOG.debug("Exception: ", e.toString());
        }
        return null;
    }

    public User findUserByEmail(String email) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM users WHERE email = ?")
        ) {
            ps.setString(1, email);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return new User(it.getInt("id"), it.getString("name"),
                            it.getString("email"), it.getString("password"));
                }
            }
        } catch (Exception e) {
            LOG.debug("Exception: ", e.toString());
        }
        return null;
    }

    public City findCityByName(String nameCity) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM city WHERE name = ?")
        ) {
            ps.setString(1, nameCity);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return new City(it.getInt("id"), it.getString("name"));
                }
            }
        } catch (Exception e) {
            LOG.debug("Exception: ", e.toString());
        }
        return null;
    }

    public void deletePost(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("DELETE * FROM post WHERE id = ?")
        ) {
            ps.setInt(1, id);
        } catch (Exception e) {
            LOG.debug("Exception: ", e.toString());
        }
    }

    public void deleteCan(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("DELETE * FROM candidate WHERE id = ?")
        ) {
            ps.setInt(1, id);
        } catch (Exception e) {
            LOG.debug("Exception: ", e.toString());
        }
    }

    @Override
    public void deleteUser(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("DELETE * FROM users WHERE id = ?")
        ) {
            ps.setInt(1, id);
        } catch (Exception e) {
            LOG.debug("Exception: ", e.toString());
        }
    }

    @Override
    public void deleteAllCandidates() {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("delete from candidate;")
        ) {
            ps.executeUpdate();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
