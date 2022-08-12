package com.santex.webcrawler.testing.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
public class WebCrawlControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void testCanCrawlPage() throws Exception {
		
		MvcResult getStudentsResult = mockMvc.perform(get("/webcrawler/crawl?address=page-01")
					.content(getRequestJSON())
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
					.andReturn();

		String contentAsString = getStudentsResult.getResponse().getContentAsString();
		assertThat(contentAsString).contains("page-01");
		
	}
	
	@Test
	void testInvalidParameter() throws Exception {

		MvcResult getStudentsResult = mockMvc.perform(get("/webcrawler/crawl?address=")
					.content(getRequestJSON())
					.contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isBadRequest())
					.andReturn();

		String contentAsString = getStudentsResult.getResponse().getContentAsString();
		assertThat(contentAsString).contains("Invalid parameter");
		
	}
	
	private String getRequestJSON() {
		try {
			ClassPathResource resource = new ClassPathResource("internet.json");
			return Files.readString(Path.of(resource.getURI()), StandardCharsets.US_ASCII);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
