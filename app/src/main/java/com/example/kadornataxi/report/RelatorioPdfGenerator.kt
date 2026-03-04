package com.example.kadornataxi.report

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.util.Log
import androidx.core.content.FileProvider
import com.example.kadornataxi.dao.ViagemDAO
import com.example.kadornataxi.model.Viagem
import com.example.kadornataxi.util.ColunasPdf
import com.example.kadornataxi.util.Meses
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.Locale

class RelatorioPdfGenerator(private val context: Context, val mesAno: String, val viagens : List<Viagem>) {
    private val A4_H = 842
    private val A4_B = 595

    val nomePessoa = "Kadorna da Silva Santo Pereira Filho"
    val cnpj = "90.250.089/0001-99"
    val nomeEmpresa = "Kadorna Transportes"
    val telefone = "(10) 991541407"
    val email = "kad.kadorna@hotmail.com"

    val mesAnoExtenso = converterMesAnoParaExtenso(mesAno)


    val linha = 20f
    val nomeArquivo = "relatorio_teste.pdf"


    fun test() {
        val document = PdfDocument()
        val pageInfo: PdfDocument.PageInfo = PdfDocument.PageInfo.Builder(A4_B, A4_H, 1).create()
        val page = document.startPage(pageInfo)
        val canvas = page.canvas
        Paint().apply {
            color = Color.BLACK
            textAlign = Paint.Align.CENTER
            textSize = 9f
        }

        var y : Float = gerarCabecalho(canvas)
        y += linha

        gerarColunas(y, canvas)
        y += linha

        gerarViagens(y, canvas)

        document.finishPage(page)

        val arquivo = salvarDocumento(document)

        abrirPdf(arquivo)
    }

    private fun converterMesAnoParaExtenso(mesAno: String): String {
        val mesExtenso = Meses.buscarPorNumero(mesAno)
        val partes = mesAno.split("-")
        val ano = partes[0]
        if (partes.size > 2) {
            val cliente = partes[2]
            return "$mesExtenso de $ano - $cliente"
        } else {
            return "$mesExtenso de $ano"
        }
    }

    private fun gerarCabecalho(canvas : Canvas) : Float{
        val paint = Paint().apply {
            color = Color.BLACK
            textAlign = Paint.Align.CENTER
            textSize = 10f
        }
        val centroTela = (A4_B/2).toFloat()

        var y = 20f

        canvas.drawText("$nomePessoa - CNPJ $cnpj", centroTela, y, paint)
        y += linha

        canvas.drawText("$nomeEmpresa - Tel. $telefone", centroTela, y, paint)
        y += linha

        canvas.drawText("E-mail: $email", centroTela, y, paint)
        y += linha * 1.5f

        canvas.drawText("Relação de Táxi - Mês: $mesAnoExtenso", centroTela, y, paint)

        return y
    }

    private fun gerarColunas (y : Float, canvas : Canvas) {
        val paint = Paint().apply {
            color = Color.BLACK
            textAlign = Paint.Align.CENTER
            textSize = 9f
        }
        var x = 5f

        ColunasPdf.entries.forEach { coluna ->
            val meioColuna = x + (coluna.larguraPt/2f)
            canvas.drawText(coluna.nome, meioColuna, y, paint)
            x += coluna.larguraPt
        }
    }

    private fun gerarViagens(y : Float, canvas : Canvas) {
        val paint = Paint().apply {
            color = Color.BLACK
            textAlign = Paint.Align.CENTER
            textSize = 9f
        }

        var alturaLinha = y

        viagens.forEach { viagem ->
            var x = 5f
            ColunasPdf.entries.forEach { coluna ->
                val meioColuna = x + (coluna.larguraPt/2f)

                when (coluna.nome) {
                    "Dia" -> canvas.drawText(viagem.getDiaMes(), meioColuna, alturaLinha, paint)
                    "Hora" -> canvas.drawText(viagem.hora, meioColuna, alturaLinha, paint)
                    "Origem" -> canvas.drawText(viagem.origem, meioColuna, alturaLinha, paint)
                    "Destino" -> canvas.drawText(viagem.destino, meioColuna, alturaLinha, paint)
                    "Descrição" -> canvas.drawText(viagem.descricao, meioColuna, alturaLinha, paint)
                    "Valor" -> canvas.drawText(String.format(Locale.getDefault(), "R$ %.2f", viagem.valorKms), meioColuna, alturaLinha, paint)
                    "Motorista" -> canvas.drawText(viagem.motorista, meioColuna, alturaLinha, paint)
                    "h de Espera" -> {
                        val primeiraMetadeCentro = x + (coluna.larguraPt * 0.25f)
                        val segundaMetadeCentro = x + (coluna.larguraPt * 0.75f)
                        canvas.drawText(String.format(Locale.getDefault(), "%.2f",viagem.horaEspera), primeiraMetadeCentro, alturaLinha, paint)
                        canvas.drawText(String.format(Locale.getDefault(),"R$ %.2f", viagem.valorHoraEspera), segundaMetadeCentro, alturaLinha, paint)
                    }
                }
                x += coluna.larguraPt
            }
            alturaLinha += linha
        }
    }

    private fun salvarDocumento(document : PdfDocument) : File{
        val mediaDir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
        val arquivoFinal = File(mediaDir, nomeArquivo)
        try {
            val fos = FileOutputStream(arquivoFinal)
            document.writeTo(fos)
            Log.d("PDF Teste", "Salvo em ${arquivoFinal.absolutePath}")
        } catch (e : IOException) {
            e.printStackTrace()
        } finally {
            document.close()
        }
        return arquivoFinal
    }

    private fun abrirPdf(file : File) {
        val uri = FileProvider.getUriForFile(
            context,
            context.applicationContext.packageName + ".provider",
            file
        )

        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(uri, "application/pdf")

        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_CLEAR_TOP

        try {
            context.startActivity(intent)
        } catch (e : Exception) {
            e.printStackTrace()
        }
    }

    fun gerarRelatorioMensal(mesAno: String, viagensDoMes: List<Viagem>) {}
}
