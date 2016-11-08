package com.lrmarkanjo.controlefinanceiro.banco.core;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lrmar on 06/11/2016.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "controle_financeiro";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTableGastos =
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

        db.execSQL(createTableGastos);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion==1 && newVersion==2) {
            String alterTableGastos = "alter table gasto add imagem BLOB";
            db.execSQL(alterTableGastos);
        }
    }
}
