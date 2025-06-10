package com.apirest.demo_park_api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.apirest.demo_park_api.web.dto.ClienteCreateDTO;
import com.apirest.demo_park_api.web.dto.ClienteResponseDto;
import com.apirest.demo_park_api.web.dto.PageableDTO;
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
                                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "testeApi3@gmail.com",
                                                "123456"))
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
                                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "testeApi3@gmail.com",
                                                "123456"))
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
                                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "testeApi3@gmail.com",
                                                "123456"))
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
                                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "testeApi3@gmail.com",
                                                "123456"))
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
                                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "testeApi3@gmail.com",
                                                "123456"))
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
                                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "testeApi@gmail.com",
                                                "123456"))
                                .bodyValue(new ClienteCreateDTO("acrisio cruz", "89395284099"))
                                .exchange()
                                .expectStatus().isEqualTo(403)
                                .expectBody(ErrorMessage.class)
                                .returnResult().getResponseBody();

                org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
                org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
        }

        @Test
        public void buscarCliente_ComIdExistentePeloAdmin_RetornarClienteStatus200() {
                ClienteResponseDto responseBody = testClient
                                .get()
                                .uri("/api/v1/clientes/10")
                                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "testeApi@gmail.com",
                                                "123456"))
                                .exchange()
                                .expectStatus().isOk()
                                .expectBody(ClienteResponseDto.class)
                                .returnResult().getResponseBody();

                org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
                org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(10);
        }

        @Test
        public void buscarCliente_ComIdInexistentePeloAdmin_RetornarErrorMessageComStatus404() {
                ErrorMessage responseBody = testClient
                                .get()
                                .uri("/api/v1/clientes/0")
                                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "testeApi@gmail.com",
                                                "123456"))
                                .exchange()
                                .expectStatus().isNotFound()
                                .expectBody(ErrorMessage.class)
                                .returnResult().getResponseBody();

                org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
                org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
        }

        @Test
        public void buscarCliente_ComIdExistentePeloCliente_RetornarErrorMessageComStatus403() {
                ErrorMessage responseBody = testClient
                                .get()
                                .uri("/api/v1/clientes/0")
                                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "teste-Api1@gmail.com",
                                                "123456"))
                                .exchange()
                                .expectStatus().isForbidden()
                                .expectBody(ErrorMessage.class)
                                .returnResult().getResponseBody();

                org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
                org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
        }

        @Test
        public void buscarCliente_ComPaginacaoPeloAdmin_RetornarClientesComStatus200() {
                PageableDTO responseBody = testClient
                                .get()
                                .uri("/api/v1/clientes")
                                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "testeApi@gmail.com",
                                                "123456"))
                                .exchange()
                                .expectStatus().isOk()
                                .expectBody(PageableDTO.class)
                                .returnResult().getResponseBody();

                org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
                org.assertj.core.api.Assertions.assertThat(responseBody.getContent().size()).isEqualTo(3);
                org.assertj.core.api.Assertions.assertThat(responseBody.getNumber()).isEqualTo(0);
                org.assertj.core.api.Assertions.assertThat(responseBody.getTotalPages()).isEqualTo(1);

                responseBody = testClient
                                .get()
                                .uri("/api/v1/clientes?size=1&page=1")
                                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "testeApi@gmail.com",
                                                "123456"))
                                .exchange()
                                .expectStatus().isOk()
                                .expectBody(PageableDTO.class)
                                .returnResult().getResponseBody();

                org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
                org.assertj.core.api.Assertions.assertThat(responseBody.getContent().size()).isEqualTo(1);
                org.assertj.core.api.Assertions.assertThat(responseBody.getNumber()).isEqualTo(1);
                org.assertj.core.api.Assertions.assertThat(responseBody.getTotalPages()).isEqualTo(3);

        }

        @Test
        public void buscarCliente_ComPaginacaoPeloCliente_RetornarClientes_RetornarErrorMessageComStatus403() {
                ErrorMessage responseBody = testClient
                                .get()
                                .uri("/api/v1/clientes")
                                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "teste-Api1@gmail.com",
                                                "123456"))
                                .exchange()
                                .expectStatus().isForbidden()
                                .expectBody(ErrorMessage.class)
                                .returnResult().getResponseBody();

                org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
                org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);

        }

        @Test
        public void buscarCliente_ComDadosDoTokenDeCliente_RetornarClienteComStatus200() {
                ClienteResponseDto responseBody = testClient
                                .get()
                                .uri("/api/v1/clientes/detalhes")
                                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "teste-Api1@gmail.com",
                                                "123456"))
                                .exchange()
                                .expectStatus().isOk()
                                .expectBody(ClienteResponseDto.class)
                                .returnResult().getResponseBody();

                org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
                org.assertj.core.api.Assertions.assertThat(responseBody.getCpf()).isEqualTo("48644714015");
                org.assertj.core.api.Assertions.assertThat(responseBody.getNome()).isEqualTo("Raditz API");


        }

        @Test
        public void buscarCliente_ComDadosDoTokenDeAdministrador_RetornarErrorMessageComStatus403() {
                ErrorMessage responseBody = testClient
                                .get()
                                .uri("/api/v1/clientes/detalhes")
                                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "testeApi@gmail.com",
                                                "123456"))
                                .exchange()
                                .expectStatus().isForbidden()
                                .expectBody(ErrorMessage.class)
                                .returnResult().getResponseBody();

                org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
                org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);


        }
}
