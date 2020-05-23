package com.oracle.marthen.demo.graalvmspringbootdemo.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by Marthen on 22/05/20.
 */

@RestController
@RequestMapping("/graal")
public class BenchmarkController {

    @RequestMapping(method = RequestMethod.GET, value = "/benchmark/{iteration}", produces = {MediaType.TEXT_PLAIN_VALUE})
    String benchmark(@PathVariable final int iteration) {
        final int[] values = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		long result = 0;
		final long begin = System.currentTimeMillis();
		for (int i = 0; i < iteration; i++) {
			final int a = Arrays.stream(values).map(x -> x + 1).map(x -> x * 2).map(x -> x + 2).reduce(0, Integer::sum);
			result += a;
		}
		final long processTime = System.currentTimeMillis() - begin;
		final String output = "Time taken to complete in milliseconds: " + processTime + " ; and result is: " + result;
		System.out.println(output);
		// return new Response(HttpStatus.OK);
		return output;
    }

}
