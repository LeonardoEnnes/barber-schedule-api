package com.dev.barber_schedule_api.core.domain;

import java.util.UUID;

public class Cliente {
    private final UUID id;
    private String nome;
    private String telefone;
    private String email;

    public Cliente(UUID id, String nome, String telefone, String email) {
        this.id = id != null ? id : UUID.randomUUID();
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }

    public UUID getId() { return id; }
    public String getNome() { return nome; }
}
