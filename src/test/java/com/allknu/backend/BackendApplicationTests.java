package com.allknu.backend;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.Iterator;

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

}
