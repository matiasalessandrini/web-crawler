package com.santex.webcrawler.service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.santex.webcrawler.dto.PageStatisticsDTO;
import com.santex.webcrawler.model.InternetPages;
import com.santex.webcrawler.model.Page;

@Service
public class WebCrawlerServiceImpl implements WebCrawlerService {

	@Override
	public PageStatisticsDTO crawl(String address, InternetPages iPages) {

		Map<String, Page> pagesMap = iPages.getPages().stream()
				.collect(Collectors.toMap(Page::getAddress, Function.identity()));
		
		Set<String> visitedPages = ConcurrentHashMap.newKeySet();
		Set<String> skippedPages = ConcurrentHashMap.newKeySet();
		Set<String> failedPages = ConcurrentHashMap.newKeySet();
		PageStatisticsDTO pageStatisticsDTO = new PageStatisticsDTO(address, visitedPages, skippedPages, failedPages);
		Crawler crawler = new Crawler(address, pagesMap, pageStatisticsDTO);
		new ForkJoinPool().invoke(crawler);
		return pageStatisticsDTO;
	}

	

}
