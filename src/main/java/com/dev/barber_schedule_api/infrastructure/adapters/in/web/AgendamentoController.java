package com.dev.barber_schedule_api.infrastructure.adapters.in.web;

import com.dev.barber_schedule_api.application.usecases.CriarAgendamentoUseCase;
import com.dev.barber_schedule_api.core.domain.Agendamento;
import com.dev.barber_schedule_api.core.domain.Cliente;
import com.dev.barber_schedule_api.core.domain.Prestador;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/agendamentos")
@RequiredArgsConstructor
public class AgendamentoController {

    private final CriarAgendamentoUseCase criarAgendamentoUseCase;

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody @Valid CriarAgendamentoRequest request) {
        Cliente clienteMock = new Cliente(request.clienteId(), "Cliente", "", "");
        Prestador prestadorMock = new Prestador(request.prestadorId(), "Barbeiro");

        Agendamento agendamentoSalvo = criarAgendamentoUseCase.executar(
                clienteMock, prestadorMock, request.dataHoraInicio(), request.dataHoraFim()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(agendamentoSalvo);
    }
}