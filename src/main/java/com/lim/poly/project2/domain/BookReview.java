package com.lim.poly.project2.domain;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Table(name = "book_review")
@Entity
public class BookReview {

    @Column(name = "book_review_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //독서록 제목
    private String title;

    //독서록 내용
    private String content;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
}
