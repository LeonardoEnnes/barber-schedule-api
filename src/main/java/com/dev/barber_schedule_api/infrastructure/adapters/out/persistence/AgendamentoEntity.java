package com.dev.barber_schedule_api.infrastructure.adapters.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "agendamentos")
@Getter
@Setter
@NoArgsConstructor
public class AgendamentoEntity {
    @Id
    private UUID id;

    @Column(name = "cliente_id", nullable = false)
    private UUID clienteId;

    @Column(name = "prestador_id", nullable = false)
    private UUID prestadorId;

    @Column(name = "data_hora_inicio", nullable = false)
    private Instant dataHoraInicio;

    @Column(name = "data_hora_fim", nullable = false)
    private Instant dataHoraFim;

    @Column(nullable = false)
    private String status;
}