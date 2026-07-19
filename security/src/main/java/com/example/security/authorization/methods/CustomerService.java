package com.example.security.authorization.methods;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
class CustomerService {

	// <.>
	private final Map<Long, Customer> customers = Map.of(//
			1L, new Customer("josh@joshlong.com"), //
			2L, new Customer("user@anotherdomain.site")//
	);//

	@PreAuthorize("hasRole('ADMIN')") // <.>
	@PostAuthorize(" returnObject  != null  && returnObject.username == authentication.name ") // <.>
	Customer readPrivilegedCustomerData(long id) {
		return this.customers.getOrDefault(id, null);
	}

}

record Customer(String username) {
}
