package com.lrmarkanjo.controlefinanceiro;

import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.AccountPicker;
import com.lrmarkanjo.controlefinanceiro.adapters.GastoArrayAdapter;
import com.lrmarkanjo.controlefinanceiro.banco.daos.GastoDAO;
import com.lrmarkanjo.controlefinanceiro.banco.modelos.Gasto;
import com.lrmarkanjo.controlefinanceiro.rest.Sincronizar;
import com.lrmarkanjo.controlefinanceiro.rest.interfaces.IRecebeGasto;
import com.lrmarkanjo.controlefinanceiro.rest.interfaces.IRecebePing;
import com.lrmarkanjo.controlefinanceiro.views.AdicionarActivity;
import com.lrmarkanjo.controlefinanceiro.views.UsuarioActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Runnable, IRecebePing, NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, AdapterView.OnItemClickListener {

    private FloatingActionButton btnAdicionar;
    private ListView lstGastos;

    public static MainActivity instance = null;
    private static int PICK_ACCOUNT_REQUEST = 22;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.btnAdicionar = (FloatingActionButton) findViewById(R.id.fab);
        btnAdicionar.setOnClickListener(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        this.lstGastos = (ListView) findViewById(R.id.lstGastos);
        this.lstGastos.setOnItemClickListener(this);

        this.refreshGastos();

        instance = this;

        Thread validaNet = new Thread(this);
        validaNet.start();

    }

    @Override
    public void run() {
        while (true) {
            boolean conectado = isConectadoWifi();
            System.out.println("WIFI: " + conectado);
            if (conectado) {
                try {
                    new Sincronizar().ping(getApplicationContext(), this);
                } catch (Exception e) {}
            }
            try {
                Thread.sleep(10000);
            } catch (Exception e){}
        }
    }

    @Override
    public void recebePing() {
        System.out.println("Servidor: ON");
    }

    private boolean isConectadoWifi() {

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni!=null) {
            //System.out.println("Internet: " + ni.getType() + " - " + ni.getTypeName() + " - " + (ni.getState()== NetworkInfo.State.CONNECTED));
            if (ni.getType()==1 && NetworkInfo.State.CONNECTED.equals(ni.getState())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    private ArrayList<Gasto> lista = null;

    public void refreshGastos() {

        lista = (ArrayList<Gasto>) new GastoDAO(this).selectAll();
        this.lstGastos.setAdapter(new GastoArrayAdapter(this, R.layout.gastos_row, lista));
        //Snackbar.make(btnAdicionar, "Salvou com sucesso", Snackbar.LENGTH_LONG).setAction("Action", null).show();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_usuario) {

            Intent intencao = new Intent(this, UsuarioActivity.class);
            startActivity(intencao);

        } else if (id == R.id.nav_adicionar) {

            new Sincronizar().buscarGasto(getApplicationContext(), 30, new IRecebeGasto() {
                @Override
                public void result(Gasto gasto) {
                    System.out.print("sucesso: " + gasto);
                    if (gasto!=null) {
                        //new GastoDAO(getApplicationContext()).gravar(gasto);
                        refreshGastos();
                    }
                }

                @Override
                public void fail(String erro) {

                }
            });

            /*
            for (Gasto gasto : lista) {

                new Sincronizar().enviarGasto(getApplicationContext(), gasto, new IRecebeGasto() {
                    @Override
                    public void result(Gasto gasto) {
                        System.out.print("sucesso");
                    }

                    @Override
                    public void fail(String erro) {

                    }
                });
            }
*/
        }

        /*
        if (id == R.id.nav_camera) {

            Snackbar.make(this.fab, "Opção não Implementada", Snackbar.LENGTH_LONG).setAction("Action", null).show();

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        */
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.equals(this.btnAdicionar)) {
            //Snackbar.make(v, "Opção não Implementada", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            Intent intencao = new Intent(this, AdicionarActivity.class);
            startActivity(intencao);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intencao = new Intent(this, AdicionarActivity.class);
        intencao.putExtra("gasto_id", lista.get(position).getId());
        startActivity(intencao);

    }
}
