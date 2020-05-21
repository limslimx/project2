package com.lim.poly.project2.service;

import com.lim.poly.project2.domain.Book;
import com.lim.poly.project2.domain.BookRepository;
import com.lim.poly.project2.util.CmmUtil;
import com.lim.poly.project2.util.DateUtil;
import com.lim.poly.project2.web.dto.BookCrawlingDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class CrawlingService {

    private final BookRepository bookRepository;

    public List<com.lim.poly.project2.domain.Book> searchBookAndSave(String searchBy) throws Exception {
        String url = "https://search.kyobobook.co.kr/web/search?vPstrKeyWord="+searchBy+"&orderClick=LAG";
        log.info(url);
        Document doc = Jsoup.connect(url).get();
        Elements elements = doc.select("tbody#search_list tr");
        for (Element element : elements) {
            //제목
            String title = element.select("div.title strong").text();
            //카테고리
            String category = element.select("div.category").text();
            //이미지
            Elements imageElements = element.select("div.cover img");
            String imageAttr = imageElements.attr("src");
            //상세카테고리
            String detailCategory = element.select("span.category2").text();
            //저자
            String author = element.select("div.author a:nth-child(1)").text();
            //태그
            String tag = element.select("div.tag a").text();
            //url
            Elements urlElements = element.select("div.title a");
            String urlAttr = urlElements.attr("href");

            // ---------------------------------- 여기서부터는 상세 url에서의 정보수집 -----------------------------------
            Document doc2 = Jsoup.connect(urlAttr).get();

            //소제목
            String title2 = doc2.select("h1.title span.back").text();
            //출간일
            String publicationDate = doc2.select("span.date").text();
            //순위
            String rank = doc2.select("div.rank a:nth-child(3) em").text();

            log.info("----------------------------------");
            log.info("해당책 크롤링 시작");
            log.info("검색어: "+searchBy);
            log.info("제목: "+title);
            log.info("카테고리: "+category);
            log.info("이미지: "+imageAttr);
            log.info("상세카테고리: "+detailCategory);
            log.info("저자: "+author);
            log.info("태그: "+tag);
            log.info("상세url: "+urlAttr);
            log.info("소제목: "+title2);
            log.info("출간일: "+publicationDate);
            log.info("순위: "+rank);
            if(!category.equals("국내도서")){
                log.info("출력x");
            }
            log.info("해당책 크롤링 완료");
            log.info("----------------------------------");

            if(category.equals("국내도서")){
                //bookDto에 데이터 저장
                BookCrawlingDto bookCrawlingDto = BookCrawlingDto.builder()
                        .searchDate(CmmUtil.nvl(DateUtil.getDateTime("yyyyMMdd")))
                        .searchBy(searchBy)
                        .name(CmmUtil.nvl(title))
                        .category(CmmUtil.nvl(category))
                        .img(CmmUtil.nvl(imageAttr))
                        .detailCategory(CmmUtil.nvl(detailCategory))
                        .author(CmmUtil.nvl(author))
                        .tag(CmmUtil.nvl(tag))
                        .url(CmmUtil.nvl(urlAttr))
                        .subName(CmmUtil.nvl(title2))
                        .publicationDate(CmmUtil.nvl(publicationDate))
                        .rank(CmmUtil.nvl(rank))
                        .build();

                //bookDto를 엔티티로 변환한 후에 저장
                Book book = bookCrawlingDto.toEntity();
                bookRepository.save(book);
            }
        }
        return bookRepository.findAll();
    }
}

