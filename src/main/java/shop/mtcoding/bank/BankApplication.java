package shop.mtcoding.bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing //--> User entity에서 날짜 자동으로 입력되게 하기 위해서 추가해야 할 어노테이션
@SpringBootApplication
public class BankApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankApplication.class, args);
		/*
		ConfigurableApplicationContext context = SpringApplication.run(BankApplication.class, args);
		String[] iocNames = context.getBeanDefinitionNames();
		for ( String iocName: iocNames) {
			System.out.println(iocName);
		}
		*/
	}

}
