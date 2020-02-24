package com.pl.orthography.data.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Entity
@Table(name = "user_tests")
public class UserTest extends BasicEntity {

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_test")
    private Test test;

    @NotNull
    private int points;

    @NotNull
    private Date date;


}
