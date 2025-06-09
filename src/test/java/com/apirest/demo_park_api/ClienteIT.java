package com.apirest.demo_park_api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.apirest.demo_park_api.web.dto.ClienteCreateDTO;
import com.apirest.demo_park_api.web.dto.ClienteResponseDto;
import com.apirest.demo_park_api.web.exception.ErrorMessage;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/clientes/clientes-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/clientes/clientes-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)

public class ClienteIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public void criarCliente_comDadosValidos_RetornarClienteComStatus201() {
        ClienteResponseDto responseBody = testClient
                .post()
                .uri("/api/v1/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "testeApi3@gmail.com", "123456"))
                .bodyValue(new ClienteCreateDTO("testeApi3@gmail.com", "27321466094"))
                .exchange()
                .expectBody(ClienteResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getNome()).isEqualTo("testeApi3@gmail.com");
        org.assertj.core.api.Assertions.assertThat(responseBody.getCpf()).isEqualTo("27321466094");

    }

    @Test
    public void criarCliente_comDadosValidos_RetornarErrorMessageStatus409() {
        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/v1/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "testeApi3@gmail.com", "123456"))
                .bodyValue(new ClienteCreateDTO("acrisio cruz", "89395284099"))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(409);
    }

    @Test
    public void criarCliente_comDadosInvalidos_RetornarErrorMessageStatus422() {
        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/v1/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "testeApi3@gmail.com", "123456"))
                .bodyValue(new ClienteCreateDTO("", ""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient
                .post()
                .uri("/api/v1/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "testeApi3@gmail.com", "123456"))
                .bodyValue(new ClienteCreateDTO("acris", "11111111111"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient
                .post()
                .uri("/api/v1/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "testeApi3@gmail.com", "123456"))
                .bodyValue(new ClienteCreateDTO("acris", "893.952.840-99"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void criarCliente_comUsuariosNaoPermitido_RetornarErrorMessageStatus403() {
        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/v1/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "testeApi@gmail.com", "123456"))
                .bodyValue(new ClienteCreateDTO("acrisio cruz", "89395284099"))
                .exchange()
                .expectStatus().isEqualTo(403)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
    }

}
