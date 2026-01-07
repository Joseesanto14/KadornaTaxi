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
    private int valorViagem = 62; //terá mascara que coloca virgula automaticamente igual maquina de cartão.
    private String motorista = "Marcelo";
    private float horaEspera = 0;


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

    public String getDataOrigem() {
        return dataOrigem;
    }

    public String getHoraOrigem() {
        return horaOrigem;
    }

    public String getDestino() {
        return destino;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public String getRecorteViagem(){
        return this.dataOrigem.substring(3,10);

    }

    public String getDataDestino() {
        return dataDestino;
    }

    public String getHoraDestino() {
        return horaDestino;
    }

    public int getValorViagem() {
        return valorViagem;
    }

    public String getMotorista() {
        return motorista;
    }

    public float getHoraEspera() {
        return horaEspera;
    }

    @Override
    public String toString() {
        return String.format("| %s | %s | %s | %s | %s | %d | %s | %.2f | %s |",
                getDataOrigem(),getHoraOrigem(),getOrigem(),getDestino(),getJustificativa(),
                getValorViagem(),getMotorista(),getHoraEspera(),getHoraEspera()*20);
    }
}
