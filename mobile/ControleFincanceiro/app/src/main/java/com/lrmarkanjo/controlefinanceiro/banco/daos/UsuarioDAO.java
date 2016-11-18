package com.lrmarkanjo.controlefinanceiro.banco.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lrmarkanjo.controlefinanceiro.banco.core.DBHelper;
import com.lrmarkanjo.controlefinanceiro.banco.modelos.Usuario;


/**
 * Created by lrmar on 17/11/2016.
 */

public class UsuarioDAO {

    private Context context;

    public UsuarioDAO(Context context) {
        this.context = context;
    }

    public Usuario select() {

        String selectQuery = "select usuario_id, nome, email, senha, foto from usuario ";
        SQLiteDatabase db = new DBHelper(this.context).getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        Usuario usuario = null;

        if (cursor.moveToFirst()) {
            do {

                usuario = new Usuario();
                usuario.setId(cursor.getInt(0));
                usuario.setNome(cursor.getString(1));
                usuario.setEmail(cursor.getString(2));
                usuario.setSenha(cursor.getString(3));
                usuario.setFoto(cursor.getBlob(4));

            } while (cursor.moveToNext());
        }
        db.close();

        return usuario;

    }

    public boolean gravar(Usuario usuario) {
        if (usuario.getId()==null) {
            return this.insert(usuario);
        } else {
            return this.update(usuario);
        }
    }

    public boolean insert(Usuario usuario) {

        try {

            SQLiteDatabase db = new DBHelper(this.context).getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("usuario_id", usuario.getId());
            values.put("nome", usuario.getNome());
            values.put("email", usuario.getEmail());
            values.put("senha", usuario.getSenha());
            values.put("foto", usuario.getFoto());

            db.insert("usuario", null, values);
            db.close();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    public boolean update(Usuario usuario) {

        try {

            SQLiteDatabase db = new DBHelper(this.context).getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("nome", usuario.getNome());
            values.put("email", usuario.getEmail());
            values.put("senha", usuario.getSenha());
            values.put("foto", usuario.getFoto());

            db.update("usuario", values, " usuario_id = ? ", new String[]{String.valueOf(usuario.getId())});
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

            db.delete("usuario", " usuario_id = ? ", new String[]{String.valueOf(gastoId)});
            db.close();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

}
