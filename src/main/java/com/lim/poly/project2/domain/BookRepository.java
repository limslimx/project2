package com.lim.poly.project2.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<com.lim.poly.project2.domain.Book, Long> {

    @Query("select b from Book b where b.searchBy=:searchBy and b.searchDate=:searchDate")
    List<Book> findBookByDate(@Param("searchBy") String searchBy, @Param("searchDate") String searchDate);

    Book findBookByIdAndSearchDate(Long id, String searchDate);
}
