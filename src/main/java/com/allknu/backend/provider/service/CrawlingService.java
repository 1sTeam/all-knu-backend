package com.allknu.backend.provider.service;

import com.allknu.backend.core.service.CrawlingServiceInterface;
import com.allknu.backend.core.types.EventNoticeType;
import com.allknu.backend.core.types.MajorNoticeType;
import com.allknu.backend.core.types.UnivNoticeType;
import com.allknu.backend.web.dto.ResponseCrawling;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;

@Service
public class CrawlingService implements CrawlingServiceInterface {

    private ObjectMapper objectMapper;

    @PostConstruct
    void init() {
        objectMapper = new ObjectMapper();
    }

    @Override
    public Optional<List<ResponseCrawling.UnivNotice>> getUnivNotice(int pageNum, UnivNoticeType type) {
        List<ResponseCrawling.UnivNotice> lists = new ArrayList<>();

        //type에 따라 전체, 학사, 장학, 학습/상담, 취창업 공지 크롤링
        String url = "***REMOVED***?paginationInfo.currentPageNo="
                +pageNum+"&searchMenuSeq=" + type.getSearchMenuNumber() + "&searchType=&searchValue=";

        try {
            Document doc = Jsoup.connect(url).get();

            Iterator<Element> rows = doc.select("div.tbody > ul").iterator();
            while(rows.hasNext()) {
                Element target = rows.next();
                Elements li = target.select("li"); // ul 안의 li들

                String number = li.get(0).text(); // 게시글번호 li
                if(!StringUtil.isNumeric(number)) {
                    //넘버가 숫자가 아니라면 필독공지임 이거는 패스
                    continue;
                }

                Element linkElement = li.get(1).selectFirst("a.detailLink"); // 링크li
                JsonNode jsonNode = objectMapper.readTree(linkElement.attr("data-params"));
                String encMenuSeq = jsonNode.get("encMenuSeq").asText();
                String encMenuBoardSeq = jsonNode.get("encMenuBoardSeq").asText();
                String link = "***REMOVED***?scrtWrtiYn=false&encMenuSeq="
                        + encMenuSeq + "&encMenuBoardSeq=" + encMenuBoardSeq;

                String title = linkElement.text();
                String writeType = li.get(2).text(); //구분 li
                String writer = li.get(4).text(); // 작성자
                String date = li.get(5).text(); // date
                String views = li.get(6).text(); // views

                ResponseCrawling.UnivNotice notice = ResponseCrawling.UnivNotice.builder()
                        .link(link)
                        .date(date)
                        .number(number)
                        .writer(writer)
                        .type(writeType)
                        .views(views)
                        .title(title)
                        .build();

                lists.add(notice);
            }

        } catch (IOException e) {
            System.out.println(e);
        }

        return Optional.ofNullable(lists);
    }
    @Override
    public Optional<List<ResponseCrawling.EventNotice>> getEventNotice(int pageNum, EventNoticeType type) {
        List<ResponseCrawling.EventNotice> lists = new ArrayList<>();

        //type에 따라 전체, 학사, 장학, 학습/상담, 취창업 공지 크롤링
        String url = "***REMOVED***?paginationInfo.currentPageNo="
                +pageNum+"&searchMenuSeq=" + type.getSearchMenuNumber() + "&searchType=&searchValue=";

        try {
            Document doc = Jsoup.connect(url).get();

            Iterator<Element> rows = doc.select("div.tbody > ul").iterator();
            while (rows.hasNext()) {
                Element target = rows.next();
                Elements dl = target.select("li > div > dl"); // li안에 div안에 dl들
                Elements dt = dl.select("dt");  //title
                Elements span = dl.select("dd > span");  //작성자, 등록일, 조회수

                String title = dt.get(0).text(); // 제목 title

                Element linkElement = dt.get(0).selectFirst("a.detailLink"); // 링크li
                JsonNode jsonNode = objectMapper.readTree(linkElement.attr("data-params"));
                String encMenuSeq = jsonNode.get("encMenuSeq").asText();
                String encMenuBoardSeq = jsonNode.get("encMenuBoardSeq").asText();
                String link = "***REMOVED***?scrtWrtiYn=false&encMenuSeq="
                        + encMenuSeq + "&encMenuBoardSeq=" + encMenuBoardSeq;

                String[] writer = span.get(0).text().split(" "); // 작성자
                String[] date = span.get(1).text().split(" "); // date
                String[] views = span.get(2).text().split(" "); // views
                String department = writer[1];
                String tel = writer[writer.length-1];


                ResponseCrawling.EventNotice eventsInformation = ResponseCrawling.EventNotice.builder()
                        .link(link)
                        .date(date[1])
                        .writer(writer[2])
                        .views(views[1])
                        .department(department)
                        .tel(tel)
                        .title(title)
                        .build();

                lists.add(eventsInformation);
            }
        }
        catch (IOException e) {
            System.out.println(e);
        }

        return Optional.ofNullable(lists);
    }
    @Override
    public Optional<List<ResponseCrawling.UnivNotice>> getMajorDefaultTemplateNotice(int pageNum, MajorNoticeType type) {
        List<ResponseCrawling.UnivNotice> lists = new ArrayList<>();
        String url = type.getUrl() + "?paginationInfo.currentPageNo=" + pageNum;

        try {
            Document doc = Jsoup.connect(url).get();

            //구분이 있는 학과 공지도 있고 구분이 없는 학과 공지가 있다.
            int tableHeadSize = doc.select("div.thead li").size(); // 테이블 헤드 길이 계산, 6개면 세부구분이 없는 학과

            Iterator<Element> rows = doc.select("div.tbody > ul").iterator();
            while(rows.hasNext()) {
                Element target = rows.next();
                Elements li = target.select("li"); // ul 안의 li들

                String number = li.get(0).text(); // 게시글번호 li
                if(!StringUtil.isNumeric(number)) {
                    //넘버가 숫자가 아니라면 필독공지임 이거는 패스
                    continue;
                }

                Element linkElement = li.get(1).selectFirst("a.detailLink"); // 링크li
                JsonNode jsonNode = objectMapper.readTree(linkElement.attr("data-params"));
                String encMenuSeq = jsonNode.get("encMenuSeq").asText();
                String encMenuBoardSeq = jsonNode.get("encMenuBoardSeq").asText();
                String link = type.getBoardUrl() + "?encMenuSeq=" + encMenuSeq + "&encMenuBoardSeq=" + encMenuBoardSeq;

                ResponseCrawling.UnivNotice notice = null;
                if(tableHeadSize == 7) {
                    //소응 처럼 세부 구분이 있는 학과 공지사항
                    String title = linkElement.text();
                    String writeType = li.get(2).text(); //구분 li
                    String writer = li.get(4).text(); // 작성자
                    String date = li.get(5).text(); // date
                    String views = li.get(6).text(); // views

                    notice = ResponseCrawling.UnivNotice.builder()
                            .link(link)
                            .date(date)
                            .number(number)
                            .writer(writer)
                            .type(writeType)
                            .views(views)
                            .title(title)
                            .build();
                } else if(tableHeadSize == 5) {
                    //글경은 5개..ㅎㅎ
                    String title = linkElement.text();
                    String views = li.get(2).text(); // views
                    String writer = li.get(3).text(); // 작성자
                    String date = li.get(4).text(); // date

                    notice = ResponseCrawling.UnivNotice.builder()
                            .link(link)
                            .date(date)
                            .number(number)
                            .writer(writer)
                            .views(views)
                            .title(title)
                            .build();
                } else {
                    //교육학과처럼 세부 구분이 없는 학과 공지사항
                    String title = linkElement.text();
                    String writer = li.get(3).text(); // 작성자
                    String date = li.get(4).text(); // date
                    String views = li.get(5).text(); // views

                    notice = ResponseCrawling.UnivNotice.builder()
                            .link(link)
                            .date(date)
                            .number(number)
                            .writer(writer)
                            .views(views)
                            .title(title)
                            .build();
                }
                lists.add(notice);
            }

        } catch (IOException e) {
            System.out.println(e);
        }
        return Optional.ofNullable(lists);
    }
    @Override
    public Optional<Map<String, List<ResponseCrawling.Schedule>>> getKnuCalendar(){
        Map<String, List<ResponseCrawling.Schedule>> monthMap = new LinkedHashMap<>();
        String url = "***REMOVED***?tab=2";
        String[] month = {"jan","feb","mar","apr","may","jun","jul","aug","sep","oct","nov","dec"};
        int idx =0;
        try{
            Document doc = Jsoup.connect(url).get();
            Iterator<Element> rows = doc.select(".cal_list").iterator();
            while(rows.hasNext()){
                List<ResponseCrawling.Schedule> scheduleList = new ArrayList<>();
                //월별 날짜와 일정 내용
                Iterator<Element> trs = rows.next().select("div.tbl.typeA.calendal_list > table > tbody > tr").iterator();
                while(trs.hasNext()){
                    String[] tr = trs.next().text().split(" ");
                    ResponseCrawling.Schedule schedule = ResponseCrawling.Schedule.builder()
                            .date(tr[0])
                            .content(tr[1])
                            .build();
                    scheduleList.add(schedule);
                }
                monthMap.put(month[idx++],scheduleList);
            }

        }catch (IOException e){
            System.out.println(e);
        }
        return Optional.ofNullable(monthMap);
    }
}
