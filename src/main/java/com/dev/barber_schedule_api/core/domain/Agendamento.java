package com.dev.barber_schedule_api.core.domain;

import java.time.Instant;
import java.util.UUID;

public class Agendamento {
    private final UUID id;
    private final Cliente cliente;
    private final Prestador prestador;
    private Instant dataHoraInicio;
    private Instant dataHoraFim;
    private StatusAgendamento status;

    public Agendamento(Cliente cliente, Prestador prestador, Instant dataHoraInicio, Instant dataHoraFim) {
        validaPeriodo(dataHoraInicio, dataHoraFim);
        this.id = UUID.randomUUID();
        this.cliente = cliente;
        this.prestador = prestador;
        this.dataHoraInicio = dataHoraInicio;
        this.dataHoraFim = dataHoraFim;
        this.status = StatusAgendamento.PENDENTE;
    }

    private void validaPeriodo(Instant inicio, Instant fim) {
        if (inicio.isAfter(fim) || inicio.equals(fim)) {
            throw new IllegalArgumentException("A data de início deve ser anterior à data de fim.");
        }
    }

    public void cancelar() {
        if (this.status == StatusAgendamento.FINALIZADO) {
            throw new IllegalStateException("Não é possível cancelar um agendamento já finalizado.");
        }
        this.status = StatusAgendamento.CANCELADO;
    }

    public void finalizar() {
        if (this.status == StatusAgendamento.CANCELADO) {
            throw new IllegalStateException("Não é possível finalizar um agendamento que foi cancelado.");
        }
        this.status = StatusAgendamento.FINALIZADO;
    }

    public UUID getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public Prestador getPrestador() { return prestador; }
    public Instant getDataHoraInicio() { return dataHoraInicio; }
    public Instant getDataHoraFim() { return dataHoraFim; }
    public StatusAgendamento getStatus() { return status; }
}
