package ru.job4j.dream.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

public class Candidate {
    private int id;
    private String name;
    private int cityId;
    private LocalDateTime created;
    private String cityName;
    private Timestamp creat;

    public Candidate(int id, String name, int cityId, LocalDateTime created) {
        this.id = id;
        this.name = name;
        this.cityId = cityId;
        this.created = created;
    }

    public Candidate(int id, String name, String cityName, Timestamp created) {
        this.id = id;
        this.name = name;
        this.creat = created;
        this.cityName = cityName;
    }

    public Timestamp getCreat() {
        return creat;
    }

    public void setCreat(Timestamp creat) {
        this.creat = creat;
    }

    public String getCityName() {
        return cityName;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Candidate candidate = (Candidate) o;
        return id == candidate.id
                && Objects.equals(name, candidate.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
