package com.santex.webcrawler.service;

import java.util.Map;
import java.util.concurrent.RecursiveAction;

import com.santex.webcrawler.dto.PageStatisticsDTO;
import com.santex.webcrawler.model.Page;

public class Crawler extends RecursiveAction{
	
	private static final long serialVersionUID = 7967939683437295811L;
	
	private String address;
	private Map<String, Page> pagesMap;
	private PageStatisticsDTO pageStatisticsDTO;
	
	public Crawler(String address, Map<String, Page> pagesMap, PageStatisticsDTO pageStatisticsDTO) {
		this.address = address;
		this.pagesMap = pagesMap;
		this.pageStatisticsDTO = pageStatisticsDTO;
	}

	@Override
	protected void compute() {
		Page page = pagesMap.get(address);
		if (page != null) {
			if (pageStatisticsDTO.getVisitedPages().add(address)) {
				page.getLinks().forEach(pageAddress -> {
					Crawler crawler = new Crawler(pageAddress, pagesMap, pageStatisticsDTO);
					crawler.fork();
					crawler.join();
				});

			} else {
				pageStatisticsDTO.getSkippedPages().add(address);
			}
		} else {
			pageStatisticsDTO.getFailedPages().add(address);
		}
		
	}

}
