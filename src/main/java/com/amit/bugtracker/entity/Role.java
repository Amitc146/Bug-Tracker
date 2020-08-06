package com.amit.bugtracker.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Override
    public String toString() {
        return this.name.charAt(5) + this.name.substring(6).toLowerCase();
    }

}