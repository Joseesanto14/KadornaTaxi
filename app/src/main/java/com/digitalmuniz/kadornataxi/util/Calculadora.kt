package com.digitalmuniz.kadornataxi.util

import android.content.Context
import com.digitalmuniz.kadornataxi.data.dao.ConfiguracaoDAO
import java.util.Locale

class Calculadora (private val context: Context){ //TODO: planned class for refactor and remove calc logic from UI

    fun calcularValorKm(kmStr : String) : Float {
        if (kmStr.isEmpty()) {
            return 0f
        }
        try {
            val km = kmStr.replace(",", ".").toFloat()
            return km * (ConfiguracaoDAO(context).getConfiguracao().valorKmRodado)
        } catch (_: NumberFormatException) {
            return 0f
        }
    }

    fun formatarValorKm (valor : Float) : String {
        return String.format(Locale.getDefault(), "%.2f", valor)
    }
}