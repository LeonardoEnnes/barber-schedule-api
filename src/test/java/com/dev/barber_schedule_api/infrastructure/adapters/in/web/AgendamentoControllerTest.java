package com.dev.barber_schedule_api.infrastructure.adapters.in.web;

import com.dev.barber_schedule_api.application.usecases.CancelarAgendamentoUseCase;
import com.dev.barber_schedule_api.application.usecases.CriarAgendamentoUseCase;
import com.dev.barber_schedule_api.application.usecases.FinalizarAgendamentoUseCase;
import com.dev.barber_schedule_api.application.usecases.ListarAgendamentosPorPrestadorUseCase;
import com.dev.barber_schedule_api.core.domain.Agendamento;
import com.dev.barber_schedule_api.core.domain.Cliente;
import com.dev.barber_schedule_api.core.domain.Prestador;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.UUID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AgendamentoController.class)
class AgendamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CriarAgendamentoUseCase criarAgendamentoUseCase;

    @MockBean
    private CancelarAgendamentoUseCase cancelarAgendamentoUseCase;

    @MockBean
    private FinalizarAgendamentoUseCase finalizarAgendamentoUseCase;

    @MockBean
    private ListarAgendamentosPorPrestadorUseCase listarAgendamentosUseCase;

    @Test
    @DisplayName("Deve retornar 201 Created ao enviar payload válido")
    void deveRetornar201AoCriarAgendamento() throws Exception {
        CriarAgendamentoRequest request = new CriarAgendamentoRequest(
                UUID.randomUUID(),
                UUID.randomUUID(),
                Instant.now().plusSeconds(3600),
                Instant.now().plusSeconds(7200)
        );

        Agendamento agendamentoSalvo = new Agendamento(
                new Cliente(request.clienteId(), "Cliente", "", ""),
                new Prestador(request.prestadorId(), "Barbeiro"),
                request.dataHoraInicio(),
                request.dataHoraFim()
        );

        when(criarAgendamentoUseCase.executar(any(), any(), any(), any())).thenReturn(agendamentoSalvo);

        mockMvc.perform(post("/api/v1/agendamentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("PENDENTE"));
    }

    @Test
    @DisplayName("Deve retornar 400 Bad Request se a data for no passado")
    void deveRetornar400SeDataForInvalida() throws Exception {
        CriarAgendamentoRequest request = new CriarAgendamentoRequest(
                UUID.randomUUID(),
                UUID.randomUUID(),
                Instant.now().minusSeconds(3600), // Data no passado (Falha no @Future)
                Instant.now().plusSeconds(7200)
        );

        mockMvc.perform(post("/api/v1/agendamentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.dataHoraInicio").exists());
    }
}