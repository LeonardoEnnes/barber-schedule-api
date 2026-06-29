package com.dev.barber_schedule_api.application.usecases;
import com.dev.barber_schedule_api.application.ports.out.AgendamentoRepositoryPort;
import com.dev.barber_schedule_api.core.domain.Agendamento;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class ListarAgendamentosPorPrestadorUseCase {
    private final AgendamentoRepositoryPort agendamentoRepository;

    public List<Agendamento> executar(UUID prestadorId, int page, int size) {
        return agendamentoRepository.buscarPorPrestador(prestadorId, page, size);
    }
}
