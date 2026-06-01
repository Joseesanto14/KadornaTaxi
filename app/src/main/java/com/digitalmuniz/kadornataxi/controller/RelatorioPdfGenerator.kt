package com.digitalmuniz.kadornataxi.controller

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.Log
import androidx.core.content.FileProvider
import com.digitalmuniz.kadornataxi.model.entities.Configuracao
import com.digitalmuniz.kadornataxi.model.entities.Viagem
import com.digitalmuniz.kadornataxi.model.enums.ColunasPdf
import com.digitalmuniz.kadornataxi.model.enums.Meses
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

/**
 * Gerador de Relatório PDF para as viagens realizadas.
 * Segue princípios de Clean Code para organização de estilos e fluxo de desenho.
 */
class RelatorioPdfGenerator(
    private val context: Context,
    private val listaViagens: List<Viagem>,
    private val config: Configuracao,
    mesAno: String
) {

    companion object {
        private const val A4_HEIGHT = 842f
        private const val DOCUMENT_WIDTH = 770
        private const val MARGEM_HORIZONTAL = 5f
        private const val MARGEM_SUPERIOR = 20f
        private const val MARGEM_INFERIOR = 40f
        private const val ALTURA_LINHA_PADRAO = 20f
        
        private const val FILE_NAME = "relatorio_viagens"
        private const val TAG = "RelatorioPdfGenerator"
    }

    private val mesAnoExtenso = converterMesAnoParaExtenso(mesAno)
    private val centroPagina = (DOCUMENT_WIDTH / 2).toFloat()

    // Estado do documento
    private var pageCount = 1
    private lateinit var document: PdfDocument
    private lateinit var currentPage: PdfDocument.Page
    private lateinit var currentCanvas: Canvas

    // Estilos (Paints)
    private val painter = PdfPainter()

    /**
     * Ponto de entrada principal para gerar e abrir o relatório.
     */
    fun gerarRelatorio() {
        document = PdfDocument()
        pageCount = 1
        
        iniciarNovaPagina()

        var yPos = desenharCabecalho()
        yPos += ALTURA_LINHA_PADRAO

        desenharTitulosColunas(yPos)
        yPos += ALTURA_LINHA_PADRAO

        yPos = desenharListaViagens(yPos)

        yPos = garantirEspacoParaTotais(yPos)
        desenharTotais(yPos)

        document.finishPage(currentPage)
        val arquivo = salvarDocumento()
        abrirDocumento(arquivo)
    }

    // --- Funções de Desenho ---

    private fun desenharCabecalho(): Float {
        var y = MARGEM_SUPERIOR
        val style = painter.headerStyle

        // Linha 1: Titular e CNPJ
        val linha1 = StringBuilder()
        if (!config.titularCNPJ.isNullOrBlank()) {
            linha1.append(config.titularCNPJ)
        }
        if (!config.cnpj.isNullOrBlank()) {
            if (linha1.isNotEmpty()) linha1.append(" - ")
            linha1.append("CNPJ: ").append(config.cnpj)
        }
        
        if (linha1.isNotEmpty()) {
            currentCanvas.drawText(linha1.toString(), centroPagina, y, style)
            y += ALTURA_LINHA_PADRAO
        }

        // Linha 2: Nome Fantasia e Telefone
        val linha2 = StringBuilder()
        if (!config.nomeFantasia.isNullOrBlank()) {
            linha2.append(config.nomeFantasia)
        }
        if (!config.telefone.isNullOrBlank()) {
            if (linha2.isNotEmpty()) linha2.append(" - ")
            linha2.append("Tel. ").append(config.telefone)
        }

        if (linha2.isNotEmpty()) {
            currentCanvas.drawText(linha2.toString(), centroPagina, y, style)
            y += ALTURA_LINHA_PADRAO
        }

        // Linha 3: E-mail
        if (!config.email.isNullOrBlank()) {
            currentCanvas.drawText("E-mail: ${config.email}", centroPagina, y, style)
            y += ALTURA_LINHA_PADRAO
        }

        y += ALTURA_LINHA_PADRAO * 0.5f
        currentCanvas.drawText("Relação de Táxi - Mês: $mesAnoExtenso", centroPagina, y, style)
        return y
    }

    private fun desenharTitulosColunas(y: Float) {
        var x = MARGEM_HORIZONTAL
        val style = painter.columnHeaderStyle

        ColunasPdf.entries.forEach { coluna ->
            val xCentroColuna = x + (coluna.getMetadeLargura())
            currentCanvas.drawText(coluna.nome, xCentroColuna, y, style)
            x += coluna.larguraPt
        }
    }

    private fun desenharListaViagens(yInicial: Float): Float {
        var yPos = yInicial

        listaViagens.forEach { viagem ->
            val layouts = criarLayoutsParaLinha(viagem)
            val alturaNecessaria = layouts.maxOf { it.height }.toFloat()

            // Quebra de página se não couber
            if (yPos + alturaNecessaria > A4_HEIGHT - MARGEM_INFERIOR) {
                document.finishPage(currentPage)
                iniciarNovaPagina()
                yPos = desenharCabecalho() + ALTURA_LINHA_PADRAO
                desenharTitulosColunas(yPos)
                yPos += ALTURA_LINHA_PADRAO
            }

            desenharLinhaViagem(yPos, layouts)
            yPos += alturaNecessaria + (ALTURA_LINHA_PADRAO/2)
        }
        return yPos
    }

    private fun desenharLinhaViagem(y: Float, layouts: List<StaticLayout>) {
        var x = MARGEM_HORIZONTAL
        layouts.forEachIndexed { index, layout ->
            val coluna = ColunasPdf.entries[index]
            currentCanvas.save()
            currentCanvas.translate(x + coluna.getMetadeLargura(), y)
            layout.draw(currentCanvas)
            currentCanvas.restore()
            x += coluna.larguraPt
        }
    }

    private fun desenharTotais(y: Float) {
        val paint = painter.totalStyle
        val xAlinhamentoDireito = currentPage.info.pageWidth - (MARGEM_HORIZONTAL + 50f)
        var currentY = y

        val totais = calcularResumoFinanceiro()

        val formatarMoeda = { valor: Double ->
            val df = DecimalFormat("#,##0.00", DecimalFormatSymbols(Locale("pt", "BR")))
            "R$ " + df.format(valor)
        }

        val linhasTotais = listOf(
            "Número de Viagens: ${listaViagens.size}",
            "Total valor KM: ${formatarMoeda(totais.kms)}",
            "Total valor Espera: ${formatarMoeda(totais.espera)}",
            "Total valor Serviço: ${formatarMoeda(totais.servico)}",
            "TOTAL GERAL: ${formatarMoeda(totais.geral)}"
        )

        linhasTotais.forEach { texto ->
            currentCanvas.drawText(texto, xAlinhamentoDireito, currentY, paint)
            currentY += ALTURA_LINHA_PADRAO
        }
    }

    // --- Helpers de Lógica ---

    private fun iniciarNovaPagina() {
        val pageInfo = PdfDocument.PageInfo.Builder(DOCUMENT_WIDTH, A4_HEIGHT.toInt(), pageCount++).create()
        currentPage = document.startPage(pageInfo)
        currentCanvas = currentPage.canvas
    }

    private fun criarLayoutsParaLinha(viagem: Viagem): List<StaticLayout> {
        return ColunasPdf.entries.map { coluna ->
            val texto = coluna.extrairDado(viagem)
            StaticLayout.Builder.obtain(texto, 0, texto.length, painter.bodyTextPaint, coluna.larguraPt.toInt())
                .setAlignment(Layout.Alignment.ALIGN_NORMAL)
                .build()
        }
    }

    private fun garantirEspacoParaTotais(y: Float): Float {
        val alturaEstimadaTotais = ALTURA_LINHA_PADRAO * 7.5

        if ((y + alturaEstimadaTotais) > (A4_HEIGHT - MARGEM_INFERIOR)) {
            document.finishPage(currentPage)
            iniciarNovaPagina()
            return MARGEM_SUPERIOR * 2
        }
        return y + ALTURA_LINHA_PADRAO * 1.5f
    }

    private fun calcularResumoFinanceiro() = object {
        val kms = listaViagens.sumOf { it.valorKms.toDouble() }
        val espera = listaViagens.sumOf { it.valorHoraEspera.toDouble() }
        val servico = listaViagens.sumOf { it.valorServico.toDouble() }
        val geral = listaViagens.sumOf { it.valorTotal.toDouble() }
    }

    private fun converterMesAnoParaExtenso(mesAno: String): String {
        val partes = mesAno.split("-")
        val mesExtenso = Meses.buscarPorNumero(partes[1])
        val ano = partes[0]
        
        return if (partes.size > 2) {
            "$mesExtenso de $ano - ${partes[2]}"
        } else {
            "$mesExtenso de $ano"
        }
    }

    // --- Persistência e Abertura ---

    private fun salvarDocumento(): File {
        val nomeArquivo = "${FILE_NAME}_${mesAnoExtenso}.pdf"

        val mediaDir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
        val arquivo = File(mediaDir, nomeArquivo)
        
        try {
            FileOutputStream(arquivo).use { fos ->
                document.writeTo(fos)
                Log.d(TAG, "PDF salvo em: ${arquivo.absolutePath}")
            }
        } catch (e: IOException) {
            Log.e(TAG, "Erro ao salvar PDF", e)
        } finally {
            document.close()
        }
        return arquivo
    }

    private fun abrirDocumento(file: File) {
        val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, "application/pdf")
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }

        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            Log.e(TAG, "Nenhum visualizador de PDF encontrado", e)
        }
    }

    /**
     * Classe interna para gerenciar os estilos de pintura (Paints).
     */
    private class PdfPainter {
        val headerStyle = Paint().apply {
            color = Color.BLACK
            textAlign = Paint.Align.CENTER
            textSize = 10f
            isAntiAlias = true
        }

        val columnHeaderStyle = Paint().apply {
            color = Color.BLACK
            textAlign = Paint.Align.CENTER
            textSize = 9f
            isFakeBoldText = true
        }

        val bodyTextPaint = TextPaint().apply {
            color = Color.BLACK
            textAlign = Paint.Align.CENTER
            textSize = 9f
            isAntiAlias = true
        }

        val totalStyle = Paint().apply {
            color = Color.BLUE
            textAlign = Paint.Align.RIGHT
            textSize = 12f
            isFakeBoldText = true
        }
    }
}
