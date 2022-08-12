package com.santex.webcrawler.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.santex.webcrawler.dto.PageStatisticsDTO;
import com.santex.webcrawler.exception.BadRequestException;
import com.santex.webcrawler.model.InternetPages;
import com.santex.webcrawler.service.WebCrawlerService;
import com.santex.webcrawler.validator.ControllerValidator;

@RestController
@RequestMapping("/webcrawler")
public class WebCrawlerController {
	
	private WebCrawlerService webCrawlerService;
	
	public WebCrawlerController(WebCrawlerService webCrawlerService) {
		this.webCrawlerService = webCrawlerService;
	}
	
	@PostMapping(value = "/crawl")
	public ResponseEntity<PageStatisticsDTO> crawl(@RequestParam String address, @RequestBody InternetPages pageMap) {
		if (!ControllerValidator.isValidParameter().test(address)) {
			throw new BadRequestException("Invalid parameter");
		}
		return ResponseEntity.ok(webCrawlerService.crawl(address, pageMap));
	}
}
