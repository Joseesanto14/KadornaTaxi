package com.example.kadornataxi.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kadornataxi.database.DbHelper;
import com.example.kadornataxi.model.Viagem;

import java.util.ArrayList;
import java.util.List;

public class ViagemDAO {
    private SQLiteDatabase database;
    private DbHelper dbHelper;

    private void open() {
        database = dbHelper.getWritableDatabase();
    }

    private void close() {
        dbHelper.close();
    }

    // construtor padrão (vazio)
    public ViagemDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    // CREATE - Inserir um novo registro (viagem)

    public void inserirNoDatabase(Viagem viagem) {
        open();

        Log.d("ViagemDAO", "Viagem inserida com o ID: " + insert(viagem));

        close();
    }
    private long insert(Viagem viagem) {
        ContentValues values = new ContentValues();
        values.put(DbHelper.COLUMN_ORIGEM, viagem.getOrigem());
        values.put(DbHelper.COLUMN_DATA_ORIGEM, Viagem.formatarDataISO8601(viagem.getDataOrigem()));
        values.put(DbHelper.COLUMN_HORA_ORIGEM, viagem.getHoraOrigem());
        values.put(DbHelper.COLUMN_DESTINO, viagem.getDestino());
        values.put(DbHelper.COLUMN_JUSTIFICATIVA, viagem.getDescricao());
        values.put(DbHelper.COLUMN_VALOR_VIAGEM, viagem.getKmsRodados());
        values.put(DbHelper.COLUMN_MOTORISTA, viagem.getMotorista());
        values.put(DbHelper.COLUMN_HORA_ESPERA, viagem.getHoraEspera());
        values.put(DbHelper.COLUMN_VALOR_HORA_ESPERA, viagem.getValorHoraEspera());
        values.put(DbHelper.COLUMN_VIAGEM_SEPARADA, viagem.isViagemSeparada() ? 1 : 0);

        return database.insert(DbHelper.TABLE_VIAGEM, null, values);
    }

    // READ - Listar todos os registros (viagens)

    public List<Viagem> getAllOrdenadoPorDataDesc() {
        open();

        List<Viagem> lista = new ArrayList<>();

        Cursor cursor = database.query(DbHelper.TABLE_VIAGEM,
                null, null, null,
                null, null, DbHelper.COLUMN_DATA_ORIGEM + " DESC");

        if (cursor.moveToFirst()) {
            do {
                lista.add(cursorToViagem(cursor));
            } while (cursor.moveToNext());
        }

        cursor.close();

        close();

        return lista;
    }

    public List<Viagem> getAll() {
        open();

        List<Viagem> lista = new ArrayList<>();

        Cursor cursor = database.query(DbHelper.TABLE_VIAGEM,
                null, null, null,
                null, null, null);

        if (cursor.moveToFirst()) {
            do {
                lista.add(cursorToViagem(cursor));
            } while (cursor.moveToNext());
        }

        cursor.close();

        close();

        return lista;
    }

    // UPTADE - atualizar os resgistros (viagem)

    public int update(Viagem viagem) {
        open();
        close();
        return 0;
    }

    // DELETE - remover um registro do banco de dados

    public int delete(long id) {
        open();
        
        int linhasAfetadas = database.delete(DbHelper.TABLE_VIAGEM, DbHelper.COLUMN_ID + " = ?", new String[]{String.valueOf(id)});

        close();

        return linhasAfetadas;
    }

    // DELETAR TODOS - remover todos os registros do banco de dados

    public int deleteAll() {
        open();
        int linhasAfetadas = database.delete(DbHelper.TABLE_VIAGEM, null, null);
        close();

        return linhasAfetadas;
    }

    // CONTAR TODOS OS REGISTROS

    public int getCount() {
        return 0;
    }

    // BUSCAR UM REGISTRO USANDO OUTROS DADOS

    public Viagem getViagemById(long id) {
        open();
        close();
        return null;
    }

    public List<Viagem> getViagemByLocal(String local) {
        open();
        List<Viagem> lista = new ArrayList<>();

        Cursor cursor = database.query(DbHelper.TABLE_VIAGEM,
                null,
                DbHelper.COLUMN_ORIGEM + " = ? OR " +
                        DbHelper.COLUMN_DESTINO + " = ?",
                new String[]{local, local}, null, null, null);

        while (cursor.moveToNext()) {
            lista.add(cursorToViagem(cursor));
        }

        cursor.close();
        close();
        return lista;
    }

    public List<Viagem> getViagemByDestino(String destino) {
        open();
        List<Viagem> lista = new ArrayList<>();

        Cursor cursor = database.query(DbHelper.TABLE_VIAGEM,
                null, DbHelper.COLUMN_DESTINO +
                        " = ?", new String[]{destino}, null,
                null, null);

        while (cursor.moveToNext()) {
            lista.add(cursorToViagem(cursor));
        }

        cursor.close();
        close();
        return lista;
    }

    public List<Viagem> getViagemByPeriodo(String anoMes) {
        open();
        List<Viagem> lista = new ArrayList<>();

        Cursor cursor = database.query(
                DbHelper.TABLE_VIAGEM,
                null,
                DbHelper.COLUMN_DATA_ORIGEM + " LIKE ?",
                new String[]{anoMes + "%"},
                null, null,
                DbHelper.COLUMN_DATA_ORIGEM + " ASC");

        if (cursor.moveToFirst()) {
            do {
                lista.add(cursorToViagem(cursor));
            } while (cursor.moveToNext());
        }

        cursor.close();
        close();
        return lista;
    }

    public List<Viagem> getViagensByIntervalo(String dataInicio, String dataFim) {
        List<Viagem> lista = new ArrayList<>();

        Cursor cursor = database.query(DbHelper.TABLE_VIAGEM,
                null, DbHelper.COLUMN_DATA_ORIGEM + " BETWEEN ? AND ?",
                new String[]{dataInicio, dataFim}, null, null, null);

        while (cursor.moveToNext()) {
            lista.add(cursorToViagem(cursor));
        }

        cursor.close();

        return lista;
    }

    public List<Viagem> getViagensSeparadas(){
        List<Viagem> lista = new ArrayList<>();

        Cursor cursor = database.query(DbHelper.TABLE_VIAGEM, null,
                DbHelper.COLUMN_VIAGEM_SEPARADA + " = 1", null,
                null, null, null);

        while (cursor.moveToNext()){
            lista.add(cursorToViagem(cursor));
        }

        cursor.close();

        return lista;
        }

    private Viagem cursorToViagem(Cursor cursor) {
        Viagem viagem = new Viagem();

        viagem.setId(cursor.getLong(cursor.getColumnIndexOrThrow(DbHelper.
                COLUMN_ID)));
        viagem.setOrigem(cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.
                COLUMN_ORIGEM)));
        viagem.setDataOrigem(cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.
                COLUMN_DATA_ORIGEM)));
        viagem.setHoraOrigem(cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.
                COLUMN_HORA_ORIGEM)));
        viagem.setDestino(cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.
                COLUMN_DESTINO)));
        viagem.setDescricao(cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.
                COLUMN_JUSTIFICATIVA)));
        viagem.setKmsRodados(cursor.getInt(cursor.getColumnIndexOrThrow(DbHelper.
                COLUMN_VALOR_VIAGEM)));
        viagem.setMotorista(cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.
                COLUMN_MOTORISTA)));
        viagem.setHoraEspera(cursor.getFloat(cursor.getColumnIndexOrThrow(DbHelper.
                COLUMN_HORA_ESPERA)));
        viagem.setValorHoraEspera(cursor.getInt(cursor.getColumnIndexOrThrow(DbHelper.
                COLUMN_VALOR_HORA_ESPERA)));
        viagem.setViagemSeparada(cursor.getInt(cursor.getColumnIndexOrThrow(DbHelper.
                COLUMN_VIAGEM_SEPARADA)) == 1);

        return viagem;
    }
}
