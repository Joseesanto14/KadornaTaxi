package com.example.kadornataxi.model;

import java.io.Serializable;

public class Viagem implements Serializable {
    private String origem;
    private String dataOrigem;
    private String horaOrigem;
    private String destino;
    private String dataDestino;
    private String horaDestino;
    private String justificativa;

    public Viagem(String origem, String dataOrigem, String horaOrigem, String destino, String dataDestino, String horaDestino, String justificativa) {
        this.origem = origem;
        this.dataOrigem = dataOrigem;
        this.horaOrigem = horaOrigem;
        this.destino = destino;
        this.dataDestino = dataDestino;
        this.horaDestino = horaDestino;
        this.justificativa = justificativa;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDataOrigem() {
        return dataOrigem;
    }

    public void setDataOrigem(String dataOrigem) {
        this.dataOrigem = dataOrigem;
    }

    public String getHoraOrigem() {
        return horaOrigem;
    }

    public void setHoraOrigem(String horaOrigem) {
        this.horaOrigem = horaOrigem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getDataDestino() {
        return dataDestino;
    }

    public void setDataDestino(String dataDestino) {
        this.dataDestino = dataDestino;
    }

    public String getHoraDestino() {
        return horaDestino;
    }

    public void setHoraDestino(String horaDestino) {
        this.horaDestino = horaDestino;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }

    @Override
    public String toString() {
        return "Viagem{" +
                "origem='" + origem + '\'' +
                ", dataOrigem='" + dataOrigem + '\'' +
                ", horaOrigem='" + horaOrigem + '\'' +
                ", destino='" + destino + '\'' +
                ", dataDestino='" + dataDestino + '\'' +
                ", horaDestino='" + horaDestino + '\'' +
                ", justificativa='" + justificativa + '\'' +
                '}';
    }
}
