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
    private String dataDestino;
    private String horaDestino;
    private String justificativa;
    private float kmsRodados = 6766; //1,99 o km. Terá mascara que coloca virgula automaticamente igual maquina de cartão.
    private String motorista = "Marcelo";
    private float horaEspera = 0;
    private float valorHoraEspera = 2613;
    private float valorViagem;
    private boolean viagemSeparada;

    public Viagem() {}

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

    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "| %s | %s | %s | %s | %s | %f | %s | %.2f | %s |",
                getDataOrigem(), getHoraOrigem(), getOrigem(), getDestino(), getJustificativa(),
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

    public String getJustificativa() {
        return justificativa;
    }

    public String getDataDestino() {
        return dataDestino;
    }

    public String getHoraDestino() {
        return horaDestino;
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

    public void setDataDestino(String dataDestino) {
        this.dataDestino = dataDestino;
    }

    public void setHoraDestino(String horaDestino) {
        this.horaDestino = horaDestino;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
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
}
