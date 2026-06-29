package com.dev.barber_schedule_api.infrastructure.adapters.out.persistence;

import com.dev.barber_schedule_api.application.ports.out.AgendamentoRepositoryPort;
import com.dev.barber_schedule_api.core.domain.Agendamento;
import com.dev.barber_schedule_api.core.domain.Cliente;
import com.dev.barber_schedule_api.core.domain.Prestador;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
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

    @Override
    public Optional<Agendamento> buscarPorId(UUID id) {
        return jpaRepository.findById(id)
            .map(this::toDomain);
    }

    @Override
    public List<Agendamento> buscarPorPrestador(UUID prestadorId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        return jpaRepository.findByPrestadorId(prestadorId, pageRequest)
            .stream()
            .map(this::toDomain)
            .toList();
    }

    private Agendamento toDomain(AgendamentoEntity entity) {
        Cliente cliente = new Cliente(entity.getClienteId(), "Cliente", "", "");
        Prestador prestador = new Prestador(entity.getPrestadorId(), "Barbeiro");

        Agendamento agendamento = new Agendamento(cliente, prestador, entity.getDataHoraInicio(), entity.getDataHoraFim());

        return agendamento;
    }
}
