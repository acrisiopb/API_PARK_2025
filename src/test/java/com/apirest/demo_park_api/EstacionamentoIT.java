package com.apirest.demo_park_api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;
import com.apirest.demo_park_api.web.dto.EstacionamentoCreateDto;
import com.apirest.demo_park_api.web.dto.PageableDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/estacionamentos/estacionamentos-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/estacionamentos/estacionamentos-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class EstacionamentoIT {

        @Autowired
        WebTestClient testClient;

        @Test
        public void criarCheckin_ComDadosValidos_RetornarCreatedAndLocation() {
                EstacionamentoCreateDto createDto = EstacionamentoCreateDto.builder()
                                .placa("WER-1111").marca("FIAT").modelo("PALIO 1.0")
                                .cor("AZUL").clienteCpf("09191773016")
                                .build();

                testClient.post().uri("/api/v1/estacionamentos/check-in")
                                .contentType(MediaType.APPLICATION_JSON)
                                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "testeApi@gmail.com",
                                                "123456"))
                                .bodyValue(createDto)
                                .exchange()
                                .expectStatus().isCreated()
                                .expectHeader().exists(HttpHeaders.LOCATION)
                                .expectBody()
                                .jsonPath("placa").isEqualTo("WER-1111")
                                .jsonPath("marca").isEqualTo("FIAT")
                                .jsonPath("modelo").isEqualTo("PALIO 1.0")
                                .jsonPath("cor").isEqualTo("AZUL")
                                .jsonPath("clienteCpf").isEqualTo("09191773016")
                                .jsonPath("recibo").exists()
                                .jsonPath("dataEntrada").exists()
                                .jsonPath("vagaCodigo").exists();
        }

        @Test
        public void criarCheckin_ComRoleCliente_RetornarErrorStatus403() {
                EstacionamentoCreateDto createDto = EstacionamentoCreateDto.builder()
                                .placa("WER-1111").marca("FIAT").modelo("PALIO 1.0")
                                .cor("AZUL").clienteCpf("09191773016")
                                .build();

                testClient.post().uri("/api/v1/estacionamentos/check-in")
                                .contentType(MediaType.APPLICATION_JSON)
                                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "teste-Api1@gmail.com",
                                                "123456"))
                                .bodyValue(createDto)
                                .exchange()
                                .expectStatus().isForbidden()
                                .expectBody()
                                .jsonPath("status").isEqualTo("403")
                                .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-in")
                                .jsonPath("method").isEqualTo("POST");
        }

        @Test
        public void criarCheckin_ComDadosInvalidos_RetornarErrorStatus422() {
                EstacionamentoCreateDto createDto = EstacionamentoCreateDto.builder()
                                .placa("").marca("").modelo("")
                                .cor("").clienteCpf("")
                                .build();

                testClient.post().uri("/api/v1/estacionamentos/check-in")
                                .contentType(MediaType.APPLICATION_JSON)
                                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "testeApi@gmail.com",
                                                "123456"))
                                .bodyValue(createDto)
                                .exchange()
                                .expectStatus().isEqualTo(422)
                                .expectBody()
                                .jsonPath("status").isEqualTo("422")
                                .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-in")
                                .jsonPath("method").isEqualTo("POST");
        }

        @Test
        public void criarCheckin_ComCPFInexistente_RetornarErrorStatus404() {
                EstacionamentoCreateDto createDto = EstacionamentoCreateDto.builder()
                                .placa("WER-1111").marca("FIAT").modelo("PALIO 1.0")
                                .cor("AZUL").clienteCpf("84143280086") // CPF não está na base de dados vai ocorrer 404
                                .build();

                testClient.post().uri("/api/v1/estacionamentos/check-in")
                                .contentType(MediaType.APPLICATION_JSON)
                                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "testeApi@gmail.com",
                                                "123456"))
                                .bodyValue(createDto)
                                .exchange()
                                .expectStatus().isNotFound()
                                .expectBody()
                                .jsonPath("status").isEqualTo("404")
                                .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-in")
                                .jsonPath("method").isEqualTo("POST");
        }

        /* Teste com sql personalizado / caminho */
        @Sql(scripts = "/sql/estacionamentos/estacionamentos-insert-vagas-ocupadas.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        @Sql(scripts = "/sql/estacionamentos/estacionamentos-delete-vagas-ocupadas.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
        @Test
        public void criarCheckin_ComVagasOcupadas_RetornarErrorStatus404() {
                EstacionamentoCreateDto createDto = EstacionamentoCreateDto.builder()
                                .placa("WER-1111").marca("FIAT").modelo("PALIO 1.0")
                                .cor("AZUL").clienteCpf("09191773016")
                                .build();

                testClient.post().uri("/api/v1/estacionamentos/check-in")
                                .contentType(MediaType.APPLICATION_JSON)
                                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "testeApi@gmail.com",
                                                "123456"))
                                .bodyValue(createDto)
                                .exchange()
                                .expectStatus().isNotFound()
                                .expectBody()
                                .jsonPath("status").isEqualTo("404")
                                .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-in")
                                .jsonPath("method").isEqualTo("POST");
        }

        @Test
        public void criarCheckin_ComPerfilAdmin_RetornarErrorStatus200() {

                testClient.get()
                                .uri("/api/v1/estacionamentos/check-in/{recibo}", "20230313-101300")
                                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "testeApi@gmail.com",
                                                "123456"))
                                .exchange()
                                .expectStatus().isOk()
                                .expectBody()
                                .jsonPath("placa").isEqualTo("FIT-1020")
                                .jsonPath("marca").isEqualTo("FIAT")
                                .jsonPath("modelo").isEqualTo("PALIO")
                                .jsonPath("cor").isEqualTo("VERDE")
                                .jsonPath("clienteCpf").isEqualTo("98401203015")
                                .jsonPath("recibo").isEqualTo("20230313-101300")
                                .jsonPath("dataEntrada").isEqualTo("2023-03-13 10:15:00")
                                .jsonPath("vagaCodigo").isEqualTo("A-01");
        }

        @Test
        public void criarCheckin_ComPerfilCliente_RetornarErrorStatus200() {

                testClient.get()
                                .uri("/api/v1/estacionamentos/check-in/{recibo}", "20230313-101300")
                                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "testeApi@gmail.com",
                                                "123456"))
                                .exchange()
                                .expectStatus().isOk()
                                .expectBody()
                                .jsonPath("placa").isEqualTo("FIT-1020")
                                .jsonPath("marca").isEqualTo("FIAT")
                                .jsonPath("modelo").isEqualTo("PALIO")
                                .jsonPath("cor").isEqualTo("VERDE")
                                .jsonPath("clienteCpf").isEqualTo("98401203015")
                                .jsonPath("recibo").isEqualTo("20230313-101300")
                                .jsonPath("dataEntrada").isEqualTo("2023-03-13 10:15:00")
                                .jsonPath("vagaCodigo").isEqualTo("A-01");
        }

        @Test
        public void buscarCheckin_ComReciboInexistente_RetornarErrorStatus404() {

                testClient.get()
                                .uri("/api/v1/estacionamentos/check-in/{recibo}", "20230313-101302")
                                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "testeApi@gmail.com",
                                                "123456"))
                                .exchange()
                                .expectStatus()
                                .isNotFound()
                                .expectBody()
                                .jsonPath("status").isEqualTo("404")
                                .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-in/20230313-101302")
                                .jsonPath("method").isEqualTo("GET");
        }

        @Test
        public void criarCheckOut_ComReciboExistente_RetornarSucesso() {

                testClient.put()
                                .uri("/api/v1/estacionamentos/check-out/{recibo}", "20230313-101300")
                                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "testeApi@gmail.com",
                                                "123456"))
                                .exchange()
                                .expectStatus().isOk()
                                .expectBody()
                                .jsonPath("placa").isEqualTo("FIT-1020")
                                .jsonPath("marca").isEqualTo("FIAT")
                                .jsonPath("modelo").isEqualTo("PALIO")
                                .jsonPath("cor").isEqualTo("VERDE")
                                .jsonPath("dataEntrada").isEqualTo("2025-03-13 10:15:00")
                                .jsonPath("clienteCpf").isEqualTo("98401203015")
                                .jsonPath("vagaCodigo").isEqualTo("A-01")
                                .jsonPath("recibo").isEqualTo("20230313-101300")
                                .jsonPath("dataSaida").exists()
                                .jsonPath("valor").exists()
                                .jsonPath("desconto").exists();

        }

        @Test
        public void criarCheckOut_ComReciboExistente_RetornarErrorStatus404() {

                testClient.put()
                                .uri("/api/v1/estacionamentos/check-out/{recibo}", "20230313-101388") // Recibo Invalido
                                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "testeApi@gmail.com",
                                                "123456"))
                                .exchange()
                                .expectStatus().isNotFound()
                                .expectBody()
                                .jsonPath("status").isEqualTo("404")
                                .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-out/20230313-101388")
                                .jsonPath("method").isEqualTo("PUT");

        }

        @Test
        public void criarCheckOut_ComRoleCliente_RetornarErrorStatus403() {

                testClient.put()
                                .uri("/api/v1/estacionamentos/check-out/{recibo}", "20230313-101300") // Recibo Invalido
                                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "teste-Api1@gmail.com",
                                                "123456"))
                                .exchange()
                                .expectStatus().isForbidden()
                                .expectBody()
                                .jsonPath("status").isEqualTo("403")
                                .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-out/20230313-101300")
                                .jsonPath("method").isEqualTo("PUT");

        }

        @Test
        public void buscarEstacionamentos_PorClienteCpf_RetornarSucesso() {

                PageableDTO responseBody = testClient.get()
                                .uri("/api/v1/estacionamentos/cpf/{cpf}?size=1&page=0", "98401203015") // Recibo
                                                                                                       // Invalido
                                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "testeApi@gmail.com",
                                                "123456"))
                                .exchange()
                                .expectStatus().isOk()
                                .expectBody(PageableDTO.class)
                                .returnResult()
                                .getResponseBody();

                org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
                org.assertj.core.api.Assertions.assertThat(responseBody.getContent().size()).isEqualTo(1);
                org.assertj.core.api.Assertions.assertThat(responseBody.getTotalPages()).isEqualTo(2);
                org.assertj.core.api.Assertions.assertThat(responseBody.getNumber()).isEqualTo(0);
                org.assertj.core.api.Assertions.assertThat(responseBody.getSize()).isEqualTo(1);

                responseBody = testClient.get()
                                .uri("/api/v1/estacionamentos/cpf/{cpf}?size=1&page=0", "98401203015") // Recibo
                                                                                                       // Invalido
                                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "testeApi@gmail.com",
                                                "123456"))
                                .exchange()
                                .expectStatus().isOk()
                                .expectBody(PageableDTO.class)
                                .returnResult()
                                .getResponseBody();

                org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
                org.assertj.core.api.Assertions.assertThat(responseBody.getContent().size()).isEqualTo(1);
                org.assertj.core.api.Assertions.assertThat(responseBody.getTotalPages()).isEqualTo(2);
                org.assertj.core.api.Assertions.assertThat(responseBody.getNumber()).isEqualTo(0);
                org.assertj.core.api.Assertions.assertThat(responseBody.getSize()).isEqualTo(1);

        }

        @Test
        public void buscarEstacionamentos_PorClienteCpfComPerfilCliente_RetornarErrorStatus403() {

                testClient.get()
                                .uri("/api/v1/estacionamentos/cpf/{cpf}", "98401203015") // Recibo Invalido
                                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "teste-Api1@gmail.com",
                                                "123456"))
                                .exchange()
                                .expectStatus().isForbidden()
                                .expectBody()
                                .jsonPath("status").isEqualTo("403")
                                .jsonPath("path").isEqualTo("/api/v1/estacionamentos/cpf/98401203015")
                                .jsonPath("method").isEqualTo("GET");

        }

        @Test
        public void buscarEstacionamentos_DoClienteLogado_RetornarSucesso() {

                PageableDTO responseBody = testClient.get()
                                .uri("/api/v1/estacionamentos?size=1&page=0")
                                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "testeApi2@gmail.com",
                                                "123456"))
                                .exchange()
                                .expectStatus().isOk()
                                .expectBody(PageableDTO.class)
                                .returnResult().getResponseBody();

                org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
                org.assertj.core.api.Assertions.assertThat(responseBody.getContent().size()).isEqualTo(1);
                org.assertj.core.api.Assertions.assertThat(responseBody.getTotalPages()).isEqualTo(2);
                org.assertj.core.api.Assertions.assertThat(responseBody.getNumber()).isEqualTo(0);
                org.assertj.core.api.Assertions.assertThat(responseBody.getSize()).isEqualTo(1);

                responseBody = testClient.get()
                                .uri("/api/v1/estacionamentos?size=1&page=1")
                                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "testeApi2@gmail.com",
                                                "123456"))
                                .exchange()
                                .expectStatus().isOk()
                                .expectBody(PageableDTO.class)
                                .returnResult().getResponseBody();

                org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
                org.assertj.core.api.Assertions.assertThat(responseBody.getContent().size()).isEqualTo(1);
                org.assertj.core.api.Assertions.assertThat(responseBody.getTotalPages()).isEqualTo(2);
                org.assertj.core.api.Assertions.assertThat(responseBody.getNumber()).isEqualTo(1);
                org.assertj.core.api.Assertions.assertThat(responseBody.getSize()).isEqualTo(1);
        }

        @Test
        public void buscarEstacionamentos_DoClienteLogadoPerfilAdmin_RetornarErrorStatus403() {

                testClient.get()
                                .uri("/api/v1/estacionamentos")
                                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "testeApi@gmail.com",
                                                "123456"))
                                .exchange()
                                .expectStatus().isForbidden()
                                .expectBody()
                                .jsonPath("status").isEqualTo("403")
                                .jsonPath("path").isEqualTo("/api/v1/estacionamentos")
                                .jsonPath("method").isEqualTo("GET");
        }
}
