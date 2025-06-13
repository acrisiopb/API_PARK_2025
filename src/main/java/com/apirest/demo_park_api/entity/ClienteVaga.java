package com.apirest.demo_park_api.entity;

import jakarta.persistence.*; // Certifique-se que esta é a importação correta para JPA
import lombok.*;

// Remove this import as ManyToAny não é o que você precisa para relações diretas
// import org.hibernate.annotations.ManyToAny;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "clientes_tem_vagas")
@EntityListeners(AuditingEntityListener.class)
public class ClienteVaga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_recibo", nullable = false, unique = true, length = 15)
    private String recibo;

    @Column(name = "placa", nullable = false, length = 8)
    private String placa;

    @Column(name = "marca", nullable = false, length = 8)
    private String marca;

    @Column(name = "modelo", nullable = false, length = 45)
    private String modelo;

    @Column(name = "cor", nullable = false, length = 45)
    private String cor;

    @Column(name = "data_entrada", nullable = false)
    private LocalDateTime dataEntrada;

    @Column(name = "data_saida")
    private LocalDateTime dataSaida;

    @Column(name = "valor", columnDefinition = "decimal(7,2)")
    private BigDecimal valor;

    @Column(name = "desconto", columnDefinition = "decimal(7,2)")
    private BigDecimal desconto;

    // Relação: Muitos ClienteVaga para um Cliente (Many-to-One)
    @ManyToOne // Use @ManyToOne para um relacionamento many-to-one
    @JoinColumn(name = "id_cliente", nullable = false) // Coluna de chave estrangeira que referencia o Cliente
    private Cliente cliente;

   
    // Relação: Muitos ClienteVaga para uma Vaga (Many-to-One)
    @ManyToOne // Use @ManyToOne para um relacionamento many-to-one
    @JoinColumn(name = "id_vaga", nullable = false) // Coluna de chave estrangeira que referencia a Vaga
    private Vaga vaga;

    // Auditoria
    @CreatedDate
    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @LastModifiedDate
    @Column(name = "data_modificacao")
    private LocalDateTime dataModificacao;

    @CreatedBy
    @Column(name = "criador_por")
    private String criadorPor;

    @LastModifiedBy
    @Column(name = "modificado_por")
    private String ModificadoPor;

    @Override
    public int hashCode() {
        return Objects.hash(id); // Implementação mais concisa de hashCode com Objects.hash
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ClienteVaga other = (ClienteVaga) obj;
        return Objects.equals(id, other.id); // Implementação mais concisa de equals com Objects.equals
    }
}