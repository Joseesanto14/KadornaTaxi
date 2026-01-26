package com.example.kadornataxi.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kadornataxi.database.DatabaseHelper;
import com.example.kadornataxi.model.Viagem;

import java.util.ArrayList;
import java.util.List;

public class ViagemDAO {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    private void open() {
        database = dbHelper.getWritableDatabase();
    }

    private void close() {
        dbHelper.close();
    }

    // construtor padrão (vazio)
    public ViagemDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // CREATE - Inserir um novo registro (viagem)

    public void inserirNoDatabase(Viagem viagem) {
        open();

        Log.d("ViagemDAO", "Viagem inserida com o ID: " + insert(viagem));

        close();
    }
    private long insert(Viagem viagem) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_ORIGEM, viagem.getOrigem());
        values.put(DatabaseHelper.COLUMN_DATA_ORIGEM, viagem.getDataOrigem());
        values.put(DatabaseHelper.COLUMN_HORA_ORIGEM, viagem.getHoraOrigem());
        values.put(DatabaseHelper.COLUMN_DESTINO, viagem.getDestino());
        values.put(DatabaseHelper.COLUMN_DATA_DESTINO, viagem.getDataDestino());
        values.put(DatabaseHelper.COLUMN_HORA_DESTINO, viagem.getHoraDestino());
        values.put(DatabaseHelper.COLUMN_JUSTIFICATIVA, viagem.getJustificativa());
        values.put(DatabaseHelper.COLUMN_VALOR_VIAGEM, viagem.getKmsRodados());
        values.put(DatabaseHelper.COLUMN_MOTORISTA, viagem.getMotorista());
        values.put(DatabaseHelper.COLUMN_HORA_ESPERA, viagem.getHoraEspera());
        values.put(DatabaseHelper.COLUMN_VALOR_HORA_ESPERA, viagem.getValorHoraEspera());
        values.put(DatabaseHelper.COLUMN_VIAGEM_SEPARADA, viagem.isViagemSeparada() ? 1 : 0);

        return database.insert(DatabaseHelper.TABLE_VIAGEM, null, values);
    }

    // READ - Listar todos os registros (viagens)

    public List<Viagem> getAllOrdenadoPorDataDesc() {
        open();

        List<Viagem> lista = new ArrayList<>();

        Cursor cursor = database.query(DatabaseHelper.TABLE_VIAGEM,
                null, null, null,
                null, null, DatabaseHelper.COLUMN_DATA_ORIGEM + " DESC");

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

        Cursor cursor = database.query(DatabaseHelper.TABLE_VIAGEM,
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
        
        int linhasAfetadas = database.delete(DatabaseHelper.TABLE_VIAGEM, DatabaseHelper.COLUMN_ID + " = ?", new String[]{String.valueOf(id)});

        close();

        return linhasAfetadas;
    }

    // DELETAR TODOS - remover todos os registros do banco de dados

    public int deleteAll() {
        open();
        int linhasAfetadas = database.delete(DatabaseHelper.TABLE_VIAGEM, null, null);
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

        Cursor cursor = database.query(DatabaseHelper.TABLE_VIAGEM,
                null,
                DatabaseHelper.COLUMN_ORIGEM + " = ? OR " +
                        DatabaseHelper.COLUMN_DESTINO + " = ?",
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

        Cursor cursor = database.query(DatabaseHelper.TABLE_VIAGEM,
                null, DatabaseHelper.COLUMN_DESTINO +
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
                DatabaseHelper.TABLE_VIAGEM,
                null,
                DatabaseHelper.COLUMN_DATA_ORIGEM + " LIKE ?",
                new String[]{anoMes + "%"},
                null, null,
                DatabaseHelper.COLUMN_DATA_ORIGEM + " ASC");

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

        Cursor cursor = database.query(DatabaseHelper.TABLE_VIAGEM,
                null, DatabaseHelper.COLUMN_DATA_ORIGEM + " BETWEEN ? AND ?",
                new String[]{dataInicio, dataFim}, null, null, null);

        while (cursor.moveToNext()) {
            lista.add(cursorToViagem(cursor));
        }

        cursor.close();

        return lista;
    }

    public List<Viagem> getViagensSeparadas(){
        List<Viagem> lista = new ArrayList<>();

        Cursor cursor = database.query(DatabaseHelper.TABLE_VIAGEM, null,
                DatabaseHelper.COLUMN_VIAGEM_SEPARADA + " = 1", null,
                null, null, null);

        while (cursor.moveToNext()){
            lista.add(cursorToViagem(cursor));
        }

        cursor.close();

        return lista;
        }

    private Viagem cursorToViagem(Cursor cursor) {
        Viagem viagem = new Viagem();

        viagem.setId(cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.
                COLUMN_ID)));
        viagem.setOrigem(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.
                COLUMN_ORIGEM)));
        viagem.setDataOrigem(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.
                COLUMN_DATA_ORIGEM)));
        viagem.setHoraOrigem(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.
                COLUMN_HORA_ORIGEM)));
        viagem.setDestino(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.
                COLUMN_DESTINO)));
        viagem.setDataDestino(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.
                COLUMN_DATA_DESTINO)));
        viagem.setHoraDestino(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.
                COLUMN_HORA_DESTINO)));
        viagem.setJustificativa(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.
                COLUMN_JUSTIFICATIVA)));
        viagem.setKmsRodados(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.
                COLUMN_VALOR_VIAGEM)));
        viagem.setMotorista(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.
                COLUMN_MOTORISTA)));
        viagem.setHoraEspera(cursor.getFloat(cursor.getColumnIndexOrThrow(DatabaseHelper.
                COLUMN_HORA_ESPERA)));
        viagem.setValorHoraEspera(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.
                COLUMN_VALOR_HORA_ESPERA)));
        viagem.setViagemSeparada(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.
                COLUMN_VIAGEM_SEPARADA)) == 1);

        return viagem;
    }
}
