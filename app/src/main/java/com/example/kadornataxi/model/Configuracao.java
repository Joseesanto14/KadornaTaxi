package com.example.kadornataxi.model;

public class Configuracao {

    private long id;
    private float valorKmRodado;
    private float valorHoraEspera;
    private String motorista;

    public Configuracao(long id, float valorKmRodado, float valorHoraEspera, String motorista) {
        this.id = id;
        this.valorKmRodado = valorKmRodado;
        this.valorHoraEspera = valorHoraEspera;
        this.motorista = motorista;
    }

    public long getId() {
        return id;
    }

    public float getValorKmRodado() {
        return valorKmRodado;
    }

    public float getValorHoraEspera() {
        return valorHoraEspera;
    }

    public String getMotorista() {
        return motorista;
    }
}
