package com.everis.client;

import com.everis.client.dao.entity.personal.ClientPersonal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ClientApplicationTests {

	private int port = 8083;

	@Test
	public void postAccount(){

	}

}
