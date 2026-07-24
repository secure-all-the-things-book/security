package com.example.security.authorization.requests;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.Map;

@Profile("simpleauthorization")
@Controller
@ResponseBody
class ProfileController {

	static final String USER_PATH = "/user";

	static final String ADMIN_PATH = "/admin";

	@GetMapping(USER_PATH)
	Map<String, String> user(Principal principal) {
		return principal("user", principal.getName());
	}

	@GetMapping(ADMIN_PATH)
	Map<String, String> admin(Principal principal) {
		return principal("admin", principal.getName());
	}

	private Map<String, String> principal(String attribute, String principal) {
		return Map.of(attribute, principal);
	}

}
