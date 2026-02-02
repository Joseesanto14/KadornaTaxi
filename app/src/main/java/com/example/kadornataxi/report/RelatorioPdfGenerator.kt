package com.example.kadornataxi.report

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Environment
import com.example.kadornataxi.model.Configuracao
import com.example.kadornataxi.model.Viagem
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class RelatorioPdfGenerator(private val context: Context) {

    fun gerarRelatorioMensal(
        anoMes: String,
        viagens: List<Viagem>,
        configuracao: Configuracao
    ) : File? {

        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
        val page = pdfDocument.startPage(pageInfo)
        val canvas = page.canvas

        val paint = Paint().apply {
            textSize = 14f
            isFakeBoldText = true
        }

        var y = 40

        desenharCabecalho(canvas, paint, y)
        y += 120

        paint.isFakeBoldText = false
        paint.textSize = 12f

        val headers = listOf(
            "Data" to 40, "Hora" to 100, "Origem" to 140, "Destino" to 280,
            "Descrição" to 420, "Km Rodado" to 460, "Valor" to 500, "Motorista" to 540,
            "Hora Espera" to 580, "Valor Hora Espera" to 620
        )
        headers.forEach { (texto, x) ->
            canvas.drawText(texto, x.toFloat(), y.toFloat(), paint)
        }
        y += 20

        for (v in viagens) {
            canvas.drawText(v.diaMes, 40f, y.toFloat(), paint)
            canvas.drawText(v.hora, 100f, y.toFloat(), paint)
            canvas.drawText(v.origem, 140f, y.toFloat(), paint)
            canvas.drawText(v.destino, 280f, y.toFloat(), paint)
            canvas.drawText(v.descricao, 420f, y.toFloat(), paint)
            canvas.drawText(v.kmsRodados.toString(), 460f, y.toFloat(), paint)
            canvas.drawText(v.valorKms.toString(), 500f, y.toFloat(), paint)
            canvas.drawText(v.motorista, 540f, y.toFloat(), paint)
            canvas.drawText(v.horaEspera.toString(), 580f, y.toFloat(), paint)
            canvas.drawText(v.valorHoraEspera.toString(), 620f, y.toFloat(), paint)
            y += 20
        }

        y += 30
        paint.isFakeBoldText = true
        canvas.drawText("Resumo do mês: ", 40f, y.toFloat(), paint)

        paint.isFakeBoldText = false
        y += 20

        val totalKm = viagens.sumOf { it.kmsRodados.toDouble()}
        val totalViagens = viagens.size

        val totalFaturamento = viagens.sumOf { (it.kmsRodados * configuracao.valorKmRodado).toDouble() }

        canvas.drawText("Total Viagens: $totalViagens", 40f, y.toFloat(), paint)
        y += 20
        canvas.drawText("Km Rodados: $totalKm", 40f, y.toFloat(), paint)
        y += 20
        canvas.drawText("Faturamento: $totalFaturamento", 40f, y.toFloat(), paint)

        pdfDocument.finishPage(page)

        val dir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
        val file = File(dir, "relatorio_$anoMes.pdf")

        return try {
            FileOutputStream(file).use { fos ->
                pdfDocument.writeTo(fos)
            }
            pdfDocument.close()
            file
        } catch (e: IOException) {
            e.printStackTrace()
            pdfDocument.close()
            null
        }
    }

    private fun desenharCabecalho(canvas: Canvas, paint: Paint, y: Int) {
        canvas.drawText("Kadorna da Silva Santos de Almeida Filho - CNPJ 09.990.900/0009-09", 40f, y.toFloat(),paint)
        canvas.drawText("Kadorna Transportes - Tel. (14) 990990009 - 990999090", 40f, (y + 20).toFloat(), paint)
        canvas.drawText("E-mail: kado.transporte@hotmail.com", 40f, (y + 40).toFloat(), paint)
        canvas.drawText("Relação de Solicitação de Taxi - Mês: Janeiro 2026", 40f, (y + 80).toFloat(),paint)


    }
}