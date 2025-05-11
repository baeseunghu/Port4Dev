package com.port4dev.entity;

import jakarta.persistence.*;

@Entity
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String experience;
    private String skills;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Getters and setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getExperience() { return experience; }

    public void setExperience(String experience) { this.experience = experience; }

    public String getSkills() { return skills; }

    public void setSkills(String skills) { this.skills = skills; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }
}