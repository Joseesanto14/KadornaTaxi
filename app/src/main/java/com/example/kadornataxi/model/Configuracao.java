package com.example.kadornataxi.model;

public class Configuracao {

    private long id;
    private float valorKmRodado;
    private float valorHoraEspera;
    private String motorista;
    private String classificacaoViagemSeparada;

    public Configuracao(long id, float valorKmRodado, float valorHoraEspera, String motorista, String classificacaoViagemSeparada) {
        this.id = id;
        this.valorKmRodado = valorKmRodado;
        this.valorHoraEspera = valorHoraEspera;
        this.motorista = motorista;
        this.classificacaoViagemSeparada = classificacaoViagemSeparada;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getValorKmRodado() {
        return valorKmRodado;
    }

    public void setValorKmRodado(float valorKmRodado) {
        this.valorKmRodado = valorKmRodado;
    }

    public float getValorHoraEspera() {
        return valorHoraEspera;
    }

    public void setValorHoraEspera(float valorHoraEspera) {
        this.valorHoraEspera = valorHoraEspera;
    }

    public String getMotorista() {
        return motorista;
    }

    public void setMotorista(String motorista) {
        this.motorista = motorista;
    }

    public String getClassificacaoViagemSeparada() {
        return classificacaoViagemSeparada;
    }

    public void setClassificacaoViagemSeparada(String classificacaoViagemSeparada) {
        this.classificacaoViagemSeparada = classificacaoViagemSeparada;
    }
}
