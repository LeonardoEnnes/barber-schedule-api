package com.dev.barber_schedule_api.infrastructure.adapters.in.web;

import com.dev.barber_schedule_api.application.usecases.CancelarAgendamentoUseCase;
import com.dev.barber_schedule_api.application.usecases.CriarAgendamentoUseCase;
import com.dev.barber_schedule_api.application.usecases.FinalizarAgendamentoUseCase;
import com.dev.barber_schedule_api.application.usecases.ListarAgendamentosPorPrestadorUseCase;
import com.dev.barber_schedule_api.core.domain.Agendamento;
import com.dev.barber_schedule_api.core.domain.Cliente;
import com.dev.barber_schedule_api.core.domain.Prestador;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/agendamentos")
@RequiredArgsConstructor
public class AgendamentoController {

    private final CriarAgendamentoUseCase criarAgendamentoUseCase;
    private final CancelarAgendamentoUseCase cancelarAgendamentoUseCase;
    private final FinalizarAgendamentoUseCase finalizarAgendamentoUseCase;
    private final ListarAgendamentosPorPrestadorUseCase listarAgendamentosUseCase;

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody @Valid CriarAgendamentoRequest request) {
        Cliente clienteMock = new Cliente(request.clienteId(), "Cliente", "", "");
        Prestador prestadorMock = new Prestador(request.prestadorId(), "Barbeiro");

        Agendamento agendamentoSalvo = criarAgendamentoUseCase.executar(
            clienteMock, prestadorMock, request.dataHoraInicio(), request.dataHoraFim()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(agendamentoSalvo);
    }

    @GetMapping("/prestadores/{prestadorId}")
    public ResponseEntity<List<Agendamento>> listarPorPrestador(
        @PathVariable UUID prestadorId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {

        List<Agendamento> agendamentos = listarAgendamentosUseCase.executar(prestadorId, page, size);
        return ResponseEntity.ok(agendamentos);
    }

    @PatchMapping("/{id}/cancelar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelar(@PathVariable UUID id) {
        cancelarAgendamentoUseCase.executar(id);
    }

    @PatchMapping("/{id}/finalizar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void finalizar(@PathVariable UUID id) {
        finalizarAgendamentoUseCase.executar(id);
    }
}
