package com.santex.webcrawler.testing.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.santex.webcrawler.dto.PageStatisticsDTO;
import com.santex.webcrawler.model.InternetPages;
import com.santex.webcrawler.service.WebCrawlerService;

@SpringBootTest
public class WebCrawlServiceTest {

	@Autowired
	private WebCrawlerService underTest;
	
	private InternetPages iPages;

	@BeforeEach
	void setUp() {
		loadIPages();
	}
	
	@Test
	void testCrawlPage01() {

		// Given a page address
		String address = "page-01";

		// When
		PageStatisticsDTO pageStatisticsDTO = underTest.crawl(address, iPages);

		// Then
		assertThat(pageStatisticsDTO.getStartPage()).isEqualTo(address);
		assertThat(pageStatisticsDTO.getVisitedPages()).containsOnly( "page-99",
													        "page-01",
													        "page-04",
													        "page-05",
													        "page-02",
													        "page-03",
													        "page-08",
													        "page-09",
													        "page-06",
													        "page-07");

		assertThat(pageStatisticsDTO.getSkippedPages()).containsOnly( "page-01",
													        "page-04",
													        "page-05",
													        "page-02",
													        "page-03",
													        "page-08",
													        "page-09");
		
		assertThat(pageStatisticsDTO.getFailedPages()).containsOnly(  "page-11",
													        "page-00",
													        "page-12",
													        "page-10",
													        "page-13");
	}

	@Test
	void testCrawlPage50() {

		// Given a page address
		String address = "page-50";

		// When
		PageStatisticsDTO pageStatisticsDTO = underTest.crawl(address, iPages);

		// Then
		assertThat(pageStatisticsDTO.getStartPage()).isEqualTo(address);
		assertThat(pageStatisticsDTO.getVisitedPages()).containsOnly("page-50", "page-51", "page-52");
		assertThat(pageStatisticsDTO.getSkippedPages()).containsOnly("page-50");
		assertThat(pageStatisticsDTO.getFailedPages()).containsOnly("page-53");
	}
	
	@Test
	void testCrawlPage60() {

		// Given a page address
		String address = "page-60";

		// When
		PageStatisticsDTO pageStatisticsDTO = underTest.crawl(address, iPages);

		// Then
		assertThat(pageStatisticsDTO.getStartPage()).isEqualTo(address);
		assertThat(pageStatisticsDTO.getVisitedPages()).isEmpty();
		assertThat(pageStatisticsDTO.getSkippedPages()).isEmpty();
		assertThat(pageStatisticsDTO.getFailedPages()).containsOnly("page-60");
	}
	
	
	private void loadIPages() {
		try {
			ClassPathResource resource = new ClassPathResource("internet.json");
			iPages = new ObjectMapper().readValue(resource.getFile(), InternetPages.class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
