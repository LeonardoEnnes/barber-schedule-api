package com.dev.barber_schedule_api.infrastructure.config;

import com.dev.barber_schedule_api.application.ports.out.AgendamentoRepositoryPort;
import com.dev.barber_schedule_api.application.usecases.CancelarAgendamentoUseCase;
import com.dev.barber_schedule_api.application.usecases.CriarAgendamentoUseCase;
import com.dev.barber_schedule_api.application.usecases.FinalizarAgendamentoUseCase;
import com.dev.barber_schedule_api.application.usecases.ListarAgendamentosPorPrestadorUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class UseCaseConfig {
    @Bean
    public CriarAgendamentoUseCase criarAgendamentoUseCase(AgendamentoRepositoryPort repositoryPort) {
        return new CriarAgendamentoUseCase(repositoryPort);
    }
    @Bean
    public CancelarAgendamentoUseCase cancelarAgendamentoUseCase(AgendamentoRepositoryPort agendamentoRepository) {
        return new CancelarAgendamentoUseCase(agendamentoRepository);
    }

    @Bean
    public FinalizarAgendamentoUseCase finalizarAgendamentoUseCase(AgendamentoRepositoryPort agendamentoRepository) {
        return new FinalizarAgendamentoUseCase(agendamentoRepository);
    }

    @Bean
    public ListarAgendamentosPorPrestadorUseCase listarAgendamentosPorPrestadorUseCase(AgendamentoRepositoryPort agendamentoRepository) {
        return new ListarAgendamentosPorPrestadorUseCase(agendamentoRepository);
    }
}
