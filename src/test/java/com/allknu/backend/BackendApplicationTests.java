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
		String url = "***REMOVED***?paginationInfo.currentPageNo="
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
		String url = "***REMOVED***?user_id=20171234&user_pwd=1234";
		try {

			Connection.Response res = Jsoup.connect(url)
					.method(Connection.Method.GET)
					.ignoreContentType(true)
					.userAgent("***REMOVED***")
					.execute();

			System.out.println(res.body());
			ObjectMapper mapper = new ObjectMapper();
			JsonNode jsonNode = mapper.readTree(res.body()); // json mapper

			if(jsonNode.get("result").equals("success")) {
				System.out.println("로그인 성공");
				Map<String, String> cookies = res.cookies();

				for( Map.Entry<String, String> elem : cookies.entrySet() ){
					System.out.println( String.format("키 : %s, 값 : %s", elem.getKey(), elem.getValue()) );
				}
			} else {
				System.out.println("로그인 실패");
			}
 		} catch (IOException e) {
			System.out.println(e);
		}
	}
}
