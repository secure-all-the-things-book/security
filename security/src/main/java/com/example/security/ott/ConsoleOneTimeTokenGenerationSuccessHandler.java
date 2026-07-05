package com.example.security.ott;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.ott.OneTimeToken;
import org.springframework.security.web.authentication.ott.OneTimeTokenGenerationSuccessHandler;
import org.springframework.security.web.authentication.ott.RedirectOneTimeTokenGenerationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;

import static com.example.security.ott.OttConfiguration.SENT_URL;

@Component
class ConsoleOneTimeTokenGenerationSuccessHandler implements OneTimeTokenGenerationSuccessHandler {

	// <.>
	private final OneTimeTokenGenerationSuccessHandler successHandler = new RedirectOneTimeTokenGenerationSuccessHandler(
			SENT_URL);

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, OneTimeToken oneTimeToken)
			throws IOException, ServletException {

		// <.>
		var url = ServletUriComponentsBuilder.fromContextPath(request) //
			.path("/login/ott") //
			.queryParam("token", oneTimeToken.getTokenValue()) //
			.build();

		// <.>
		IO.println("sending email " + oneTimeToken.getUsername() + " who should go to " + url);

		// <.>
		this.successHandler.handle(request, response, oneTimeToken);
	}

}
