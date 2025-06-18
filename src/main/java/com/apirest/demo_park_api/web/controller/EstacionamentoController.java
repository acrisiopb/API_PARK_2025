package com.apirest.demo_park_api.web.controller;

import java.net.URI;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.data.domain.Page;
import com.apirest.demo_park_api.entity.ClienteVaga;
import com.apirest.demo_park_api.repository.projection.ClienteVagaProjection;
import com.apirest.demo_park_api.service.ClienteVagaService;
import com.apirest.demo_park_api.service.EstacionamentoService;
import com.apirest.demo_park_api.web.dto.EstacionamentoCreateDto;
import com.apirest.demo_park_api.web.dto.EstacionamentoResponseDto;
import com.apirest.demo_park_api.web.dto.PageableDTO;
import com.apirest.demo_park_api.web.dto.mapper.ClienteVagaMapper;
import com.apirest.demo_park_api.web.dto.mapper.PageableMapper;
import com.apirest.demo_park_api.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import static io.swagger.v3.oas.annotations.enums.ParameterIn.PATH;
import static io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY;

@Tag(name = "Estacionamento", description = "Contém todas as operações relativas aos recursos para cadastro de veiculo. Verificação Checkin e CheckOut.")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/estacionamentos")
public class EstacionamentoController {

        private final EstacionamentoService estacionamentoService;
        private final ClienteVagaService clienteVagaService;

        @Operation(summary = "Operação de check-in", description = "Recurso para dar entrada de um veículo no estacionamento. "
                        +
                        "Requisição exige uso de um bearer token. Acesso restrito a Role='ADMIN'", security = @SecurityRequirement(name = "security"), responses = {
                                        @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso", headers = @Header(name = HttpHeaders.LOCATION, description = "URL de acesso ao recurso criado"), content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = EstacionamentoResponseDto.class))),
                                        @ApiResponse(responseCode = "404", description = "Causas possiveis: <br/>" +
                                                        "- CPF do cliente não cadastrado no sistema; <br/>" +
                                                        "- Nenhuma vaga livre foi localizada;", content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                                        @ApiResponse(responseCode = "422", description = "Recurso não processado por falta de dados ou dados inválidos", content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                                        @ApiResponse(responseCode = "403", description = "Recurso não permito ao perfil de CLIENTE", content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class)))
                        })
        @PostMapping("/check-in")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<EstacionamentoResponseDto> checkin(@RequestBody @Valid EstacionamentoCreateDto dto) {
                ClienteVaga clienteVaga = ClienteVagaMapper.toClienteVaga(dto);
                estacionamentoService.checkIn(clienteVaga);
                EstacionamentoResponseDto responseDto = ClienteVagaMapper.toDto(clienteVaga);
                URI location = ServletUriComponentsBuilder
                                .fromCurrentRequestUri().path("/{recibo}")
                                .buildAndExpand(clienteVaga.getRecibo())
                                .toUri();
                return ResponseEntity.created(location).body(responseDto);
        }

        @Operation(summary = "Localizar um veículo estacionado", description = "Recurso para retornar um veículo estacionado "
                        +
                        "pelo nº do recibo. Requisição exige uso de um bearer token.", security = @SecurityRequirement(name = "security"), parameters = {
                                        @Parameter(in = PATH, name = "recibo", description = "Número do rebibo gerado pelo check-in")
                        }, responses = {
                                        @ApiResponse(responseCode = "200", description = "Recurso localizado com sucesso", content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = EstacionamentoResponseDto.class))),
                                        @ApiResponse(responseCode = "404", description = "Número do recibo não encontrado.", content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class)))
                        })
        @GetMapping("/check-in/{recibo}")
        @PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE')")
        public ResponseEntity<EstacionamentoResponseDto> getByRecibo(@PathVariable String recibo) {
                ClienteVaga clienteVaga = clienteVagaService.buscarPorRecibo(recibo);
                EstacionamentoResponseDto dto = ClienteVagaMapper.toDto(clienteVaga);
                return ResponseEntity.ok(dto);
        }

        @Operation(summary = "Operação de check-out", description = "Recurso para dar saída de um veículo do estacionamento. "
                        +
                        "Requisição exige uso de um bearer token. Acesso restrito a Role='ADMIN'", security = @SecurityRequirement(name = "security"), parameters = {
                                        @Parameter(in = PATH, name = "recibo", description = "Número do rebibo gerado pelo check-in", required = true)
                        }, responses = {
                                        @ApiResponse(responseCode = "200", description = "Recurso atualzado com sucesso", content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = EstacionamentoResponseDto.class))),
                                        @ApiResponse(responseCode = "404", description = "Número do recibo inexistente ou "
                                                        +
                                                        "o veículo já passou pelo check-out.", content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                                        @ApiResponse(responseCode = "403", description = "Recurso não permito ao perfil de CLIENTE", content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class)))
                        })
        @PutMapping("/check-out/{recibo}")
        @PreAuthorize("hasAnyRole('ADMIN')")
        public ResponseEntity<EstacionamentoResponseDto> checkout(@PathVariable String recibo) {
                ClienteVaga clienteVaga = estacionamentoService.checkOut(recibo);
                EstacionamentoResponseDto dto = ClienteVagaMapper.toDto(clienteVaga);
                return ResponseEntity.ok(dto);
        }

        @Operation(summary = "Localizar os registros do estacionamentos do cliente por CPF.", description = "Localizar os registros do estacionamentos do cliente por CPF. Requisição exige uso de um bearer token. ", security = @SecurityRequirement(name = "security"), parameters = {

                        @Parameter(in = PATH, name = "cpf", description = "Número do CPF referente ao cliente a ser consultado.", required = true),

                        @Parameter(in = QUERY, name = "page", description = "Representa a página retornada.", 
                        content = @Content(schema = @Schema(type = "integer", defaultValue = "0"))),

                        @Parameter(in = QUERY, name = "size", description = "Representa o total de elemento por página.", 
                        content = @Content(schema = @Schema(type = "integer", defaultValue = "5"))),

                        @Parameter(in = QUERY, name = "sort", description = "Campo padrao de ordenação 'dataEntrada,asc'.", 
                        array = @ArraySchema(schema = @Schema(type = "String", defaultValue = "dataEntrada,asc")), hidden = true)

        }, responses = {
                        @ApiResponse(responseCode = "200", description = "Recurso localizado com sucesso", content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = EstacionamentoResponseDto.class))),
                        @ApiResponse(responseCode = "403", description = "Recurso não permito ao perfil de CLIENTE", content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class)))
        })
        @GetMapping("/cpf/{cpf}")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<PageableDTO> getAllEstacionamentosPorCpf(@PathVariable String cpf,
                        @Parameter(hidden = true) @PageableDefault(size = 5, sort = "dataEntrada", direction = Sort.Direction.ASC) Pageable pageable) {
                Page<ClienteVagaProjection> projection = clienteVagaService.buscarTodosPorClienteCpf(cpf, pageable);
                PageableDTO dto = PageableMapper.toDto(projection);
                return ResponseEntity.ok(dto);
        }

        @GetMapping
        @PreAuthorize("hasRole('CLIENTE')")
        public ResponseEntity<PageableDTO> getAllEstacionamentosdoCliente(@PathVariable String cpf,
                        @Parameter(hidden = true) @PageableDefault(size = 5, sort = "dataEntrada", direction = Sort.Direction.ASC) Pageable pageable) {
                Page<ClienteVagaProjection> projection = clienteVagaService.buscarTodosPorClienteCpf(cpf, pageable);
                PageableDTO dto = PageableMapper.toDto(projection);
                return ResponseEntity.ok(dto);
        }

}