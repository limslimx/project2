package com.lim.poly.project2.web;

import com.lim.poly.project2.domain.Book;
import com.lim.poly.project2.service.CrawlingService;
import com.lim.poly.project2.service.book.BookService;
import com.lim.poly.project2.web.dto.BookCrawlingDto;
import com.lim.poly.project2.web.dto.BookSearchRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class BookController {

    private final CrawlingService crawlingService;
    private final BookService bookService;

    @PostMapping("/book/info")
    public @ResponseBody List<Book> bookSearch(@RequestBody BookSearchRequestDto bookSearchRequestDto) throws Exception {

        List<Book> bookDtoList = null;
        log.info(String.valueOf(bookSearchRequestDto.getSearchBy()));
        bookDtoList = bookService.getBookSearchInfo(bookSearchRequestDto.getSearchBy());

        if (bookDtoList == null) {
            bookDtoList = new ArrayList<Book>();
        }

        return bookDtoList;
    }
}
