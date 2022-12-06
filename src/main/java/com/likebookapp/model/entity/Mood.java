package com.likebookapp.model.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "moods")
public class Mood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private MoodsEnum moodName;

    @Column
    private String description;

    @OneToMany(mappedBy = "mood")
    private Set<Post> posts;

    public Mood() {
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public Mood setPosts(Set<Post> posts) {
        this.posts = posts;
        return this;
    }

    public Long getId() {
        return id;
    }

    public Mood setId(Long id) {
        this.id = id;
        return this;
    }

    public MoodsEnum getMoodName() {
        return moodName;
    }

    public Mood setMoodName(MoodsEnum moodName) {
        this.moodName = moodName;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Mood setDescription(String description) {
        this.description = description;
        return this;
    }
}
