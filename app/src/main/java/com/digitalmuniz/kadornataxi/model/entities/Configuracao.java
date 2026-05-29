package com.digitalmuniz.kadornataxi.model.entities;

public class Configuracao {

    private long id;
    private float valorKmRodado;
    private float valorHoraEspera;
    private String motorista;
    private String classificacaoViagemSeparada;
    private String cnpj;
    private String nomeFantasia;
    private String titularCNPJ;
    private String telefone;
    private String email;

    public Configuracao(long id, float valorKmRodado, float valorHoraEspera, String motorista, String classificacaoViagemSeparada) {
        this.id = id;
        this.valorKmRodado = valorKmRodado;
        this.valorHoraEspera = valorHoraEspera;
        this.motorista = motorista;
        this.classificacaoViagemSeparada = classificacaoViagemSeparada;
    }

    public Configuracao(long id, float valorKmRodado, float valorHoraEspera, String motorista, String classificacaoViagemSeparada, String cnpj, String nomeFantasia, String titularCNPJ, String telefone, String email) {
        this.id = id;
        this.valorKmRodado = valorKmRodado;
        this.valorHoraEspera = valorHoraEspera;
        this.motorista = motorista;
        this.classificacaoViagemSeparada = classificacaoViagemSeparada;
        this.cnpj = cnpj;
        this.nomeFantasia = nomeFantasia;
        this.titularCNPJ = titularCNPJ;
        this.telefone = telefone;
        this.email = email;
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

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getTitularCNPJ() {
        return titularCNPJ;
    }

    public void setTitularCNPJ(String titularCNPJ) {
        this.titularCNPJ = titularCNPJ;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
