package com.apirest.demo_park_api.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.ManyToAny;
import org.springframework.data.annotation.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "clientes_tem_vagas")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    //Relação - entre Cliente e Vaga   [cliente]1 -> 1*{clienteVaga}1* <- 1[Vaga] 
    @ManyToAny
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

     @ManyToAny
    @JoinColumn(name = "id_vaga", nullable = false)
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
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ClienteVaga other = (ClienteVaga) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }


    
}
