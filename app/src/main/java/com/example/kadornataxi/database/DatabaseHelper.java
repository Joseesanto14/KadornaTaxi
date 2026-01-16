package com.example.kadornataxi.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "kadorna_taxi.db";
    private static final int DATABASE_VERSION = 1;
    // Tabela Viagem
    public static final String TABLE_VIAGEM = "viagem";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ORIGEM = "origem";
    public static final String COLUMN_DATA_ORIGEM = "data_origem";
    public static final String COLUMN_HORA_ORIGEM = "hora_origem";
    public static final String COLUMN_DESTINO = "destino";
    public static final String COLUMN_DATA_DESTINO = "data_destino";
    public static final String COLUMN_HORA_DESTINO = "hora_destino";
    public static final String COLUMN_JUSTIFICATIVA = "justificativa";
    public static final String COLUMN_VALOR_VIAGEM = "valor_viagem";
    public static final String COLUMN_MOTORISTA = "motorista";
    public static final String COLUMN_HORA_ESPERA = "hora_espera";
    public static final String COLUMN_VALOR_HORA_ESPERA = "valor_hora_espera";
    public static final String COLUMN_VIAGEM_SEPARADA = "viagem_separada";

    public static final String CREATE_TABLE_VIAGEM = "CREATE TABLE " + TABLE_VIAGEM + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_ORIGEM + " TEXT, " +
            COLUMN_DATA_ORIGEM + " TEXT, " +
            COLUMN_HORA_ORIGEM + " TEXT, " +
            COLUMN_DESTINO + " TEXT, " +
            COLUMN_DATA_DESTINO + " TEXT, " +
            COLUMN_HORA_DESTINO + " TEXT, " +
            COLUMN_JUSTIFICATIVA + " TEXT, " +
            COLUMN_VALOR_VIAGEM + " INTEGER, " +
            COLUMN_MOTORISTA + " TEXT, " +
            COLUMN_HORA_ESPERA + " REAL, " +
            COLUMN_VALOR_HORA_ESPERA + " INTEGER, " +
            COLUMN_VIAGEM_SEPARADA + " INTEGER" +
            ");";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_VIAGEM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
