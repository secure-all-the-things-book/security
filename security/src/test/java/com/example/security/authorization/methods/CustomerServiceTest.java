package com.example.security.authorization.methods;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;

@SpringBootTest
class CustomerServiceTest {

	private final CustomerService customerService;

	@Autowired
	CustomerServiceTest(CustomerService customerService) {
		this.customerService = customerService;
	}

	// <.>
	@Test
	@WithMockUser(username = "josh@joshlong.com", roles = { "ADMIN", "USER" })
	void good() {
		try {
			var customer = this.customerService.readPrivilegedCustomerData(1);
			Assertions.assertEquals("josh@joshlong.com", customer.username(), "the id should be 1");
		} //
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// <.>
	@Test
	@WithMockUser(username = "bob", roles = { "USER" })
	void badPost() {
		Assertions.assertThrows(AccessDeniedException.class, () -> this.customerService.readPrivilegedCustomerData(2));
	}

	// <.>
	@Test
	@WithMockUser(username = "bob")
	void badPre() {
		Assertions.assertThrows(AccessDeniedException.class, () -> this.customerService.readPrivilegedCustomerData(1));
	}

}