package com.lim.poly.project2.domain;

import com.lim.poly.project2.util.DateUtil;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Table(name = "book_review")
@Entity
public class BookReview {

    @Column(name = "book_review_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    //독서록 제목
    @Setter
    private String title;

    //독서록 내용
    @Setter
    @Lob
    private String content;

    @Setter
    private String regDate;

    @Setter
    private String chgDate;

    @Setter
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Setter
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    /*
    생성자 메서드
     */
    public static BookReview createBookReview(String title, String content, Member member, Book book) {
        BookReview bookReview = new BookReview();
        bookReview.setTitle(title);
        bookReview.setMember(member);
        bookReview.setContent(content);
        bookReview.setBook(book);
        bookReview.setRegDate(DateUtil.getDateTime("yyyyMMdd"));
        bookReview.setChgDate(DateUtil.getDateTime("yyyyMMdd"));

        return bookReview;
    }

}
