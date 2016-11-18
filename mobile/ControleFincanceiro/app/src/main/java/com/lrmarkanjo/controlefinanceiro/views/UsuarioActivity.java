package com.lrmarkanjo.controlefinanceiro.views;

import android.accounts.AccountManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.AccountPicker;
import com.lrmarkanjo.controlefinanceiro.R;
import com.lrmarkanjo.controlefinanceiro.banco.daos.UsuarioDAO;
import com.lrmarkanjo.controlefinanceiro.banco.modelos.Usuario;
import com.lrmarkanjo.controlefinanceiro.rest.Sincronizar;
import com.lrmarkanjo.controlefinanceiro.rest.interfaces.IRecebeUsuario;

public class UsuarioActivity extends AppCompatActivity implements IRecebeUsuario {

    private Usuario usuario;
    private UsuarioDAO dao;

    private TextView txtId;
    private EditText txtNome;
    private TextView txtEmail;
    private EditText txtSenha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        this.setTitle("Usuário");

        this.txtId = (TextView) findViewById(R.id.txtId);
        this.txtNome = (EditText) findViewById(R.id.txtNome);
        this.txtEmail = (TextView) findViewById(R.id.txtEmail);
        this.txtSenha = (EditText) findViewById(R.id.txtSenha);

        this.dao = new UsuarioDAO(getApplicationContext());
        this.usuario = this.dao.select();

        if (this.usuario != null) {

            this.txtId.setText(this.usuario.getId().toString());
            this.txtNome.setText(this.usuario.getNome());
            this.txtEmail.setText(this.usuario.getEmail());
            this.txtSenha.setText(this.usuario.getSenha());

        } else {

            this.usuario = new Usuario();

        }

        Intent googlePicker = AccountPicker.newChooseAccountIntent(null, null, new String[] { GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE }, true, null, null, null, null);
        startActivityForResult(googlePicker, PICK_ACCOUNT_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_ACCOUNT_REQUEST && resultCode == RESULT_OK) {
            this.txtEmail.setText(data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME));
        }
    }


    private static int PICK_ACCOUNT_REQUEST = 22;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_usuario_drawer, menu);
        return true;
    }

    private boolean novo = true;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnSalvarUsuario: {

                this.usuario.setNome(this.txtNome.getText().toString());
                this.usuario.setEmail(this.txtEmail.getText().toString());
                this.usuario.setSenha(this.txtSenha.getText().toString());

                if (this.usuario.getId()==null) {
                    novo = true;
                } else {
                    novo = false;
                }

                new Sincronizar().enviarUsuario(getApplicationContext(), this.usuario, this, novo);

                /*
                if (this.dao.gravar(this.usuario)) {
                    onBackPressed();
                }
                */
                return true;
            }

        }
        return false;
    }

    @Override
    public void result(Usuario usuario) {
        if (novo) {
            if (dao.insert(usuario)) {
                onBackPressed();
            }
        }  else {
            if (dao.update(usuario)) {
                onBackPressed();
            }
        }
    }

    @Override
    public void fail(String erro) {
        System.out.println("Erro: " + erro);
    }
}
