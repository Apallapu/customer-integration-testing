package com.poc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poc.model.Customer;
import com.poc.repository.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	CustomerRepository customerRepository;

	public Customer createCustomer(Customer customer) {

		return customerRepository.save(customer);

	}

	public Customer getCustomer(String name) {

		return customerRepository.findByFirstName(name);
	}

	public Customer updateCustomer(Customer customer, Long id) {
		return new Customer("ankamma", "raju");

	}

	public void deleteCustomer(Long id) {
		System.out.println("delete");
		// TODO Auto-generated method stub
		
	}

}
