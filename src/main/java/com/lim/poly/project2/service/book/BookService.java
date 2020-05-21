package com.lim.poly.project2.service.book;

import com.lim.poly.project2.domain.Book;
import com.lim.poly.project2.domain.BookRepository;
import com.lim.poly.project2.service.CrawlingService;
import com.lim.poly.project2.util.DateUtil;
import com.lim.poly.project2.web.dto.BookCrawlingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class BookService {

    private final CrawlingService crawlingService;
    private final BookRepository bookRepository;

    public List<Book> getBookSearchInfo(String searchBy) throws Exception {
        List<Book> bookList = bookRepository.findBookByDate(searchBy, DateUtil.getDateTime("yyyyMMdd"));

        if (bookList == null) {
            bookList = new ArrayList<Book>();
        }
        if (bookList.size() == 0) {
            crawlingService.searchBookAndSave(searchBy);
            bookList = bookRepository.findBookByDate(searchBy, DateUtil.getDateTime("yyyyMMdd"));

            if (bookList == null) {
                bookList = new ArrayList<Book>();
            }
        }
        return bookList;
    }
}
