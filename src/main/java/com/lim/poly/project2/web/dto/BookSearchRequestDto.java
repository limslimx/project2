package com.lim.poly.project2.web.dto;

import lombok.Data;

@Data
public class BookSearchRequestDto {

    private String searchBy;

    public BookSearchRequestDto(String searchBy) {
        this.searchBy = searchBy;
    }
}
