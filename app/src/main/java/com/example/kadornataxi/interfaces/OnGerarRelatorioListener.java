package com.example.kadornataxi.interfaces;

import com.example.kadornataxi.model.Viagem;

import java.util.List;

public interface OnGerarRelatorioListener {
    void onGerarRelatorio(String mesAno, List<Viagem> viagens);

}
