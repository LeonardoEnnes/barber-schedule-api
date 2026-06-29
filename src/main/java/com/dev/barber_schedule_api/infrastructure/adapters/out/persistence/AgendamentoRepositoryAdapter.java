package com.dev.barber_schedule_api.infrastructure.adapters.out.persistence;

import com.dev.barber_schedule_api.application.ports.out.AgendamentoRepositoryPort;
import com.dev.barber_schedule_api.core.domain.Agendamento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.time.Instant;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AgendamentoRepositoryAdapter implements AgendamentoRepositoryPort {

    private final AgendamentoJpaRepository jpaRepository;

    @Override
    public Agendamento save(Agendamento agendamento) {
        AgendamentoEntity entity = new AgendamentoEntity();
        entity.setId(agendamento.getId());
        entity.setClienteId(agendamento.getCliente().getId());
        entity.setPrestadorId(agendamento.getPrestador().getId());
        entity.setDataHoraInicio(agendamento.getDataHoraInicio());
        entity.setDataHoraFim(agendamento.getDataHoraFim());
        entity.setStatus(agendamento.getStatus().name());

        jpaRepository.save(entity);
        return agendamento;
    }

    @Override
    public boolean existeConflitoDeHorario(UUID prestadorId, Instant inicio, Instant fim) {
        return jpaRepository.existeConflito(prestadorId, inicio, fim);
    }
}