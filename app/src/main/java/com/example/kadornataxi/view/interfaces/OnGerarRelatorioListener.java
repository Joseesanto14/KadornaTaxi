package com.example.kadornataxi.view.interfaces;

import com.example.kadornataxi.model.entities.Viagem;

import java.util.List;

public interface OnGerarRelatorioListener {
    void onGerarRelatorio(String mesAno, List<Viagem> viagens);

}
