package com.apirest.demo_park_api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.apirest.demo_park_api.web.dto.UsuarioCreateDTO;
import com.apirest.demo_park_api.web.dto.UsuarioResponseDto;
import com.apirest.demo_park_api.web.dto.UsuarioSenhaDto;
import com.apirest.demo_park_api.web.exception.ErrorMessage;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/usuarios/usuarios-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/usuarios/usuarios-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UsuarioIT {

        @Autowired
        WebTestClient testClient;

        @Test
        public void createUsuario_comUsernameEPasswordValidos_RetornarUsuarioCriadoComStatus201() {
                UsuarioResponseDto responseBody = testClient
                                .post()
                                .uri("/api/v1/usuarios")
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(new UsuarioCreateDTO("teste@gmail.com", "123456"))
                                .exchange()
                                .expectStatus().isCreated()
                                .expectBody(UsuarioResponseDto.class)
                                .returnResult().getResponseBody();

                org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
                org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
                org.assertj.core.api.Assertions.assertThat(responseBody.getUsername()).isEqualTo("teste@gmail.com");
                org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("CLIENTE");

        }

        @Test
        public void createUsuario_comUsernameInvalido_RetornarErrorMessageStatus422() {
                ErrorMessage responseBody = testClient
                                .post()
                                .uri("/api/v1/usuarios")
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(new UsuarioCreateDTO("", "123456"))
                                .exchange()
                                .expectStatus().isEqualTo(422)
                                .expectBody(ErrorMessage.class)
                                .returnResult().getResponseBody();

                org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
                org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

                responseBody = testClient
                                .post()
                                .uri("/api/v1/usuarios")
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(new UsuarioCreateDTO("teste@", "123456"))
                                .exchange()
                                .expectStatus().isEqualTo(422)
                                .expectBody(ErrorMessage.class)
                                .returnResult().getResponseBody();

                org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
                org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

                responseBody = testClient
                                .post()
                                .uri("/api/v1/usuarios")
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(new UsuarioCreateDTO("teste@gmail.", "123456"))
                                .exchange()
                                .expectStatus().isEqualTo(422)
                                .expectBody(ErrorMessage.class)
                                .returnResult().getResponseBody();

                org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
                org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        }

        @Test
        public void createUsuario_comPasswordInvalido_RetornarErrorMessageStatus422() {
                ErrorMessage responseBody = testClient
                                .post()
                                .uri("/api/v1/usuarios")
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(new UsuarioCreateDTO("teste@gmail.com", ""))
                                .exchange()
                                .expectStatus().isEqualTo(422)
                                .expectBody(ErrorMessage.class)
                                .returnResult().getResponseBody();

                org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
                org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

                responseBody = testClient
                                .post()
                                .uri("/api/v1/usuarios")
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(new UsuarioCreateDTO("teste@gmail.com", "123456s2"))
                                .exchange()
                                .expectStatus().isEqualTo(422)
                                .expectBody(ErrorMessage.class)
                                .returnResult().getResponseBody();

                org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
                org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

                responseBody = testClient
                                .post()
                                .uri("/api/v1/usuarios")
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(new UsuarioCreateDTO("teste@gmail.com", "1234"))
                                .exchange()
                                .expectStatus().isEqualTo(422)
                                .expectBody(ErrorMessage.class)
                                .returnResult().getResponseBody();

                org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
                org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        }

        @Test
        public void createUsuario_comUsernameRepetido_RetornarErrorMEssageComStatus409() {
                ErrorMessage responseBody = testClient
                                .post()
                                .uri("/api/v1/usuarios")
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(new UsuarioCreateDTO("testeApi@gmail.com", "123456"))
                                .exchange()
                                .expectStatus().isEqualTo(409)
                                .expectBody(ErrorMessage.class)
                                .returnResult().getResponseBody();

                org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
                org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(409);

        }

        @Test
        public void buscarUsuario_comIdExistente_RetornarUsuarioComStatus200() {
                UsuarioResponseDto responseBody = testClient
                                .get()
                                .uri("/api/v1/usuarios/100")
                                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "testeApi@gmail.com",
                                                "123456"))
                                .exchange()
                                .expectStatus().isOk()
                                .expectBody(UsuarioResponseDto.class)
                                .returnResult().getResponseBody();

                org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
                org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(100);
                org.assertj.core.api.Assertions.assertThat(responseBody.getUsername()).isEqualTo("testeApi@gmail.com");
                org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("ADMIN");

                responseBody = testClient
                                .get()
                                .uri("/api/v1/usuarios/101")
                                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "testeApi@gmail.com",
                                                "123456"))
                                .exchange()
                                .expectStatus().isOk()
                                .expectBody(UsuarioResponseDto.class)
                                .returnResult().getResponseBody();

                org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
                org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(101);
                org.assertj.core.api.Assertions.assertThat(responseBody.getUsername())
                                .isEqualTo("teste-Api1@gmail.com");
                org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("CLIENTE");

                responseBody = testClient
                                .get()
                                .uri("/api/v1/usuarios/101")
                                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "teste-Api1@gmail.com",
                                                "123456"))
                                .exchange()
                                .expectStatus().isOk()
                                .expectBody(UsuarioResponseDto.class)
                                .returnResult().getResponseBody();

                org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
                org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(101);
                org.assertj.core.api.Assertions.assertThat(responseBody.getUsername())
                                .isEqualTo("teste-Api1@gmail.com");
                org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("CLIENTE");

        }

        @Test
        public void buscarUsuario_comIdInexistente_RetornarErrorMessageComStatus404() {
                ErrorMessage responseBody = testClient
                                .get()
                                .uri("/api/v1/usuarios/0")
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
        public void buscarUsuario_ComUsuarioClienteBuscandoOutroCliente_RetornarErrorMessageComStatus403() {
                ErrorMessage responseBody = testClient
                                .get()
                                .uri("/api/v1/usuarios/102")
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
        public void editarSenha_comDadosValidos_RetornarUsuarioCriadoComStatus204() {
                testClient
                                .patch()
                                .uri("/api/v1/usuarios/100")
                                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "testeApi@gmail.com",
                                                "123456"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(new UsuarioSenhaDto("123456", "123456", "123456"))
                                .exchange()
                                .expectStatus().isNoContent();

                testClient
                                .patch()
                                .uri("/api/v1/usuarios/101")
                                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "teste-Api1@gmail.com",
                                                "123456"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(new UsuarioSenhaDto("123456", "123456", "123456"))
                                .exchange()
                                .expectStatus().isNoContent();
        }

        // @Test
        // public void editarSenha_comIdInexistente_RetornarErrorMessageComStatus404() {
        // ErrorMessage responseBody = testClient
        // .put()
        // .uri("/api/v1/usuarios/0")
        // .contentType(MediaType.APPLICATION_JSON)
        // .bodyValue(new UsuarioSenhaDto("123456", "123456", "123456"))
        // .exchange()
        // .expectStatus().isNotFound()
        // .expectBody(ErrorMessage.class)
        // .returnResult().getResponseBody();

        // org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        // org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);

        // }

        @Test
        public void editarSenha_comUsuariosDiferentes_RetornarErrorMessageComStatus403() {
                ErrorMessage responseBody = testClient
                                .patch()
                                .uri("/api/v1/usuarios/0")
                                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "teste-Api1@gmail.com",
                                                "123456"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(new UsuarioSenhaDto("123456", "123456", "123456"))
                                .exchange()
                                .expectStatus().isForbidden()
                                .expectBody(ErrorMessage.class)
                                .returnResult().getResponseBody();

                org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
                org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);

                responseBody = testClient
                                .patch()
                                .uri("/api/v1/usuarios/0")
                                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "teste-Api1@gmail.com",
                                                "123456"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(new UsuarioSenhaDto("123456", "123456", "123456"))
                                .exchange()
                                .expectStatus().isForbidden()
                                .expectBody(ErrorMessage.class)
                                .returnResult().getResponseBody();

                org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
                org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);

        }

        @Test
        public void editarSenha_comCamposInvalidos_RetornarErrorMessageComStatus422() {
                ErrorMessage responseBody = testClient
                                .patch()
                                .uri("/api/v1/usuarios/100")
                                .contentType(MediaType.APPLICATION_JSON)
                                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "testeApi@gmail.com",
                                                "123456"))
                                .bodyValue(new UsuarioSenhaDto("", "", ""))
                                .exchange()
                                .expectStatus().isEqualTo(422)
                                .expectBody(ErrorMessage.class)
                                .returnResult().getResponseBody();

                org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
                org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

                responseBody = testClient
                                .patch()
                                .uri("/api/v1/usuarios/100")
                                .contentType(MediaType.APPLICATION_JSON)
                                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "testeApi@gmail.com",
                                                "123456"))
                                .bodyValue(new UsuarioSenhaDto("12", "12", "12"))
                                .exchange()
                                .expectStatus().isEqualTo(422)
                                .expectBody(ErrorMessage.class)
                                .returnResult().getResponseBody();

                org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
                org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

                responseBody = testClient
                                .patch()
                                .uri("/api/v1/usuarios/100")
                                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "testeApi@gmail.com",
                                                "123456"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(new UsuarioSenhaDto("1234567", "1234567", "1234567"))
                                .exchange()
                                .expectStatus().isEqualTo(422)
                                .expectBody(ErrorMessage.class)
                                .returnResult().getResponseBody();

                org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
                org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
        }

        @Test
        public void editarSenha_ComSenhaInvalidas_RetornarErrorMessageComStatus400() {
                ErrorMessage responseBody = testClient
                                .patch()
                                .uri("/api/v1/usuarios/100")
                                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "testeApi@gmail.com",
                                                "123456"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(new UsuarioSenhaDto("123456", "123456", "000000"))
                                .exchange()
                                .expectStatus().isEqualTo(400)
                                .expectBody(ErrorMessage.class)
                                .returnResult().getResponseBody();

                org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
                org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);

                responseBody = testClient
                                .patch()
                                .uri("/api/v1/usuarios/100")
                                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "testeApi@gmail.com",
                                                "123456"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(new UsuarioSenhaDto("000000", "123456", "123456"))
                                .exchange()
                                .expectStatus().isEqualTo(400)
                                .expectBody(ErrorMessage.class)
                                .returnResult().getResponseBody();

                org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
                org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);
        }

        @Test
        public void buscarTodosUsuarios_RetornarUsuarioComStatus200() {
                UsuarioResponseDto[] users = testClient
                                .get()
                                .uri("/api/v1/usuarios")
                                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "testeApi@gmail.com",
                                                "123456"))
                                .exchange()
                                .expectStatus().isOk()
                                .expectBody(UsuarioResponseDto[].class)
                                .returnResult().getResponseBody();

                org.assertj.core.api.Assertions.assertThat(users).isNotNull();
                org.assertj.core.api.Assertions.assertThat(users.length).isEqualTo(3);

        }

        @Test
        public void listarUsuarios_ComUsuarioSemPermissao_RetornarErrorMessageComStatus403() {
          
           ErrorMessage responseBody = testClient 
                                .get()
                                .uri("/api/v1/usuarios")
                                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "teste-Api1@gmail.com",
                                                "123456"))
                                .exchange()
                                .expectStatus().isForbidden()
                                .expectBody(ErrorMessage.class)
                                .returnResult().getResponseBody();

                org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
        }

}
