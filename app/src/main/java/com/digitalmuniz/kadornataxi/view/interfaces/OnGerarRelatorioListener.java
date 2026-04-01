package com.digitalmuniz.kadornataxi.view.interfaces;

import com.digitalmuniz.kadornataxi.model.entities.Viagem;

import java.util.List;

public interface OnGerarRelatorioListener {
    void onGerarRelatorio(String mesAno, List<Viagem> viagens);

}
