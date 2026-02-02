package com.example.kadornataxi.model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

public class Viagem implements Serializable {
    private long id;
    private String origem;
    private String dataOrigem;
    private String horaOrigem;
    private String destino;
    private String descricao;
    private float kmsRodados;
    private float valorViagem;
    private String motorista;
    private float horaEspera;
    private float valorHoraEspera;
    private String classificacao;
    private float valorTotal;

    public Viagem() {}

    public static String formatarDataISO8601(String data) {
        String[] partes = data.split("/");
        String dia = partes[0];
        String mes = partes[1];
        String ano = partes[2];

        return ano + "-" + mes + "-" + dia;
    }

    public String getDiaMes() {
        /*
        * Receive a Date in ISO 8601 format (YYYY-MM-DD) and return the day and month in the format DD/MM (Brazilian legibility)
         */

        String[] partes = getDataOrigem().split("-");
        return partes[2] + "/" + partes[1];
    }
}


