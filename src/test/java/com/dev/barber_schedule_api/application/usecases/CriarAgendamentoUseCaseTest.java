package com.dev.barber_schedule_api.application.usecases;

import com.dev.barber_schedule_api.application.ports.out.AgendamentoRepositoryPort;
import com.dev.barber_schedule_api.core.domain.Agendamento;
import com.dev.barber_schedule_api.core.domain.Cliente;
import com.dev.barber_schedule_api.core.domain.Prestador;
import com.dev.barber_schedule_api.core.domain.StatusAgendamento;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.Instant;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CriarAgendamentoUseCaseTest {

    @Mock
    private AgendamentoRepositoryPort repository;

    @InjectMocks
    private CriarAgendamentoUseCase useCase;

    @Test
    @DisplayName("Deve salvar um agendamento com sucesso quando o horário estiver livre")
    void deveSalvarAgendamentoComSucesso() {
        Cliente cliente = new Cliente(UUID.randomUUID(), "João", "", "");
        Prestador prestador = new Prestador(UUID.randomUUID(), "Barbeiro Zé");
        Instant inicio = Instant.now().plusSeconds(3600);
        Instant fim = inicio.plusSeconds(3600);

        when(repository.existeConflitoDeHorario(prestador.getId(), inicio, fim)).thenReturn(false);
        when(repository.save(any(Agendamento.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Agendamento resultado = useCase.executar(cliente, prestador, inicio, fim);

        assertNotNull(resultado);
        assertEquals(StatusAgendamento.PENDENTE, resultado.getStatus());
        verify(repository, times(1)).save(any(Agendamento.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando o prestador já tiver agendamento no horário")
    void deveLancarExcecaoQuandoHouverConflito() {
        Cliente cliente = new Cliente(UUID.randomUUID(), "João", "", "");
        Prestador prestador = new Prestador(UUID.randomUUID(), "Barbeiro Zé");
        Instant inicio = Instant.now().plusSeconds(3600);
        Instant fim = inicio.plusSeconds(3600);

        when(repository.existeConflitoDeHorario(prestador.getId(), inicio, fim)).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> 
            useCase.executar(cliente, prestador, inicio, fim)
        );

        assertEquals("O prestador já possui um agendamento neste horário.", exception.getMessage());
        verify(repository, never()).save(any(Agendamento.class));
    }
}