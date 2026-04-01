package com.digitalmuniz.kadornataxi.model.enums

import com.digitalmuniz.kadornataxi.model.entities.Viagem
import java.util.Locale

enum class ColunasPdf(nome : String, larguraCm: Float) {
    /**
     * Enum that represents the columns of the PDF report.
     */

    DIA("Dia", 1.8f) {
        override fun extrairDado(viagem: Viagem): String {
            return viagem.diaMes

        }
    },
    HORA("Hora",1.11f) {
        override fun extrairDado(viagem: Viagem): String {
            return viagem.hora
        }
    },
    ORIGEM ("Origem",2.5f) {
        override fun extrairDado(viagem: Viagem): String {
            return viagem.origem
        }
    },
    DESTINO("Destino",2.93f) {
        override fun extrairDado(viagem: Viagem): String {
            return viagem.destino
        }
    },
    DESCRICAO("Descrição",4.68f) {
        override fun extrairDado(viagem: Viagem): String {
            return viagem.descricao
        }
    },
    KMS_RODADOS("Kms Rodados", 1.8f) {
        override fun extrairDado(viagem: Viagem): String {
            return String.format(Locale.getDefault(), "%.1f km", viagem.kmsRodados)
        }
    },
    VALOR_KMS("Valor Kms", 2f) {
        override fun extrairDado(viagem: Viagem): String {
            return String.format(Locale.getDefault(), "R$ %.2f", viagem.valorKms)
        }
    },
    VALOR_SERVICO("Valor Serviço", 2.1f) {
        override fun extrairDado(viagem: Viagem): String {
            return String.format(Locale.getDefault(), "R$ %.2f", viagem.valorServico)
        }
    },
    HORAS_ESPERA("Espera (h)",1.9f) {
        override fun extrairDado(viagem: Viagem): String {
            return String.format(Locale.getDefault(), "%.2f", viagem.horaEspera)
        }
    },
    VALOR_ESPERA("Valor Espera", 1.8f) {
        override fun extrairDado(viagem: Viagem): String {
            return String.format(Locale.getDefault(), "R$ %.2f", viagem.valorHoraEspera)
        }
    },
    VALOR_TOTAL("Valor Total",2.3f) {
        override fun extrairDado(viagem: Viagem): String {
            return String.format(Locale.getDefault(), "R$ %.2f", viagem.valorTotal)
        }
    },
    MOTORISTA("Motorista",1.8f) {
        override fun extrairDado(viagem: Viagem): String {
            return  viagem.motorista
        }
    };

    val larguraPt = larguraCm * 28.35f
    val nome = nome

    fun getMetadeLargura() : Float {
        return larguraPt / 2f
    }

    abstract fun extrairDado(viagem: Viagem) : String

}