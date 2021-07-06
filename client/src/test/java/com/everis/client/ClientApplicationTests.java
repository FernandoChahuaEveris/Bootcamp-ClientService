package com.everis.client;

import com.everis.client.dao.entity.CreditCardPersonal;
import com.everis.client.dao.entity.personal.ClientPersonal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ClientApplicationTests {

    @Value("${server.port}")
    String port;

    @Test
    public void testApplyVipToClientPersonal() {
        String dni = "12345678";
        try {
            WebTestClient.bindToServer()
                    .baseUrl("http://localhost:" + port)
                    .build()
                    .post()
                    .uri("/client/personal/vip/" + dni)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isOk();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testApplyPymeToClientEnterprise() {
        String ruc = "12345678";
        try {
            WebTestClient.bindToServer()
                    .baseUrl("http://localhost:" + port)
                    .build()
                    .post()
                    .uri("/client/enterprise/pyme/" + ruc)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isOk();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
