package com.example.security.ott;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static com.example.security.ott.OttConfiguration.SENT_URL;

@Controller
class OttController {

	@GetMapping(SENT_URL)
	String sent() {
		// <.>
		return "ott-sent";
	}

}
