package com.dev.barber_schedule_api.core.domain;

import java.util.UUID;

public class Prestador {
    private final UUID id;
    private String nome;

    public Prestador(UUID id, String nome) {
        this.id = id != null ? id : UUID.randomUUID();
        this.nome = nome;
    }

    public UUID getId() { return id; }
    public String getNome() { return nome; }
}
