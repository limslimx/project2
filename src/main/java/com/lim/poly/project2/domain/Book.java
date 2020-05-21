package com.lim.poly.project2.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Book {

    @Column(name = "book_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //검색 날짜 -> 크롤링 시 같은 책인데 매일매일 크롤링 시에 최근 데이터로 가져오기 위한 구분값
    private String searchDate;

    //검색어 -> 도서 검색 기능 구현 시에 검색어를 저장함으로써 나중에 다시 똑같은 검색어로 검색할 때 더 빠르게 조회할 수 있도록 함
    private String searchBy;

    //책 이름
    private String name;

    //책 소제목
    private String subName;

    //책 이미지
    private String img;

    //책 저자
    private String author;

    //책 카테고리 -> 사실 이게 필요할지는 좀 더 생각해봐야 될 듯
    private String category;

    //책 상세 카테고리
    private String detailCategory;

    //책 순위
    private String rank;

    //책 태그
    private String tag;

    //책 상세 url
    private String url;

    //책 출판일
    private String publicationDate;

    //책 리뷰들 -> 한 책에 여러 개의 리뷰가 있을텐데 어떤 타입으로 지정해야 할까
    //private String review;

    @Builder
    public Book(String searchDate, String searchBy, String name, String subName, String img, String author, String category, String detailCategory, String rank, String tag, String url, String publicationDate) {
        this.searchDate = searchDate;
        this.searchBy = searchBy;
        this.name = name;
        this.subName = subName;
        this.img = img;
        this.author = author;
        this.category = category;
        this.detailCategory = detailCategory;
        this.rank = rank;
        this.tag = tag;
        this.url = url;
        this.publicationDate = publicationDate;
    }
}
