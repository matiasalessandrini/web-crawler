package com.santex.webcrawler.dto;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageStatisticsDTO {

	public PageStatisticsDTO(String startPage, Set<String> visitedPages, Set<String> skippedPages,
			Set<String> failedPages) {

		this.startPage = startPage;
		this.visitedPages = visitedPages;
		this.skippedPages = skippedPages;
		this.failedPages = failedPages;
	}

	@JsonProperty("Start Page")
	private String startPage;

	@JsonProperty("Success")
	private Set<String> visitedPages;

	@JsonProperty("Skipped")
	private Set<String> skippedPages;

	@JsonProperty("Error")
	private Set<String> failedPages;

}
