package com.santex.webcrawler.model;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InternetPages {
	
	@Valid
	@NotEmpty
	private List<Page> pages;

}
