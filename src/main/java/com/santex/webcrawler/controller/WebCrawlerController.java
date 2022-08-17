package com.santex.webcrawler.controller;

import javax.validation.Valid;

import javax.validation.constraints.NotBlank;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.santex.webcrawler.dto.PageStatisticsDTO;
import com.santex.webcrawler.model.InternetPages;
import com.santex.webcrawler.service.WebCrawlerService;


@Validated
@RestController
@RequestMapping("/webcrawler")
public class WebCrawlerController{
	
	private WebCrawlerService webCrawlerService;
	
	public WebCrawlerController(WebCrawlerService webCrawlerService) {
		this.webCrawlerService = webCrawlerService;
	}
	
	@PostMapping(value = "/crawl")
	public ResponseEntity<PageStatisticsDTO> crawl(@NotBlank @RequestParam String address, @Valid @RequestBody InternetPages pageMap) {
		return ResponseEntity.ok(webCrawlerService.crawl(address, pageMap));
	}
}
