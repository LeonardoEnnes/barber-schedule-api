package com.dev.barber_schedule_api.infrastructure.adapters.out.persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
class AgendamentoJpaRepositoryTest {

    @Autowired
    private AgendamentoJpaRepository jpaRepository;

    @Test
    @DisplayName("Deve retornar true se houver conflito de horário para o prestador")
    void deveDetectarConflitoDeHorario() {
        UUID prestadorId = UUID.randomUUID();
        Instant inicio = Instant.now().plusSeconds(3600);
        Instant fim = inicio.plusSeconds(7200);

        AgendamentoEntity entity = new AgendamentoEntity();
        entity.setId(UUID.randomUUID());
        entity.setClienteId(UUID.randomUUID());
        entity.setPrestadorId(prestadorId);
        entity.setDataHoraInicio(inicio);
        entity.setDataHoraFim(fim);
        entity.setStatus("PENDENTE");

        jpaRepository.save(entity);

        boolean existeConflito = jpaRepository.existeConflito(prestadorId, inicio, fim);

        assertTrue(existeConflito);
    }

    @Test
    @DisplayName("Não deve conflitar com agendamentos CANCELADOS")
    void naoDeveConflitarComAgendamentosCancelados() {
        UUID prestadorId = UUID.randomUUID();
        Instant inicio = Instant.now().plusSeconds(3600);
        Instant fim = inicio.plusSeconds(7200);

        AgendamentoEntity entity = new AgendamentoEntity();
        entity.setId(UUID.randomUUID());
        entity.setClienteId(UUID.randomUUID());
        entity.setPrestadorId(prestadorId);
        entity.setDataHoraInicio(inicio);
        entity.setDataHoraFim(fim);
        entity.setStatus("CANCELADO");

        jpaRepository.save(entity);

        boolean existeConflito = jpaRepository.existeConflito(prestadorId, inicio, fim);

        assertFalse(existeConflito);
    }
}