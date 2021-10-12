package com.example.servingwebcontent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;


@Controller
public class MainController {

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) 
	{
		return builder.build();
	}

	@Autowired
	private RestTemplate restTemplate;
	private String server_URL = "http://127.0.0.1:5000/";
	private String action = "";


    /**
     * Serves a html webpage with operations related to OAuth
     **/

	@GetMapping("/")
	public String oauth(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model)
	{
		action = "Please Click login with linkedIn Button to generate access token!";
		String auth_URL = server_URL + "login";
		model.addAttribute("auth_url", auth_URL );
		model.addAttribute("output", "response");
		model.addAttribute("action", action);
		return "oauthli";
	}

	/**
     * Handles the post requests of Html page, calls the API endpoints of server URL
	 * to return a response and updates it on UI
     **/

	@PostMapping(path = "/", produces = {"application/json", "application/xml"},  consumes = {"application/x-www-form-urlencoded"})
	public String postBody(@RequestBody String post_Array, Model model) 
	{
		String response = "";

				if(post_Array == "profile=Get+Profile")
				{
					response = restTemplate.getForObject(server_URL + "profile", String.class);

				}else if(post_Array == "refresh_token=Refresh+Token")
				{
					response = restTemplate.getForObject(server_URL + "refresh_token", String.class);
				}
				else //token_Introspection=Token+Introspection
				{
				 	response = restTemplate.getForObject(server_URL + "token_Introspection", String.class);
				}

		model.addAttribute("output", response);
		model.addAttribute("action", post_Array);
		return "oauthli";
	}

}
