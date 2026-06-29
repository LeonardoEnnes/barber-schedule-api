package com.dev.barber_schedule_api.application.usecases;

import com.dev.barber_schedule_api.application.ports.out.AgendamentoRepositoryPort;
import com.dev.barber_schedule_api.core.domain.Agendamento;
import com.dev.barber_schedule_api.core.domain.Cliente;
import com.dev.barber_schedule_api.core.domain.Prestador;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@RequiredArgsConstructor
public class CriarAgendamentoUseCase {

    private final AgendamentoRepositoryPort agendamentoRepository;

    public Agendamento executar(Cliente cliente, Prestador prestador, Instant inicio, Instant fim) {
        if (agendamentoRepository.existeConflitoDeHorario(prestador.getId(), inicio, fim)) {
            throw new IllegalArgumentException("O prestador já possui um agendamento neste horário.");
        }
        Agendamento novoAgendamento = new Agendamento(cliente, prestador, inicio, fim);
        return agendamentoRepository.save(novoAgendamento);
    }

}
