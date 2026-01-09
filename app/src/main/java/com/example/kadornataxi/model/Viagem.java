package com.example.kadornataxi.model;

import java.io.Serializable;

public class Viagem implements Serializable {
    private final String origem;
    private final String dataOrigem;
    private final String horaOrigem;
    private final String destino;
    private final String dataDestino;
    private final String horaDestino;
    private final String justificativa;
    private final int valorViagem = 62; //terá mascara que coloca virgula automaticamente igual maquina de cartão.
    private final String motorista = "Marcelo";
    private final float horaEspera = 0;
    private final boolean viagemSeparada;


    public Viagem(String origem, String dataOrigem, String horaOrigem, String destino,
                  String dataDestino, String horaDestino, String justificativa, boolean viagemSeparada) {
        this.origem = origem;
        this.dataOrigem = dataOrigem;
        this.horaOrigem = horaOrigem;
        this.destino = destino;
        this.dataDestino = dataDestino;
        this.horaDestino = horaDestino;
        this.justificativa = justificativa;
        this.viagemSeparada = viagemSeparada;
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
        String recorte = this.isViagemSeparada() ? this.dataOrigem.substring(3,10) + " TRECHO 2" : this.dataOrigem.substring(3,10);
        return recorte;

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

    public boolean isViagemSeparada() {
        return viagemSeparada;
    }

    @Override
    public String toString() {
        return String.format("| %s | %s | %s | %s | %s | %d | %s | %.2f | %s |",
                getDataOrigem(),getHoraOrigem(),getOrigem(),getDestino(),getJustificativa(),
                getValorViagem(),getMotorista(),getHoraEspera(),getHoraEspera()*20);
    }
}
