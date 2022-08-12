package com.santex.webcrawler.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
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

		List<CompletableFuture<Void>> taskList = new ArrayList<>();
		PageStatisticsDTO pageStatisticsDTO = new PageStatisticsDTO(address, visitedPages, skippedPages, failedPages);
		parse(address, pagesMap, pageStatisticsDTO, taskList);
		CompletableFuture.allOf(taskList.toArray(new CompletableFuture[taskList.size()])).join();
		return pageStatisticsDTO;
	}

	private void parse(String address, Map<String, Page> pagesMap, PageStatisticsDTO pageStatisticsDTO,
			List<CompletableFuture<Void>> taskList) {

		Page page = pagesMap.get(address);
		if (page != null) {
			if (!pageStatisticsDTO.getVisitedPages().contains(address)) {
				pageStatisticsDTO.getVisitedPages().add(address);
				page.getLinks().forEach(pageAddress -> CompletableFuture
						.runAsync(() -> parse(pageAddress, pagesMap, pageStatisticsDTO, taskList)));

			} else {
				pageStatisticsDTO.getSkippedPages().add(address);
			}
		} else {
			pageStatisticsDTO.getFailedPages().add(address);
		}
	}

}
