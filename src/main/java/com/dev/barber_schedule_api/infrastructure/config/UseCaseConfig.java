package com.dev.barber_schedule_api.infrastructure.config;

import com.dev.barber_schedule_api.application.ports.out.AgendamentoRepositoryPort;
import com.dev.barber_schedule_api.application.usecases.CriarAgendamentoUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {
    @Bean
    public CriarAgendamentoUseCase criarAgendamentoUseCase(AgendamentoRepositoryPort repositoryPort) {
        return new CriarAgendamentoUseCase(repositoryPort);
    }
}