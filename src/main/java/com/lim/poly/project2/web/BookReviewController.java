package com.lim.poly.project2.web;

import com.lim.poly.project2.domain.Book;
import com.lim.poly.project2.domain.BookRepository;
import com.lim.poly.project2.service.bookReview.BookReviewService;
import com.lim.poly.project2.util.DateUtil;
import com.lim.poly.project2.web.dto.BookReviewListDto;
import com.lim.poly.project2.web.dto.BookReviewSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class BookReviewController {

    private final BookRepository bookRepository;
    private final BookReviewService bookReviewService;

    @GetMapping(value = "/bookReview/{id}")
    public String bookReviewAjax(@PathVariable Long id, Model model) {
        log.info(this.getClass().getName() + ".bookReviewAjax start!");
        log.info("###############bookReviewAjax`s id"+id);

        Book book = bookRepository.findBookByIdAndSearchDate(id, DateUtil.getDateTime("yyyyMMdd"));

        model.addAttribute("id", book.getId());
        model.addAttribute("img", book.getImg());
        model.addAttribute("name", book.getName());
        model.addAttribute("author", book.getAuthor());
        model.addAttribute("category", book.getDetailCategory());

        log.info(book.getId().toString());
        log.info(book.getImg());
        log.info(book.getName());
        log.info(book.getAuthor());
        log.info(book.getDetailCategory());

        log.info(this.getClass().getName() + ".bookReviewAjax end!");
        return "bookReview/reg";
    }

    @PostMapping("/bookReview/save")
    public @ResponseBody Long bookReviewSave(@RequestBody BookReviewSaveRequestDto bookReviewSaveRequestDto, HttpSession httpSession) {
        log.info(this.getClass().getName() + ".bookReviewSave start!");

        String userId = (String) httpSession.getAttribute("userId");
        log.info("############bookReviewSave`s userId: "+userId);
        log.info(this.getClass().getName() + ".bookReviewSave end!");
        return bookReviewService.save(userId, bookReviewSaveRequestDto);
    }

    @GetMapping("/bookReview/list")
    public String bookReviewList(HttpSession httpSession, Model model) {
        log.info(this.getClass().getName() + ".bookReviewList start!");
        String userId = (String)httpSession.getAttribute("userId");
        List<BookReviewListDto> bookReviewList = bookReviewService.getBookReviewList(userId);
        model.addAttribute("bookReviewList", bookReviewList);
        return "bookReview/list";
    }

    @GetMapping("/bookReview/list2")
    public String bookReviewList2(HttpSession httpSession, Model model) {
        log.info(this.getClass().getName() + ".bookReviewList start!");
        String userId = (String)httpSession.getAttribute("userId");
        List<BookReviewListDto> bookReviewList = bookReviewService.getBookReviewList(userId);
        model.addAttribute("bookReviewList", bookReviewList);
        return "bookReview/list2";
    }
}
