package com.oracle.marthen.demo.graalvmspringbootdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import com.oracle.marthen.demo.graalvmspringbootdemo.model.Customer;
import com.oracle.marthen.demo.graalvmspringbootdemo.model.CustomerRepository;

/**
 * Created by Marthen on 22/05/20.
 */

@RestController
@RequestMapping("/graal")
public class BenchmarkController {
	
	@Autowired
	CustomerRepository customerRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/benchmark/{iteration}", produces = {MediaType.TEXT_PLAIN_VALUE})
    String benchmark(@PathVariable final int iteration) {
        final int[] values = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		long result = 0;
		final long begin = System.currentTimeMillis();
		for (int i = 0; i < iteration; i++) {
			final int a = Arrays.stream(values)
								.map(x -> x + 1)
								.map(x -> x * 2)
								.map(x -> x + 2)
								.reduce(0, Integer::sum);
			result += a;
		}
		final long processTime = System.currentTimeMillis() - begin;
		final String output = "Time taken to complete in milliseconds: " + processTime + " ; and result is: " + result;
		System.out.println(output);
		return output;
	}
	
    @RequestMapping(method = RequestMethod.GET, value = "/benchmark-jpa/{iteration}", produces = {MediaType.TEXT_PLAIN_VALUE})
    String benchmarkJPA(@PathVariable final int iteration) {
		final long begin = System.currentTimeMillis();

		// iteration starts..
		// JPA save entity
		for (int i = 0; i < iteration; i++) {
			// save a few customers
			customerRepository.save(new Customer("Jack" + i, "Bauer" + i));
			customerRepository.save(new Customer("Chloe" + i, "O'Brian" + i));
		}

		// JPA query entity
		long totalRecords = customerRepository.count();
		long displayTheLast4RecordsInTheOuput = totalRecords - 4;
		long counter = 0;
		for (Customer customer : customerRepository.findAll()) {
			counter += 1;
			if (counter > displayTheLast4RecordsInTheOuput)
				System.out.println(customer.toString());
		}

		// fetch an individual customer by ID
		Customer customer = customerRepository.findById(1L);
		System.out.println("==> fetch an individual customer by ID 1 : " + customer.toString());

		// fetch customers by last name.. assuming there more than 50 iterations..
		customerRepository.findByLastName("Bauer50").forEach(bauer -> {
			System.out.println("==> fetch an individual customer by lastName 'Bauer50' : " + bauer.toString());
		});

		final long processTime = System.currentTimeMillis() - begin;
		final String output = "Time taken to complete in milliseconds: " + processTime;
		System.out.println(output);
		return output;
	}

}
