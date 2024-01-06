package com.allknu.backend.knuapi.domain.scraper;

import com.allknu.backend.global.asset.ApiEndpointSecretProperties;
import com.allknu.backend.global.crawling.JsoupDocumentDownloader;
import com.allknu.backend.global.crawling.Scraper;
import com.allknu.backend.knuapi.domain.scraper.dto.KnuCalenderScraperResponseDto;
import lombok.Builder;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.*;

public class KnuCalenderScraper implements Scraper<KnuCalenderScraperResponseDto> {

    private final JsoupDocumentDownloader jsoupDocumentDownloader;
    private static final String[] MONTH = {
            "jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec"
    };

    @Builder
    public KnuCalenderScraper(ApiEndpointSecretProperties apiEndpointSecretProperties) {
        String url = apiEndpointSecretProperties.getCrawling().getKnuCalendar() + "?tab=2";

        this.jsoupDocumentDownloader = JsoupDocumentDownloader.builder()
                .url(url)
                .build();
    }

    @Override
    public KnuCalenderScraperResponseDto scrap() throws IOException {
        Document document = jsoupDocumentDownloader.execute();

        Map<String, List<KnuCalenderScraperResponseDto.CalendarItem>> monthMap = new LinkedHashMap<>();
        int idx = 0;

        for (Element element : document.select(".cal_list")) {
            List<KnuCalenderScraperResponseDto.CalendarItem> scheduleList = new ArrayList<>();

            //월별 날짜와 일정 내용
            for (Element target : element.select("div.tbl.typeA.calendal_list > table > tbody > tr")) {
                String th = target.select("th").text();
                String td = target.select("td").text();

                KnuCalenderScraperResponseDto.CalendarItem schedule = KnuCalenderScraperResponseDto.CalendarItem.builder()
                        .date(th)
                        .content(td)
                        .build();

                scheduleList.add(schedule);
            }
            monthMap.put(MONTH[idx++], scheduleList);
        }

        return new KnuCalenderScraperResponseDto(monthMap);
    }
}
