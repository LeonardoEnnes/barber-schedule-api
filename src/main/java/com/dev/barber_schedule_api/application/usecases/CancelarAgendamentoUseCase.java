package com.dev.barber_schedule_api.application.usecases;

import com.dev.barber_schedule_api.application.ports.out.AgendamentoRepositoryPort;
import com.dev.barber_schedule_api.core.domain.Agendamento;
import lombok.RequiredArgsConstructor;
import java.util.UUID;

@RequiredArgsConstructor
public class CancelarAgendamentoUseCase {
    private final AgendamentoRepositoryPort agendamentoRepositoryPort;

    public void executar(UUID id) {
        Agendamento agendamento = agendamentoRepositoryPort.buscarPorId(id)
            .orElseThrow(() -> new IllegalArgumentException("Agendamento não encontrado."));

        agendamento.cancelar();

        agendamentoRepositoryPort.save(agendamento);
    }
}
