package com.dev.barber_schedule_api.application.ports.out;

import com.dev.barber_schedule_api.core.domain.Agendamento;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AgendamentoRepositoryPort {
    Agendamento save(Agendamento agendamento);
    boolean existeConflitoDeHorario(UUID prestadorId, Instant inicio, Instant fim);
    Optional<Agendamento> buscarPorId(UUID id);
    List<Agendamento> buscarPorPrestador(UUID prestadorId, int page, int size);
}
