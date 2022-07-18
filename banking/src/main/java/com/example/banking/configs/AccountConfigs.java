package com.example.banking.configs;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import com.example.banking.entities.account.AccountRiskLevel;


@Configuration
@PropertySource(value = "classpath:properties/accounts.properties")
@ComponentScan("com.example.banking")
@EnableAspectJAutoProxy
public class AccountConfigs {
	
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
	
}
