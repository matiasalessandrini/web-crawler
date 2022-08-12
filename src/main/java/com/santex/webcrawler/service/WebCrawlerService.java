package com.santex.webcrawler.service;

import com.santex.webcrawler.dto.PageStatisticsDTO;
import com.santex.webcrawler.model.InternetPages;

public interface WebCrawlerService {
	
	PageStatisticsDTO crawl(String address, InternetPages iPages);
	
}
