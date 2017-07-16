package com.turreta.springboot.retry;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
public class CustomConfiguration
{
	@Bean
	public RetryTemplate retryTemplate() {
		RetryTemplate retryTemplate = new RetryTemplate();

		/* Start - Fixed BackOff */
		// FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
		// fixedBackOffPolicy.setBackOffPeriod(2000l);
		// retryTemplate.setBackOffPolicy(fixedBackOffPolicy);
		/* End - Fixed BackOff */

		ExponentialBackOffPolicy exponentialBackOffPolicy = new ExponentialBackOffPolicy();
		exponentialBackOffPolicy.setMultiplier(1.5);
		exponentialBackOffPolicy.setInitialInterval(10000); // 10 seconds
		exponentialBackOffPolicy.setMaxInterval(30000); // 30 seconds max
		retryTemplate.setBackOffPolicy(exponentialBackOffPolicy);

		//SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
		//retryPolicy.setMaxAttempts(2);
		//retryTemplate.setRetryPolicy(retryPolicy);

		return retryTemplate;
	}
}
