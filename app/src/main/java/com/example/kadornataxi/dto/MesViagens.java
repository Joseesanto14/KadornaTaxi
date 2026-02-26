package com.example.kadornataxi.dto;

import com.example.kadornataxi.model.Viagem;

import java.util.List;

public class MesViagens {
    private final String mesAno;
    private final List<Viagem> viagens;
    private boolean expandido;

    public MesViagens(String mesAno, List<Viagem> viagens) {
        this.mesAno = mesAno;
        this.viagens = viagens;
        this.expandido = false;
    }
    
    public String getMesAno() {
        return mesAno;
    }

    public List<Viagem> getViagens() {
        return viagens;
    }

    public boolean isExpandido() {
        return expandido;
    }

    public void setExpandido(boolean expandido) {
        this.expandido = expandido;
    }

}
