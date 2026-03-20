package com.example.kadornataxi.model.enums

import com.example.kadornataxi.model.entities.Viagem
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
    ORIGEM ("Origem",1.63f) {
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
    KMS_RODADOS("Kms Rodados", 2.35f) {
        override fun extrairDado(viagem: Viagem): String {
            return String.format(Locale.getDefault(), "R$ %.2f", viagem.kmsRodados)
        }
    },
    VALOR_SERVICO("Valor Serviço", 2.35f) {
        override fun extrairDado(viagem: Viagem): String {
            return String.format(Locale.getDefault(), "R$ %.2f", 20f)
            //TODO: Criar o campo valor serviço, implementar no banco de dados
        }
    },
    HORAS_ESPERA("Espera (h)",1.76f) {
        override fun extrairDado(viagem: Viagem): String {
            return String.format(Locale.getDefault(), "%.2f", viagem.horaEspera)
        }
    },
    VALOR_ESPERA("Valor Espera", 1.76f) {
        override fun extrairDado(viagem: Viagem): String {
            return String.format(Locale.getDefault(), "R$ %.2f", viagem.valorHoraEspera)
        }
    },
    VALOR_TOTAL("Valor Total",2.35f) {
        override fun extrairDado(viagem: Viagem): String {
            return String.format(Locale.getDefault(), "R$ %.2f", viagem.valorTotal)
        }
    },
    MOTORISTA("Motorista",2.40f) {
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