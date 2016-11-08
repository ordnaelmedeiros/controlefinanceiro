package com.lrmarkanjo.controlefinanceiro;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.lrmarkanjo.controlefinanceiro.adapters.GastoArrayAdapter;
import com.lrmarkanjo.controlefinanceiro.banco.daos.GastoDAO;
import com.lrmarkanjo.controlefinanceiro.banco.modelos.Gasto;
import com.lrmarkanjo.controlefinanceiro.views.AdicionarActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, AdapterView.OnItemClickListener {

    private FloatingActionButton btnAdicionar;
    private ListView lstGastos;

    public static MainActivity instance = null;



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

    }

    private ArrayList<Gasto> lista = null;

    public void refreshGastos() {

        lista = (ArrayList<Gasto>) new GastoDAO(this).selectAll();
        this.lstGastos.setAdapter(new GastoArrayAdapter(this, R.layout.gastos_row, lista));

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

        if (id == R.id.nav_adicionar) {

            Snackbar.make(this.btnAdicionar, "Opção não Implementada", Snackbar.LENGTH_LONG).setAction("Action", null).show();

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
