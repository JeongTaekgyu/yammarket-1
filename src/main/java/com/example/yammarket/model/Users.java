package com.example.yammarket.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Users {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private String user_id;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    //@OneToMany(fetch = FetchType.LAZY, mappedBy = "users")
    //private List<Posts> posts;


}