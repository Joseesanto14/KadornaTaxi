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
    private boolean viagemSeparada;

    public Viagem() {}

    public Viagem(String origem, String dataOrigem, String horaOrigem, String destino,
                  String descricao, boolean viagemSeparada) {
        this.origem = origem;
        this.dataOrigem = dataOrigem;
        this.horaOrigem = horaOrigem;
        this.destino = destino;
        this.descricao = descricao;
        this.viagemSeparada = viagemSeparada;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "| %s | %s | %s | %s | %s | %f | %s | %.2f | %s |",
                getDataOrigem(), getHoraOrigem(), getOrigem(), getDestino(), getDescricao(),
                getKmsRodados(), getMotorista(), getHoraEspera(), getHoraEspera() * 20);
    }

    public String getRecorteViagem() {
        return this.isViagemSeparada() ? this.dataOrigem.substring(3, 10) + " TRECHO 2" : this.dataOrigem.substring(3, 10);

    }

    public long getId() {
        return id;
    }

    public String getOrigem() {
        return origem;
    }

    public String getDataOrigem() {
        return dataOrigem;
    }

    public String getHoraOrigem() {
        return horaOrigem;
    }

    public String getDestino() {
        return destino;
    }

    public String getDescricao() {
        return descricao;
    }

    public float getKmsRodados() {
        return kmsRodados;
    }

    public String getMotorista() {
        return motorista;
    }

    public float getHoraEspera() {
        return horaEspera;
    }

    public float getValorHoraEspera() {
        return valorHoraEspera;
    }

    public float getValorViagem() {
        return valorViagem;
    }

    public boolean isViagemSeparada() {
        return viagemSeparada;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public void setDataOrigem(String dataOrigem) {
        this.dataOrigem = dataOrigem;
    }

    public void setHoraOrigem(String horaOrigem) {
        this.horaOrigem = horaOrigem;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setKmsRodados(float kmsRodados) {
        this.kmsRodados = kmsRodados;
    }

    public void setMotorista(String motorista) {
        this.motorista = motorista;
    }

    public void setHoraEspera(float horaEspera) {
        this.horaEspera = horaEspera;
    }

    public void setViagemSeparada(boolean viagemSeparada) {
        this.viagemSeparada = viagemSeparada;
    }
    public void setValorViagem(float valorViagem) {
        this.valorViagem = valorViagem;
    }

    public void setValorHoraEspera(float valorHoraEspera) {
        this.valorHoraEspera = valorHoraEspera;
    }

    public static float getValorTotalViagens(List<Viagem> viagens) {
        float total = 0;
        for (Viagem v : viagens) {
            total += v.getValorViagem();
        }
        return total;
    }

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

        String[] partes = dataOrigem.split("-");
        return partes[2] + "/" + partes[1];
    }
}
