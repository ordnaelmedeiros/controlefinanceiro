package com.lrmarkanjo.controlefinanceiro.banco.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lrmarkanjo.controlefinanceiro.banco.core.DBHelper;
import com.lrmarkanjo.controlefinanceiro.banco.enums.GastoGrupo;
import com.lrmarkanjo.controlefinanceiro.banco.enums.GastoGrupoCusto;
import com.lrmarkanjo.controlefinanceiro.banco.enums.TipoPagamento;
import com.lrmarkanjo.controlefinanceiro.banco.modelos.Gasto;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lrmar on 06/11/2016.
 */

public class GastoDAO {

    private Context context;

    public GastoDAO(Context context) {
        this.context = context;
    }

    private SimpleDateFormat dfSql = new SimpleDateFormat("yyyy-MM-dd");

    public List<Gasto> selectAll() {

        String selectQuery = "select gasto_id, data, valor, grupo from gasto order by data desc";
        SQLiteDatabase db = new DBHelper(this.context).getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        List<Gasto> list = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {

                Gasto gasto = new Gasto();
                gasto.setId(cursor.getInt(0));
                try {
                    gasto.setData(dfSql.parse(cursor.getString(1)));
                } catch (ParseException e) {
                    System.out.println("Erro converter data");
                }
                gasto.setValor(new BigDecimal(cursor.getString(2)));
                gasto.setGrupo(GastoGrupo.valueOf(cursor.getString(3)));
                list.add(gasto);

            } while (cursor.moveToNext());
        }
        db.close();
        return list;

    }

    public Gasto select(Integer id) {

        String selectQuery = "select gasto_id, data, valor, tipo, grupo, sub_grupo, descricao, imagem from gasto where gasto_id = " + id;
        SQLiteDatabase db = new DBHelper(this.context).getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        Gasto gasto = null;

        if (cursor.moveToFirst()) {
            do {

                gasto = new Gasto();
                gasto.setId(cursor.getInt(0));
                try {
                    gasto.setData(dfSql.parse(cursor.getString(1)));
                } catch (ParseException e) {
                    System.out.println("Erro converter data");
                }
                gasto.setValor(new BigDecimal(cursor.getString(2)));
                gasto.setTipoPagamento(TipoPagamento.valueOf(cursor.getString(3)));
                gasto.setGrupo(GastoGrupo.valueOf(cursor.getString(4)));
                gasto.setSubGrupo(GastoGrupoCusto.valueOf(cursor.getString(5)));
                gasto.setDescricao(cursor.getString(6));
                gasto.setImagem(cursor.getBlob(7));

            } while (cursor.moveToNext());
        }
        db.close();

        return gasto;

    }

    public boolean gravar(Gasto gasto) {
        if (gasto.getId()==null) {
            return this.insert(gasto);
        } else {
            return this.update(gasto);
        }
    }

    public boolean insert(Gasto gasto) {

        try {

            SQLiteDatabase db = new DBHelper(this.context).getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("data", dfSql.format(gasto.getData()));
            values.put("valor", gasto.getValor().floatValue());
            values.put("tipo", gasto.getTipoPagamento().toString());
            values.put("grupo", gasto.getGrupo().toString());
            values.put("sub_grupo", gasto.getSubGrupo().toString());
            values.put("descricao", gasto.getDescricao());
            values.put("imagem", gasto.getImagem());

            db.insert("gasto", null, values);
            db.close();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    public boolean update(Gasto gasto) {

        try {

            SQLiteDatabase db = new DBHelper(this.context).getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("gasto_id", gasto.getId());
            values.put("data", dfSql.format(gasto.getData()));
            values.put("valor", gasto.getValor().floatValue());
            values.put("tipo", gasto.getTipoPagamento().toString());
            values.put("grupo", gasto.getGrupo().toString());
            values.put("sub_grupo", gasto.getSubGrupo().toString());
            values.put("descricao", gasto.getDescricao());
            values.put("imagem", gasto.getImagem());

            db.update("gasto", values, " gasto_id = ? ", new String[]{String.valueOf(gasto.getId())});
            db.close();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    public boolean delete(Integer gastoId) {

        try {

            SQLiteDatabase db = new DBHelper(this.context).getWritableDatabase();

            db.delete("gasto", " gasto_id = ? ", new String[]{String.valueOf(gastoId)});
            db.close();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

}
