package com.dev.barber_schedule_api.infrastructure.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.Instant;
import java.util.UUID;

public interface AgendamentoJpaRepository extends JpaRepository<AgendamentoEntity, UUID> {
    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM AgendamentoEntity a " +
            "WHERE a.prestadorId = :prestadorId AND a.status <> 'CANCELADO' " +
            "AND (a.dataHoraInicio < :fim AND a.dataHoraFim > :inicio)")
    boolean existeConflito(@Param("prestadorId") UUID prestadorId,
                           @Param("inicio") Instant inicio,
                           @Param("fim") Instant fim);
}