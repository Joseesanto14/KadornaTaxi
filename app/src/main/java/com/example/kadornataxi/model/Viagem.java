package com.example.kadornataxi.model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Locale;

public class Viagem implements Serializable {
    private long id;
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
    private int valorHoraEspera = 20;
    private boolean viagemSeparada;

    public Viagem() {}

    public Viagem(long id, String origem, String dataOrigem, String horaOrigem, String destino, String dataDestino, String horaDestino, String justificativa, int valorViagem, String motorista, float horaEspera, int valorHoraEspera, boolean viagemSeparada) {
        this.id = id;
        this.origem = origem;
        this.dataOrigem = dataOrigem;
        this.horaOrigem = horaOrigem;
        this.destino = destino;
        this.dataDestino = dataDestino;
        this.horaDestino = horaDestino;
        this.justificativa = justificativa;
        this.valorViagem = valorViagem;
        this.motorista = motorista;
        this.horaEspera = horaEspera;
        this.valorHoraEspera = valorHoraEspera;
        this.viagemSeparada = viagemSeparada;
    }

    public Viagem(long id, String origem, String dataOrigem, String horaOrigem, String destino,
                  String dataDestino, String horaDestino, String justificativa, boolean viagemSeparada) {
        this.id = id;
        this.origem = origem;
        this.dataOrigem = dataOrigem;
        this.horaOrigem = horaOrigem;
        this.destino = destino;
        this.dataDestino = dataDestino;
        this.horaDestino = horaDestino;
        this.justificativa = justificativa;
        this.viagemSeparada = viagemSeparada;
    }

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

    public String toStringFormatoDataBase() {
        return "Viagem{" +
                "id=" + id +
                ", origem='" + origem + '\'' +
                ", dataOrigem='" + dataOrigem + '\'' +
                ", horaOrigem='" + horaOrigem + '\'' +
                ", destino='" + destino + '\'' +
                ", dataDestino='" + dataDestino + '\'' +
                ", horaDestino='" + horaDestino + '\'' +
                ", justificativa='" + justificativa + '\'' +
                ", valorViagem=" + valorViagem +
                ", motorista='" + motorista + '\'' +
                ", horaEspera=" + horaEspera +
                ", valorHoraEspera=" + valorHoraEspera +
                ", viagemSeparada=" + viagemSeparada +
                '}';
    }

    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "| %s | %s | %s | %s | %s | %d | %s | %.2f | %s |",
                getDataOrigem(), getHoraOrigem(), getOrigem(), getDestino(), getJustificativa(),
                getValorViagem(), getMotorista(), getHoraEspera(), getHoraEspera() * 20);
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

    public String getJustificativa() {
        return justificativa;
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

    public int getValorHoraEspera() {
        return valorHoraEspera;
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

    public void setDataDestino(String dataDestino) {
        this.dataDestino = dataDestino;
    }

    public void setHoraDestino(String horaDestino) {
        this.horaDestino = horaDestino;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }

    public void setValorViagem(int valorViagem) {
        this.valorViagem = valorViagem;
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

    public void setValorHoraEspera(int valorHoraEspera) {
        this.valorHoraEspera = valorHoraEspera;
    }
}
