package com.lim.poly.project2.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookReviewSaveRequestDto {

    private Long id;
    private String title;
    private String content;
}
