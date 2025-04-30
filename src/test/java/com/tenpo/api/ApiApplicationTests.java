package com.tenpo.api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
  "spring.datasource.url=jdbc:h2:mem:testdb",
  "spring.datasource.driver-class-name=org.postgresql.Driver",
  "spring.jpa.hibernate.ddl-auto=none"
})
class ApiApplicationTests {

	@Test
	void contextLoads() {
	}

}
