package com.example.security.events;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.core.context.SecurityContextChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
class SecurityEvents {

	private static String message(ApplicationEvent applicationEvent) {
		Assert.notNull(applicationEvent, "the event should not be null");
		return applicationEvent.getClass().getName() + " ::: " + applicationEvent;
	}

	@EventListener
	void onSecurityEvent(ApplicationEvent securityEvent) {
		var eventMessage = switch (securityEvent) {
			case AbstractAuthenticationEvent abstractAuthenticationEvent -> message(abstractAuthenticationEvent);
			case SecurityContextChangedEvent securityContextChangedEvent -> message(securityContextChangedEvent);
			default -> null;
		};
		IO.println("security result: " + eventMessage);
	}

}
