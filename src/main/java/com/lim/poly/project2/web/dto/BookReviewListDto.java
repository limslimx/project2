package com.lim.poly.project2.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class BookReviewListDto {

    //독서록 제목
    private String title;

    //독서록 내용
    private String content;

    //독서록 수정일
    private String chgDate;

    //책 제목
    private String name;

    //책 이미지
    private String img;

    //책 저자
    private String author;

    //책 카테고리
    private String detailCategory;

    public BookReviewListDto(String title, String content, String chgDate, String name, String img, String author, String detailCategory) {
        this.title = title;
        this.content = content;
        this.chgDate = chgDate;
        this.name = name;
        this.img = img;
        this.author = author;
        this.detailCategory = detailCategory;
    }
}
