package com.turreta.springboot.retry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.RetryTemplate;

import java.util.Collections;
import java.util.List;

@SpringBootApplication
public class ComTurretaSpringbootRetryApplication
{

	public static void main(String[] args) throws Exception
	{
		ApplicationContext context = SpringApplication.run(ComTurretaSpringbootRetryApplication.class, args);

		AnyService anyService = context.getBean(AnyService.class);
		RetryTemplate retryTemplate = context.getBean(RetryTemplate.class);

		// ##### Example 1
		List<String> data = retryTemplate.execute(
				new RetryCallback<List<String>, Exception>()
				{
					@Override public List<String> doWithRetry(RetryContext retryContext) throws Exception
					{
						System.out.println("Trying...");
						return anyService.getAllCodes_ThrowException();
					}
				}, new RecoveryCallback<List<String>>()
				{

					@Override public List<String> recover(RetryContext retryContext) throws Exception
					{
						return Collections.EMPTY_LIST;
					}
				});

		// Empty list - no output
		data.forEach(System.out::print);

		// ##### Example 2
		List<String> dataTwo = retryTemplate.execute(

				// Retries
				retryContext ->
				{
					System.out.println("Trying...");
					return anyService.getAllCodes_Ok();
				}
				,

				// Recover
				retryContext -> Collections.EMPTY_LIST
		);

		// Successfully retrieved data
		dataTwo.forEach(System.out::println);

		// ##### Example 3
		retryTemplate.execute(

				// Retries
				retryContext ->
				{
					System.out.println("Trying...");
					anyService.updateCode_ThrowException("AA", "OK");
					return null;
				}
				,

				// Recover
				retryContext ->
				{
					anyService.updateCode_Recovery("AA", "OK-BUT-ERROR");
					return null;
				}

		);

		// ##### Example 4
		retryTemplate.execute(

				// Retries
				retryContext ->
				{
					System.out.println("Trying...");
					anyService.updateCode_Ok("AB", "OK");
					return null;
				}
				,

				// Recover
				retryContext ->
				{
					anyService.updateCode_Recovery("AA", "OK-BUT-ERROR");
					return null;
				}
		);
	}
}
