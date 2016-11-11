package com.lrmarkanjo.controlefinanceiro.views;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.lrmarkanjo.controlefinanceiro.MainActivity;
import com.lrmarkanjo.controlefinanceiro.R;
import com.lrmarkanjo.controlefinanceiro.banco.daos.GastoDAO;
import com.lrmarkanjo.controlefinanceiro.banco.enums.GastoGrupo;
import com.lrmarkanjo.controlefinanceiro.banco.enums.GastoGrupoCusto;
import com.lrmarkanjo.controlefinanceiro.banco.enums.TipoPagamento;
import com.lrmarkanjo.controlefinanceiro.banco.modelos.Gasto;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AdicionarActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Button btnData;
    private EditText txtValor;
    private Spinner cbxGastoGrupo;
    private Spinner cbxGastoSubGrupo;
    private Spinner cbxTipoPagamento;
    private EditText txtDescricao;
    private ImageView btnImagem;

    private Date data;
    private Integer gastoId = null;
    private byte[] byteArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar);

        this.data = new Date();
        this.btnData = (Button) findViewById(R.id.btnData);
        this.btnData.setOnClickListener(this);

        this.txtValor = (EditText) findViewById(R.id.txtValor);

        this.cbxTipoPagamento = (Spinner) findViewById(R.id.cbxTipoPagamento);
        ArrayAdapter araTipoPagamento = new ArrayAdapter<TipoPagamento>(this, android.R.layout.simple_dropdown_item_1line, TipoPagamento.values());
        this.cbxTipoPagamento.setAdapter(araTipoPagamento);

        this.cbxGastoGrupo = (Spinner) findViewById(R.id.cbxGastoGrupo);
        ArrayAdapter araGastoGrupo = new ArrayAdapter<GastoGrupo>(this, android.R.layout.simple_dropdown_item_1line, GastoGrupo.values());
        this.cbxGastoGrupo.setAdapter(araGastoGrupo);
        this.cbxGastoGrupo.setOnItemSelectedListener(this);

        this.cbxGastoSubGrupo = (Spinner) findViewById(R.id.cbxGastoSubGrupo);
        ArrayAdapter araGastoSubGrupo = new ArrayAdapter<GastoGrupoCusto>(this, android.R.layout.simple_dropdown_item_1line, GastoGrupoCusto.values());
        this.cbxGastoSubGrupo.setAdapter(araGastoSubGrupo);
        this.cbxGastoSubGrupo.setOnItemSelectedListener(this);

        this.txtDescricao = (EditText) findViewById(R.id.txtDescricao);

        this.btnImagem = (ImageView) findViewById(R.id.btnImagem);
        this.btnImagem.setOnClickListener(this);

        this.gastoId = null;

        if (this.getIntent()!=null && this.getIntent().getExtras()!=null) {
            Object obj = this.getIntent().getExtras().get("gasto_id");
            if (obj != null) {

                this.gastoId = (Integer) obj;
                Gasto gasto = new GastoDAO(this).select(gastoId);

                this.data = gasto.getData();

                int spinnerPosition = 0;
                spinnerPosition = araTipoPagamento.getPosition(gasto.getTipoPagamento());
                this.cbxTipoPagamento.setSelection(spinnerPosition);

                spinnerPosition = araGastoGrupo.getPosition(gasto.getGrupo());
                this.cbxGastoGrupo.setSelection(spinnerPosition);

                if (gasto.getSubGrupo()!=null) {
                    spinnerPosition = araGastoSubGrupo.getPosition(gasto.getSubGrupo());
                    this.cbxGastoSubGrupo.setSelection(spinnerPosition);
                }

                if (gasto.getDescricao()!=null) {
                    this.txtDescricao.setText(gasto.getDescricao());
                }
                if (gasto.getValor()!=null) {
                    this.txtValor.setText(gasto.getValor().toString());
                }

                if (gasto.getImagem()!=null) {
                    byteArray = gasto.getImagem();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                    this.btnImagem.setImageBitmap(bitmap);
                }

            }
        }

        this.formataData();
        this.verificaOutro();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_adicionar_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnSalvarGasto: {
                this.salvar();
                return true;
            }
        }
        return false;
    }

    private SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    private void formataData() {
        this.btnData.setText(df.format(this.data));
    }

    private void verificaOutro() {

        if (this.cbxGastoGrupo.getSelectedItem().equals(GastoGrupo.CUSTO)) {

            this.cbxGastoSubGrupo.setEnabled(true);

            if (this.cbxGastoSubGrupo.getSelectedItem().equals(GastoGrupoCusto.OUTRO)) {
                this.txtDescricao.setEnabled(true);
            } else {
                this.txtDescricao.setEnabled(false);
            }

        } else {

            this.cbxGastoSubGrupo.setEnabled(false);
            this.txtDescricao.setEnabled(true);

        }

    }

    @Override
    public void onClick(View v) {
        if (v.equals(this.btnData)) {
            //System.out.println("show dialog");
            showDialog(0);

        } else if (v.equals(this.btnImagem)) {

            this.abrirCamera();

        }

    }

    private void salvar() {

        SimpleDateFormat dfSql = new SimpleDateFormat("yyyy-MM-dd");

        Gasto gasto = new Gasto();
        gasto.setId(this.gastoId);
        gasto.setData(this.data);
        if (!this.txtValor.getText().equals("")) {
            gasto.setValor(new BigDecimal(this.txtValor.getText().toString()));
        }
        gasto.setTipoPagamento(TipoPagamento.valueOf(this.cbxTipoPagamento.getSelectedItem().toString()));
        gasto.setGrupo(GastoGrupo.valueOf(this.cbxGastoGrupo.getSelectedItem().toString()));
        gasto.setSubGrupo(GastoGrupoCusto.valueOf(this.cbxGastoSubGrupo.getSelectedItem().toString()));
        gasto.setDescricao(this.txtDescricao.getText().toString());
        gasto.setImagem(byteArray);

        if (new GastoDAO(this).gravar(gasto)) {
            MainActivity.instance.refreshGastos();
            onBackPressed();
        }

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id==0) {

            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(this.data);

            return new DatePickerDialog(this, datePickeListener, gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), gc.get(Calendar.DATE));
        }
        return super.onCreateDialog(id);
    }

    private DatePickerDialog.OnDateSetListener datePickeListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            GregorianCalendar gc = new GregorianCalendar(year, month, dayOfMonth);
            data = gc.getTime();
            formataData();
        }
    };

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.verificaOutro();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    private void abrirCamera() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_CAMERA);

        } else {

            Intent intencao = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intencao, 0);

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ByteArrayOutputStream stream = null;

        if (requestCode==0 && resultCode==RESULT_OK) {

            try {

                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                btnImagem.setImageBitmap(bitmap);

                stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byteArray = stream.toByteArray();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (stream!=null) {
                    try {
                        stream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

}
