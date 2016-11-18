package com.lrmarkanjo.controlefinanceiro.banco.core;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lrmar on 06/11/2016.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 5;
    private static final String DATABASE_NAME = "controle_financeiro";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(createTableGastos);
        db.execSQL(createTableUsuario);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion<2) {
            String alterTableGastos = "alter table gasto add imagem BLOB";
            db.execSQL(alterTableGastos);
        }
        if (oldVersion<3) {
            db.execSQL(createTableUsuario);
        }
        if (oldVersion<5) {
            db.execSQL("drop table usuario");
            db.execSQL(createTableUsuario);
        }
    }

    private String createTableGastos =
        "create table gasto (" +
            " gasto_id integer primary key autoincrement, " +
            " data date, " +
            " valor decimal(15,2), " +
            " tipo text, " +
            " grupo text, " +
            " sub_grupo text, " +
            " descricao text, " +
            " imagem BLOB "+
        ")";

    private String createTableUsuario =
        "create table usuario (" +
            " usuario_id integer, " +
            " nome text, " +
            " email text, " +
            " senha text, " +
            " foto BLOB "+
        ")";
}
