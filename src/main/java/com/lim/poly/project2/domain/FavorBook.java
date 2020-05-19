package com.lim.poly.project2.domain;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Table(name = "favor_book")
@Entity
public class FavorBook {

    @Column(name = "favor_book_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
}
