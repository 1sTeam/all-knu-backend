package com.allknu.backend.knuapi.domain.scraper;

import com.allknu.backend.global.asset.ApiEndpointSecretProperties;
import com.allknu.backend.global.crawling.JsoupDocumentDownloader;
import com.allknu.backend.global.crawling.Scraper;
import com.allknu.backend.knuapi.domain.scraper.dto.KnuVeriusSatisfactionSurveyScraperResponseDto;
import lombok.Builder;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class KnuVeriusSatisfactionSurveyScraper implements Scraper<KnuVeriusSatisfactionSurveyScraperResponseDto> {

    private final ApiEndpointSecretProperties apiEndpointSecretProperties;
    private final JsoupDocumentDownloader jsoupDocumentDownloader;

    @Builder
    public KnuVeriusSatisfactionSurveyScraper(ApiEndpointSecretProperties apiEndpointSecretProperties,
                                              int page,
                                              Map<String, String> veriusCookies) {
        String url = apiEndpointSecretProperties.getCrawling().getVeriusMySatisfactionInfo()
                + "?CURRENT_MENU_CODE=MENU0052&TOP_MENU_CODE=MENU0010&CURR_PAGE=" + page;

        this.jsoupDocumentDownloader = JsoupDocumentDownloader.builder()
                .url(url)
                .cookies(veriusCookies)
                .build();

        this.apiEndpointSecretProperties = apiEndpointSecretProperties;
    }

    @Override
    public KnuVeriusSatisfactionSurveyScraperResponseDto scrap() throws IOException {
        Document document = jsoupDocumentDownloader.execute();
        List<KnuVeriusSatisfactionSurveyScraperResponseDto.KnuVeriusSatisfactionSurveyItem> list = new ArrayList<>();

        Elements views = document.select("div.bbsListLot tbody"); // bbsListLot tbody를 리스트로 가져온다.
        Iterator<Element> rows = views.select("tr").iterator();

        while (rows.hasNext()) {
            KnuVeriusSatisfactionSurveyScraperResponseDto.KnuVeriusSatisfactionSurveyItem satisfaction;

            Element target = rows.next();
            Elements td = target.select("td"); // td들

            //끝처리
            if(td.size() < 5) break;

            String number = td.get(0).text();
            String name = td.get(1).text();
            String endDate = td.get(2).text();
            String satisfactionEndDate = td.get(3).text();
            String status = td.get(4).text();

            // 만족도조사 링크를 따온다.
            String link = null;
            String linkOnClickText = td.get(4).select("a").attr("onclick");
            int startIdx = linkOnClickText.indexOf("(");
            int endIdx = linkOnClickText.indexOf(")");
            if (endIdx - startIdx > 1) {
                // 링크가 존재
                String[] params = linkOnClickText.substring(startIdx + 1, endIdx).split(",");
                for (int i = 0 ; i < params.length ; i++) {
                    params[i] = params[i].replace("'", "");
                }
                link = apiEndpointSecretProperties.getCrawling().getVeriusMySatisfactionInfo()
                        + "?CURRENT_MENU_CODE=MENU0052&TOP_MENU_CODE=MENU0010"
                        + "&SURVEY_SEQ=" + params[0] + "&SURVEY_GB=" + params[1] + "&INPUT_SEQ=" + params[2];
            }

            // 더 효율적인 방법이 있능가
            // 앞에 strong 태그 내용 삭제
            name = name.substring(name.indexOf(" ") + 1); // 앞에 띄어쓰기 되어있더라, + 1해서 지운다.
            endDate = endDate.substring(endDate.indexOf(" ") + 1);
            satisfactionEndDate = satisfactionEndDate.substring(satisfactionEndDate.lastIndexOf(" ") + 1);
            status = status.substring(status.indexOf(" ") + 1);

            // 만족도조사 종료일이 없다면 위의 결과는 "종료일"이고 그러면 null 넣기
            if (satisfactionEndDate.equals("종료일")) {
                satisfactionEndDate = null;
            }

            satisfaction = KnuVeriusSatisfactionSurveyScraperResponseDto.KnuVeriusSatisfactionSurveyItem.builder()
                    .number(number)
                    .name(name)
                    .operationEndDate(endDate)
                    .satisfactionEndDate(satisfactionEndDate)
                    .status(status)
                    .link(link)
                    .build();

            list.add(satisfaction);
        }

        return new KnuVeriusSatisfactionSurveyScraperResponseDto(list);
    }
}
