package com.example.kadornataxi.util

enum class ColunasPdf(nome : String, larguraCm: Float) {

    DIA("Dia", 1.8f),
    HORA("Hora",1.11f),
    ORIGEM ("Origem",1.63f),
    DESTINO("Destino",2.93f),
    DESCRICAO("Descrição",4.68f),
    VALOR("Valor",2.35f),
    MOTORISTA("Motorista",2.40f),
    HORAS_ESPERA("h de Espera",3.52f);

    val larguraPt: Float = larguraCm * 28.35f
    val nome = nome

    fun getMetadeLargura() : Float {
        return larguraPt / 2
    }



     
}