package com.lim.poly.project2.domain;

import com.lim.poly.project2.web.dto.BookReviewListDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookReviewRepository extends JpaRepository<BookReview, Long> {

    @Query("select new com.lim.poly.project2.web.dto.BookReviewListDto(br.title, br.content, br.chgDate, b.name, b.img, b.author, b.detailCategory) from BookReview br join Book b on br.book.id=b.id where br.member.uId=:userId")
    List<BookReviewListDto> findBookReviewListDtoByUserId(@Param("userId") String userId);
}
