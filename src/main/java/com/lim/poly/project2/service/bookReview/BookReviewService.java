package com.lim.poly.project2.service.bookReview;

import com.lim.poly.project2.domain.*;
import com.lim.poly.project2.web.dto.BookReviewSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class BookReviewService {

    private final BookReviewRepository bookReviewRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;


    public Long save(String userId, BookReviewSaveRequestDto bookReviewSaveRequestDto) {
        log.info(this.getClass().getName() + ".save start!");

        Member member = memberRepository.findOneByuId(userId);
        log.info("###############save`s member.getId(): "+member.getUId());
        log.info("###############save`s bookReviewSaveRequestDto.getId(): "+bookReviewSaveRequestDto.getId());
        Optional<Book> byId = bookRepository.findById(bookReviewSaveRequestDto.getId());
        Book book = byId.get();
        BookReview bookReview = BookReview.createBookReview(bookReviewSaveRequestDto.getTitle(), bookReviewSaveRequestDto.getContent(), member, book);

        bookReviewRepository.save(bookReview);

        log.info(this.getClass().getName() + ".save end!");
        return bookReview.getId();
    }
}
