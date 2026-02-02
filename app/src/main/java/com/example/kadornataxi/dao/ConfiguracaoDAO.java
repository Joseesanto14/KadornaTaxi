package com.example.kadornataxi.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.kadornataxi.database.DbHelper;
import com.example.kadornataxi.model.Configuracao;

public class ConfiguracaoDAO {
    private SQLiteDatabase db;
    private final DbHelper dbHelper;
    private final String TAG = "ConfiguracaoDAO";

    public ConfiguracaoDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    private void open() {
        db = dbHelper.getWritableDatabase();
    }

    private void close() {
        dbHelper.close();
    }
    private ContentValues valuesPut(Configuracao configuracao) {
        ContentValues values = new ContentValues();

        values.put(DbHelper.Configuracao.VALOR_KM_RODADO,
                configuracao.getValorKmRodado());
        values.put(DbHelper.Configuracao.VALOR_HORA_ESPERA,
                configuracao.getValorHoraEspera());
        values.put(DbHelper.Configuracao.MOTORISTA,
                configuracao.getMotorista());
        values.put(DbHelper.Configuracao.CLASSIFICACAO_VIAGEM_SEPARADA,
                configuracao.getClassificacaoViagemSeparada());

        return values;
    }

    private long insert(Configuracao configuracao) {
        ContentValues values = valuesPut(configuracao);

        values.put(DbHelper.CONFIG_COLUMN_ID,
                1);
        values.put(DbHelper.CONFIG_COLUMN_VALOR_KM_RODADO,
                configuracao.getValorKmRodado());
        values.put(DbHelper.CONFIG_COLUMN_VALOR_HORA_ESPERA,
                configuracao.getValorHoraEspera());
        values.put(DbHelper.CONFIG_COLUMN_MOTORISTA,
                configuracao.getMotorista());

        return db.insert(DbHelper.TABLE_CONFIGURACAO,
                null, values);
    }

    private long update(Configuracao configuracao) {
        ContentValues values = valuesPut(configuracao);

        values.put(DbHelper.CONFIG_COLUMN_VALOR_KM_RODADO,
                configuracao.getValorKmRodado());
        values.put(DbHelper.CONFIG_COLUMN_VALOR_HORA_ESPERA,
                configuracao.getValorHoraEspera());
        values.put(DbHelper.CONFIG_COLUMN_MOTORISTA,
                configuracao.getMotorista());

        return db.update(
                DbHelper.TABLE_CONFIGURACAO,
                values,
                DbHelper.CONFIG_COLUMN_ID + " = ?",
                new String[]{String.valueOf(configuracao.getId())});
    }
    public void setConfiguracaoPadrao() {
        open();

        Log.d(TAG, "Configuração padrão gerada com o ID: " +
                insert(new Configuracao(
                        1, 1.99f, 20f, "Marcelo")));

        close();
    }

    public void configurar(Configuracao configuracao, Context context) {
        if (configExiste()) {
            updateConfiguracao(configuracao);
            Toast.makeText(context, "Configurações atualizadas", Toast.LENGTH_SHORT).show();
        } else {
            setConfiguracao(configuracao);
            Toast.makeText(context, "Configurações salvas", Toast.LENGTH_SHORT).show();
        }
    }

    private void setConfiguracao(Configuracao configuracao) {
        open();

        Log.d(TAG,
                "Configuração salva com o ID: " + insert(configuracao));

        close();
    }

    private void updateConfiguracao(Configuracao configuracao) {
        open();

        Log.d (TAG ,
                "Configuração atualizada, linhas alteradas: " + update(configuracao));


        close();
    }

    public Configuracao getConfiguracao() {
        open();

        Cursor cursor = db.query(
                DbHelper.TABLE_CONFIGURACAO,
                null,
                DbHelper.COLUMN_ID + " = ?",
                new String[]{"1"} ,
                null, null, null);

        Configuracao configuracao = null;

        if (cursor.moveToFirst()) {
            configuracao = cursorToConfiguracao(cursor);
        }

        cursor.close();
        close();

        return configuracao;
    }

    private Configuracao cursorToConfiguracao(Cursor cursor) {

        return new Configuracao(
                cursor.getLong(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_ID)),
                cursor.getFloat(cursor.getColumnIndexOrThrow(DbHelper.CONFIG_COLUMN_VALOR_KM_RODADO)),
                cursor.getFloat(cursor.getColumnIndexOrThrow(DbHelper.CONFIG_COLUMN_VALOR_HORA_ESPERA)),
                cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.CONFIG_COLUMN_MOTORISTA))
        );
    }

    public boolean configExiste() {
        open();
        Cursor cursor = db.query(
                DbHelper.TABLE_CONFIGURACAO,
                null, null, null, null, null, null);
        boolean existe = cursor.getCount() > 0;
        cursor.close();
        close();
        return existe;
    }
}
