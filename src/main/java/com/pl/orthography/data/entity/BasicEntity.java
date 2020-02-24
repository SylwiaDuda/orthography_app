package com.pl.orthography.data.entity;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public abstract class BasicEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true)
    private Long id;

    public Long getId() {
        return id;
    }
}