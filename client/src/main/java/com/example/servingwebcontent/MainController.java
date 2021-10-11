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
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Autowired
	private RestTemplate restTemplate;
	private String serverurl = "http://127.0.0.1:5000/";
	private String callapi_endpoint = "";
	private String action = "";

	@GetMapping("/")
	public String oauth(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model)
	{
		action = "Please Click login with linkedin Button to genrate access token!";
		String auth_url = serverurl + "login";
		model.addAttribute("auth_url", auth_url);
		model.addAttribute("output", "response");
		model.addAttribute("action", action);
		return "oauthli";
	}

	@PostMapping(path="/", produces = {"application/json", "application/xml"},  consumes = {"application/x-www-form-urlencoded"})
	public String postBody(@RequestBody String postarray, Model model) {
		String response = "";

				 if(postarray == "profile=Get+Profile")
				 {
					  response = restTemplate.getForObject(serverurl + "profile", String.class);
				 }else if(postarray == "refresh_token=Refresh+Token"){
					  response = restTemplate.getForObject(serverurl + "refresh_token", String.class);
				 }
				 else
				 {
				 		//token_Introspection=Token+Introspection
					 	response = restTemplate.getForObject(serverurl + "token_Introspection", String.class);
				 	}

		model.addAttribute("output", response);
		model.addAttribute("action", postarray);
		return "oauthli";
	}

}
