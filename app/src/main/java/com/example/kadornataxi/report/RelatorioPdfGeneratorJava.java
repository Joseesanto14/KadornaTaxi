package com.example.kadornataxi.report;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;

import com.example.kadornataxi.model.Configuracao;
import com.example.kadornataxi.model.Viagem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

public class RelatorioPdfGeneratorJava {
    private final Context context;

    public RelatorioPdfGeneratorJava(Context context) {
        this.context = context;
    }

    public File gerarRelatorioMensal(String anoMes, List<Viagem> viagens,
                                     Configuracao configuracao) {

        PdfDocument pdfDocument = new PdfDocument();

        PdfDocument.PageInfo pageInfo =
                new PdfDocument.PageInfo.Builder(595, 842, 1).create();

        PdfDocument.Page page = pdfDocument.startPage(pageInfo);
        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        paint.setTextSize(14f);
        paint.setFakeBoldText(true);

        int y = 40;
        gerarCabecalho(canvas, paint, y, configuracao);
        y += 120;

        paint.setFakeBoldText(false);
        paint.setTextSize(12f);

        canvas.drawText("Data", 40, y, paint);
        canvas.drawText("Origem", 100, y, paint);
        canvas.drawText("Destino", 240, y, paint);
        canvas.drawText("Km", 380, y, paint);
        canvas.drawText("Valor", 440, y, paint);
        y += 20;

        for (Viagem v : viagens) {
            canvas.drawText(v.getDataOrigem(), 40, y, paint);
            canvas.drawText(v.getOrigem(), 100, y, paint);
            canvas.drawText(v.getDestino(), 240, y, paint);
            canvas.drawText(String.valueOf(v.getKmsRodados()), 380, y, paint);
            canvas.drawText(
                    String.format(Locale.getDefault(),"%.2f", Viagem.getValorTotalViagens(viagens)),
                    440, y, paint
            );
            y += 20;
        }

        y += 30;
        paint.setFakeBoldText(true);

        canvas.drawText("Resumo:", 40, y, paint);
        paint.setFakeBoldText(false);
        y += 20;

        canvas.drawText("Viagens: " + viagens.size(), 40, y, paint);

        pdfDocument.finishPage(page);

        File dir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);

        File file = new File(
                dir, "relatorio_" + anoMes + ".pdf"
        );

        try (FileOutputStream fos = new FileOutputStream(file)) {
            pdfDocument.writeTo(fos);
        } catch (IOException e) {
            e.printStackTrace();
        }

        pdfDocument.close();

        return file;
    }

    private void gerarCabecalho(Canvas canvas, Paint paint, int y, Configuracao configuracao) {
        canvas.drawText("Kadorna Transportes - Relatório Mensal", 40, y, paint);
        y += 20;

        canvas.drawText("Motorista: " + configuracao.getMotorista(), 40, y, paint);
        y += 20;

        canvas.drawText(
                "Gerado em: " + LocalDate.now().toString(),
                40, y, paint);
    }
}
