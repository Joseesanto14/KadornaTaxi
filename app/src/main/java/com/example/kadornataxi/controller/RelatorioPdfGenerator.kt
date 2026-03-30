package com.example.kadornataxi.controller

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
import com.example.kadornataxi.model.entities.Viagem
import com.example.kadornataxi.model.enums.ColunasPdf
import com.example.kadornataxi.model.enums.Meses
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.Locale

class RelatorioPdfGenerator(private val context: Context, val listaViagens: List<Viagem>, mesAno: String) {
    private val A4_H = 842
    private val A4_B = 595
    private val NOVA_LARGURA = 745
    val centroTela = (NOVA_LARGURA/2).toFloat()

    val nomePessoa = "Kadorna da Silva Santo Pereira Filho"
    val cnpj = "X0.XX0.0XX/000X-XX"
    val nomeEmpresa = "Kadorna Transportes"
    val telefone = "(XX) 99XXXXXXX"
    val email = "kad.kadorna@mocked.com"

    val mesAnoExtenso = converterMesAnoParaExtenso(mesAno)

    val MARGEM_LINHA = 5f
    val linha = 20f
    val nomeArquivo = "relatorio_teste.pdf"


    private val MARGEM_INFERIOR = 40f
    private var pageCount = 1
    private lateinit var currentPage: PdfDocument.Page
    private lateinit var currentCanvas: Canvas

    fun gerarRelatorio(mesAno: String, viagens: List<Viagem>) {
        val document = PdfDocument()
        pageCount = 1
        
        // Inicializa a primeira página
        novaPagina(document)

        var y: Float = gerarCabecalho(currentCanvas)
        y += linha

        gerarColunas(y, currentCanvas)
        y += linha

        y = gerarViagensStaticLayout(y, document)

        // Verifica se o total cabe na página atual, se não, cria uma nova
        if (y + (linha * 7) > A4_H - MARGEM_INFERIOR) {
            document.finishPage(currentPage)
            novaPagina(document)
            y = 40f
        } else {
            y += linha
        }

        desenharTotalViagens(y, currentCanvas)

        document.finishPage(currentPage)

        val arquivo = salvarDocumento(document)
        abrirDocumento(arquivo)
    }

    private fun novaPagina(document: PdfDocument) {
        val pageInfo = PdfDocument.PageInfo.Builder(NOVA_LARGURA, A4_H, pageCount++).create()
        currentPage = document.startPage(pageInfo)
        currentCanvas = currentPage.canvas
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
        var x = MARGEM_LINHA

        ColunasPdf.entries.forEach { coluna ->
            val meioColuna = x + (coluna.getMetadeLargura())
            canvas.drawText(coluna.nome, meioColuna, y, paint)
            x += coluna.larguraPt
        }
    }

    private fun gerarViagensStaticLayout(yInicial: Float, document: PdfDocument) : Float {
        val textPaint = TextPaint().apply {
            color = Color.BLACK
            textAlign = Paint.Align.CENTER
            textSize = 9f
        }

        var alturaLinha = yInicial

        listaViagens.forEach { viagem ->
            var maiorAlturaDaLinhaAtual = 0f
            val layouts = ColunasPdf.entries.map { coluna ->
                val texto = coluna.extrairDado(viagem)
                val layout = StaticLayout.Builder.obtain(texto, 0, texto.length, textPaint, coluna.larguraPt.toInt())
                    .setAlignment(Layout.Alignment.ALIGN_NORMAL)
                    .build()
                if (layout.height > maiorAlturaDaLinhaAtual) {
                    maiorAlturaDaLinhaAtual = layout.height.toFloat()
                }
                layout
            }

            // Verifica se a linha cabe na página atual
            if (alturaLinha + maiorAlturaDaLinhaAtual > A4_H - MARGEM_INFERIOR) {
                document.finishPage(currentPage)
                novaPagina(document)
                
                // Redesenha cabeçalho e colunas na nova página
                alturaLinha = gerarCabecalho(currentCanvas)
                alturaLinha += linha
                gerarColunas(alturaLinha, currentCanvas)
                alturaLinha += linha
            }

            var x = MARGEM_LINHA
            ColunasPdf.entries.forEachIndexed { index, coluna ->
                val staticLayout = layouts[index]

                currentCanvas.save()
                val meioColunaX = x + (coluna.getMetadeLargura())
                currentCanvas.translate(meioColunaX, alturaLinha)
                staticLayout.draw(currentCanvas)
                currentCanvas.restore()

                x += coluna.larguraPt
            }
            alturaLinha += maiorAlturaDaLinhaAtual + (linha/2)
        }
        return alturaLinha
    }

    private fun desenharTotalViagens(y: Float, canvas: Canvas) {
        val paint = Paint().apply {
            color = Color.BLUE
            textAlign = Paint.Align.RIGHT
            textSize = 12f
        }

        val x = currentPage.info.pageWidth - (MARGEM_LINHA + 50f)
        var altura = y;

        val somaKms = listaViagens.sumOf { it.valorKms.toDouble()}
        val somaEspera = listaViagens.sumOf { it.valorHoraEspera.toDouble() }
        val somaServico = listaViagens.sumOf { it.valorServico.toDouble() }
        val somaGeral = listaViagens.sumOf { it.valorTotal.toDouble() }

        canvas.drawText("Número de Viagens: ${listaViagens.size}", x, altura, paint)
        altura += linha

        canvas.drawText(String.format(Locale.getDefault(), "Total valor KM: R$ %.2f", somaKms), x, altura, paint)
        altura += linha

        canvas.drawText(String.format(Locale.getDefault(), "Total valor Espera: R$ %.2f", somaEspera), x, altura, paint)
        altura += linha

        canvas.drawText(String.format(Locale.getDefault(), "Total valor Serviço: R$ %.2f", somaServico), x, altura, paint)
        altura += linha

        canvas.drawText(String.format(Locale.getDefault(), "TOTAL GERAL: R$ %.2f", somaGeral), x, altura, paint)
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

    private fun abrirDocumento(file : File) {
        /**
         * Open the document as a PDF using the system's default PDF reader via intent.
         */

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
}
