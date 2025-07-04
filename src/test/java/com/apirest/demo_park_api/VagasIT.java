package com.apirest.demo_park_api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.apirest.demo_park_api.web.dto.VagaCreateDTO;
import com.apirest.demo_park_api.web.exception.ErrorMessage;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/vagas/vagas-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = "/sql/vagas/vagas-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)

public class VagasIT {
  @Autowired
  WebTestClient testClient;

  @Test
  public void criarVaga_ComDadosValidos_RetornarLocationStatus201() {

    testClient
        .post()
        .uri("/api/v1/vagas")
        .contentType(MediaType.APPLICATION_JSON)
        .headers(JwtAuthentication.getHeaderAuthorization(testClient, "testeApi@gmail.com", "123456"))
        .bodyValue(new VagaCreateDTO("A-10", "LIVRE"))
        .exchange()
        .expectStatus().isCreated()
        .expectHeader().exists(HttpHeaders.LOCATION);
  }

  @Test
  public void criarVaga_ComCodigoJaExistente_RetornarErrorMessageComStatus409() {
    testClient
        .post()
        .uri("/api/v1/vagas")
        .contentType(MediaType.APPLICATION_JSON)
        .headers(JwtAuthentication.getHeaderAuthorization(testClient, "testeApi@gmail.com", "123456"))
        .bodyValue(new VagaCreateDTO("A-01", "LIVRE"))
        .exchange()
        .expectStatus().isEqualTo(409)
        .expectBody()
        .jsonPath("status").isEqualTo(409)
        .jsonPath("method").isEqualTo("POST")
        .jsonPath("path").isEqualTo("/api/v1/vagas");

  }

  @Test
  public void criarVaga_ComDadosInvalidos_RetornarErrorMessageComStatus422() {
    testClient
        .post()
        .uri("/api/v1/vagas")
        .contentType(MediaType.APPLICATION_JSON)
        .headers(JwtAuthentication.getHeaderAuthorization(testClient, "testeApi@gmail.com", "123456"))
        .bodyValue(new VagaCreateDTO("", ""))
        .exchange()
        .expectStatus().isEqualTo(422)
        .expectBody()
        .jsonPath("status").isEqualTo(422)
        .jsonPath("method").isEqualTo("POST")
        .jsonPath("path").isEqualTo("/api/v1/vagas");

    testClient
        .post()
        .uri("/api/v1/vagas")
        .contentType(MediaType.APPLICATION_JSON)
        .headers(JwtAuthentication.getHeaderAuthorization(testClient, "testeApi@gmail.com", "123456"))
        .bodyValue(new VagaCreateDTO("ASD-5", "DESOCUPADA"))
        .exchange()
        .expectStatus().isEqualTo(422)
        .expectBody()
        .jsonPath("status").isEqualTo(422)
        .jsonPath("method").isEqualTo("POST")
        .jsonPath("path").isEqualTo("/api/v1/vagas");

  }

  @Test
  public void buscarVaga_ComCodigoExistente_RetornarVAgaComStatus200() {
    testClient
        .get()
        .uri("/api/v1/vagas/{codigo}", "A-01")
        .headers(JwtAuthentication.getHeaderAuthorization(testClient, "testeApi@gmail.com", "123456"))
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .jsonPath("id").isEqualTo(10)
        .jsonPath("codigo").isEqualTo("A-01 ")
        .jsonPath("status").isEqualTo("LIVRE");

  }

  @Test
  public void buscarVaga_ComCodigoInexistente_RetornarErrorMessageComStatus404() {
    testClient
        .get()
        .uri("/api/v1/vagas/{codigo}", "a-100")
        .headers(JwtAuthentication.getHeaderAuthorization(testClient, "testeApi@gmail.com", "123456"))
        .exchange()
        .expectStatus().isNotFound()
        .expectBody()
        .jsonPath("status").isEqualTo(404)
        .jsonPath("method").isEqualTo("GET")
        .jsonPath("path").isEqualTo("/api/v1/vagas/a-100");

  }
   
   @Test
        public void buscarVaga_ComUsuarioSemPermissao_RetornarErrorMessageComStatus403() {
                ErrorMessage responseBody = testClient
                                .get()
                                 .uri("/api/v1/vagas/{codigo}", "A-01")
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
        public void adicionarVaga_ComUsuarioSemPermissao_RetornarErrorMessageComStatus403() {
                ErrorMessage responseBody = testClient
                                .post()
                                 .uri("/api/v1/vagas")
                                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "teste-Api1@gmail.com",
                                                "123456"))
                                .bodyValue(new VagaCreateDTO("A-35","LIVRE"))
                                                .exchange()
                                .expectStatus().isForbidden()
                                .expectBody(ErrorMessage.class)
                                .returnResult().getResponseBody();

                org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
                org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
        }

}
