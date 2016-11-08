package com.lrmarkanjo.controlefinanceiro.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lrmarkanjo.controlefinanceiro.R;
import com.lrmarkanjo.controlefinanceiro.banco.modelos.Gasto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by lrmar on 06/11/2016.
 */

public class GastoArrayAdapter extends ArrayAdapter<Gasto> {

    private Context context;
    private ArrayList<Gasto> objects;


    public GastoArrayAdapter(Context context, int resource, ArrayList<Gasto> objects) {
        super(context, resource, objects);

        this.context = context;
        this.objects = objects;

    }

    private SimpleDateFormat dfSql = new SimpleDateFormat("dd/MM/yyyy");

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Gasto gasto = objects.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.gastos_row, null);

        if (gasto.getData()!=null) {
            TextView txtItemData = (TextView) view.findViewById(R.id.txtItemData);
            txtItemData.setText(dfSql.format(gasto.getData()));
        }

        if (gasto.getGrupo()!=null) {
            TextView txtItemGrupo = (TextView) view.findViewById(R.id.txtItemGrupo);
            txtItemGrupo.setText(gasto.getGrupo().toString());
        }

        if (gasto.getValor()!=null) {
            TextView txtValor = (TextView) view.findViewById(R.id.txtItemValor);
            txtValor.setText(gasto.getValor().toString());
        }
        return view;

    }
}
