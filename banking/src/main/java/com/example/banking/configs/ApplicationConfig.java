package com.example.banking.configs;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import com.example.banking.entities.account.AccountRiskLevel;

@Configuration
@PropertySources({
	@PropertySource("classpath:application.properties"),
	@PropertySource("classpath:properties/account.properties"),
	@PropertySource("classpath:properties/customer.properties"),
	@PropertySource("classpath:properties/email.properties"),
	@PropertySource("classpath:properties/currency.properties"),
})
@ComponentScan("com.example.banking")
@EnableAspectJAutoProxy
@EnableScheduling
@EnableAsync
public class ApplicationConfig {
	
	@Value("${account.type.limit.high}")
	private float highLimit;
	@Value("${account.type.limit.medium}")
	private float mediumLimit;
	@Value("${account.type.limit.low}")
	private float lowLimit;
	
	@Bean(name = "accountLimitByType")
	public Map<AccountRiskLevel, Float> buildAccountLimitByRiskTypeMap() {
		return new HashMap<AccountRiskLevel, Float>() {{
			put(AccountRiskLevel.HIGH, highLimit);
			put(AccountRiskLevel.MEDIUM, mediumLimit);
			put(AccountRiskLevel.LOW, lowLimit);
		}};
	}
	
	@Bean
	public RestTemplate restTemplateCreator() {
		return new RestTemplate();
	}
	
}

