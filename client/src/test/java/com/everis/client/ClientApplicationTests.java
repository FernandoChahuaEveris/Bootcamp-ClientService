package com.everis.client;

import com.everis.client.dao.entity.ClientPersonal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.UUID;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ClientApplicationTests {

	private int port = 8083;

	@Test
	public void postAccount(){
		 ClientPersonal account = ClientPersonal.builder()
				 .name("Bruno")
				 .lastName("diaz")
				 .dni("12345678")
				 .build();

		try {
			WebTestClient.bindToServer()
					.baseUrl("http://localhost:" + port)
					.build()
					.post()
					.uri("/client/personal")
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
					.bodyValue(account)
					.exchange()
					.expectStatus().isCreated();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
