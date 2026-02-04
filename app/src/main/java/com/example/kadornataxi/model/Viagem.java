package com.example.kadornataxi.model;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.kadornataxi.dao.ConfiguracaoDAO;
import com.example.kadornataxi.dto.MesViagens;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Viagem implements Serializable {
    private long id;
    private String origem;
    private String data;
    private String hora;
    private String destino;
    private String descricao;
    private float kmsRodados;
    private float valorKms;
    private String motorista;
    private float horaEspera;
    private float valorHoraEspera;
    private String classificacao;
    private float valorTotal;

    // --------------- construtores ---------------

    public Viagem() {}

    public Viagem(long id, String origem, String data, String hora, String destino, String descricao, float kmsRodados, float valorKms, String motorista, float horaEspera, float valorHoraEspera, String classificacao, float valorTotal) {
        this.id = id;
        this.origem = origem;
        this.data = data;
        this.hora = hora;
        this.destino = destino;
        this.descricao = descricao;
        this.kmsRodados = kmsRodados;
        this.valorKms = valorKms;
        this.motorista = motorista;
        this.horaEspera = horaEspera;
        this.valorHoraEspera = valorHoraEspera;
        this.classificacao = classificacao;
        this.valorTotal = valorTotal;
    }

    public Viagem(EditText edOrigem, EditText edData, EditText edHora, EditText edDestino, EditText edDescricao, EditText edKmsRodados, EditText edHoraEspera, EditText edMotorista, CheckBox checkViagemSeparada, Context context) {
        origem = edOrigem.getText().toString();
        data = edData.getText().toString();
        hora = edHora.getText().toString();
        destino = edDestino.getText().toString();
        descricao = edDescricao.getText().toString();
        kmsRodados = Float.parseFloat(edKmsRodados.getText().toString());

        float horas = Float.parseFloat(edHoraEspera.getText().toString().split(":")[0]);
        float minutos = Float.parseFloat(edHoraEspera.getText().toString().split(":")[1]);
        horaEspera = horas + (minutos / 60f);

        motorista = edMotorista.getText().toString();

        Configuracao config = new ConfiguracaoDAO(context).getConfiguracao();
        valorKms = kmsRodados * config.getValorKmRodado();
        valorHoraEspera = horaEspera * config.getValorHoraEspera();
        valorTotal = getValorTotal();

        classificacao = checkViagemSeparada.isChecked() ?
                config.getClassificacaoViagemSeparada() : "Comum";
    }

    // --------------- úteis ---------------

    public static String formatarDataISO8601(String data) {
        String[] partes = data.split("/");

        return partes[2] + "-" + partes[1] + "-" + partes[0];
    }

    public String getDiaMes() {
        /*
        * Receive a Date in ISO 8601 format (YYYY-MM-DD) and return the day and month in the format DD/MM (Brazilian legibility)
         */

        String[] partes = getData().split("-");
        return partes[2] + "/" + partes[1];
    }

    public static List<MesViagens> gerarListaDeMeses(List<Viagem> todas) {
        Map<String, List<Viagem>> viagensPorMes = new LinkedHashMap<>();

        for(Viagem viagem : todas) {
            String mesAno = viagem.getData().substring(0, 7);

            if (viagem.isNaoComum()) {
                mesAno += " " + viagem.getClassificacao();
            }

            viagensPorMes
                    .computeIfAbsent(mesAno, k -> new ArrayList<>())
                    .add(viagem);
        }

        List<MesViagens> listaMeses = new ArrayList<>();

        for (String mes : viagensPorMes.keySet()) {
            listaMeses.add(new MesViagens(mes, viagensPorMes.get(mes)));
        }
        return listaMeses;
    }

    public boolean isNaoComum() {
        return !getClassificacao().equals("Comum");
    }

    // --------------- getters e setters ---------------

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public float getKmsRodados() {
        return kmsRodados;
    }

    public void setKmsRodados(float kmsRodados) {
        this.kmsRodados = kmsRodados;
    }

    public float getValorKms() {
        return valorKms;
    }

    public void setValorKms(float valorKms) {
        this.valorKms = valorKms;
    }

    public String getMotorista() {
        return motorista;
    }

    public void setMotorista(String motorista) {
        this.motorista = motorista;
    }

    public float getHoraEspera() {
        return horaEspera;
    }

    public void setHoraEspera(float horaEspera) {
        this.horaEspera = horaEspera;
    }

    public float getValorHoraEspera() {
        return valorHoraEspera;
    }

    public void setValorHoraEspera(float valorHoraEspera) {
        this.valorHoraEspera = valorHoraEspera;
    }

    public String getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(String classificacao) {
        this.classificacao = classificacao;
    }

    public float getValorTotal() {
        return getValorKms() + getValorHoraEspera();
    }

    public void setValorTotal(float valorTotal) {
        this.valorTotal = valorTotal;
    }
}


