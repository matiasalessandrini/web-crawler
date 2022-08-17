package com.santex.webcrawler.model;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Page{
	
	@NotBlank
	private String address;
	
	@NotEmpty
	private List<String> links;
	
}
