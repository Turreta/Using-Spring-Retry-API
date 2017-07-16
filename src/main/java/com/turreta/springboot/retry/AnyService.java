package com.turreta.springboot.retry;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AnyService
{
	List<String> data = Stream.of("AB", "AC", "XY").collect(Collectors.toList());

	public List<String> getAllCodes_ThrowException() throws Exception {
		throw new Exception("Service not available at this time!");
	}

	public List<String> getAllCodes_Recovery() throws Exception {
		System.out.println("Recovered!");
		return Collections.EMPTY_LIST;
	}

	public List<String> getAllCodes_Ok() {
		System.out.println("Successfully retrieved data!");
		return data;
	}

	public void updateCode_Ok(String code, String status) {

		if(!data.contains(code)) {
			throw new RuntimeException("Code not found!");
		}

		System.out.println("Code status successfully updated!");
	}


	public void updateCode_Recovery(String code, String status) throws Exception {
		System.out.println("Recovered!");
	}

	public void updateCode_ThrowException(String code, String status) throws Exception {
		throw new Exception("Service not available at this time!");
	}
}
