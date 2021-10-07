package com.allknu.backend;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@SpringBootTest
@ActiveProfiles("local")
class BackendApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void crawlingTest() {
		String url = "https://web.kangnam.ac.kr/menu/f19069e6134f8f8aa7f689a4a675e66f.do?paginationInfo.currentPageNo="
				+1+"&searchMenuSeq=0&searchType=&searchValue=";

		try {
			Document doc = Jsoup.connect(url).get();
			Elements elem = doc.select("div.tbody > ul");

			Iterator<Element> links = elem.select("a.detailLink").iterator();

			while (links.hasNext()) {
				Element target = links.next();
				System.out.println(target.text());
				System.out.println(target.attr("data-params"));
			}

		} catch (IOException e) {
			System.out.println(e);
		}

	}

	@Test
	void loginKnuTest() {
		String url = "https://m.kangnam.ac.kr/knusmart/c/c001.do?user_id=201704017&user_pwd=1234";
		try {

			Connection.Response res = Jsoup.connect(url)
					.method(Connection.Method.GET)
					.ignoreContentType(true)
					.userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.61 Safari/537.36")
					.execute();

			System.out.println(res.body());
			ObjectMapper mapper = new ObjectMapper();
			JsonNode jsonNode = mapper.readTree(res.body()); // json mapper

			if(jsonNode.get("result").toString().equals("\"success\"")) {
				System.out.println("로그인 성공");
				Map<String, String> cookies = res.cookies();

				for( Map.Entry<String, String> elem : cookies.entrySet() ){
					System.out.println( String.format("키 : %s, 값 : %s", elem.getKey(), elem.getValue()) );
				}
				//시간표 조회 테스트
				url = "https://m.kangnam.ac.kr/knusmart/s/s251.do";
				Connection.Response timeRes = Jsoup.connect(url)
						.method(Connection.Method.GET)
						.ignoreContentType(true)
						.cookies(cookies) // 로그인 쿠키 삽입
						.userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.61 Safari/537.36")
						.execute();

				System.out.println(timeRes.body());// 결과
			} else {
				System.out.println("로그인 실패");
			}
 		} catch (IOException e) {
			System.out.println(e);
		}
	}
}
