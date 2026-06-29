CREATE TABLE agendamentos (
    id UUID PRIMARY KEY,
    cliente_id UUID NOT NULL,
    prestador_id UUID NOT NULL,
    data_hora_inicio TIMESTAMP WITH TIME ZONE NOT NULL,
    data_hora_fim TIMESTAMP WITH TIME ZONE NOT NULL,
    status VARCHAR(50) NOT NULL
);

CREATE INDEX idx_agendamentos_prestador_data ON agendamentos(prestador_id, data_hora_inicio, data_hora_fim);