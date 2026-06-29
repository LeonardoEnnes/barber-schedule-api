package com.dev.barber_schedule_api.infrastructure.adapters.in.web;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

public record CriarAgendamentoRequest(
        @NotNull UUID clienteId,
        @NotNull UUID prestadorId,
        @NotNull @Future Instant dataHoraInicio,
        @NotNull @Future Instant dataHoraFim
) {}