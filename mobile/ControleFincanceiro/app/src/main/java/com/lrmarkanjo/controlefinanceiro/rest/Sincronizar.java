package com.lrmarkanjo.controlefinanceiro.rest;

/**
 * Created by lrmar on 10/11/2016.
 */
import android.content.Context;

import com.loopj.android.http.*;
import com.lrmarkanjo.controlefinanceiro.MainActivity;
import com.lrmarkanjo.controlefinanceiro.banco.daos.GastoDAO;
import com.lrmarkanjo.controlefinanceiro.banco.daos.UsuarioDAO;
import com.lrmarkanjo.controlefinanceiro.banco.enums.GastoGrupo;
import com.lrmarkanjo.controlefinanceiro.banco.enums.GastoGrupoCusto;
import com.lrmarkanjo.controlefinanceiro.banco.enums.TipoPagamento;
import com.lrmarkanjo.controlefinanceiro.banco.modelos.Gasto;
import com.lrmarkanjo.controlefinanceiro.banco.modelos.Usuario;
import com.lrmarkanjo.controlefinanceiro.rest.interfaces.IRecebeGasto;
import com.lrmarkanjo.controlefinanceiro.rest.interfaces.IRecebePing;
import com.lrmarkanjo.controlefinanceiro.rest.interfaces.IRecebeUsuario;

import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HeaderElement;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.ParseException;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

public class Sincronizar {

    //private static final String BASE_URL = "http://192.168.100.4:8080/controlefinanceiro/rest/";
    public static final String BASE_URL = "http://192.168.100.4:8080/JavaAngular/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    /*
    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(Context context, String url, HttpEntity entity, AsyncHttpResponseHandler responseHandler) {
        //post(getApplicationContext(), "http://" + Constants.address + ":" + Constants.port + "/silownia_java/rest/login/auth", entity,
        client.post(context, getAbsoluteUrl(url), entity, "application/json", responseHandler);
        //client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
    */
    private SimpleDateFormat dfJson = new SimpleDateFormat("yyyy-MM-dd");

    public void enviarUsuario(Context context, final Usuario usuario, final IRecebeUsuario recebe, boolean novo) {

        JSONObject jsonObject = new JSONObject();
        ByteArrayEntity entity = null;
        try {

            jsonObject.put("id", usuario.getId());
            jsonObject.put("nome", usuario.getNome());
            jsonObject.put("email", usuario.getEmail());
            jsonObject.put("senha", usuario.getSenha());

            entity = new ByteArrayEntity(jsonObject.toString().getBytes("UTF-8"));
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (novo) {

            client.post(context, BASE_URL+"/usuario", entity, "application/json", new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {

                        usuario.setId(response.getInt("id"));
                        recebe.result(usuario);
                        System.out.println("sucesso");

                    } catch (Exception e) {
                        System.out.println("erro");
                    }

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    System.out.println("erro: " + responseString);
                }
            });

        } else {
            client.put(context, BASE_URL + "/usuario", entity, "application/json", new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {

                        usuario.setId(response.getInt("id"));
                        recebe.result(usuario);
                        System.out.println("sucesso");

                    } catch (Exception e) {
                        System.out.println("erro");
                    }

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    System.out.println("erro: " + responseString);
                }
            });
        }

    }

    public void enviarGasto(Context context, Gasto obj, final IRecebeGasto recebe) {

        Gasto gasto = new GastoDAO(context).select(obj.getId());

        JSONObject jsonObject = new JSONObject();
        ByteArrayEntity entity = null;
        try {

            //jsonObject.put("id", gasto.getId());
            jsonObject.put("valor", gasto.getValor());
            if (gasto.getDescricao()!=null) {
                jsonObject.put("descricao", gasto.getDescricao());
            }
            jsonObject.put("data", dfJson.format(gasto.getData()));
            if (gasto.getImagem()!=null) {
                jsonObject.put("imagem", Base64.encodeToString(gasto.getImagem(), Base64.DEFAULT));
            }
            if (gasto.getGrupo()!=null) {
                jsonObject.put("grupo", gasto.getGrupo().toString());
            }
            if (gasto.getSubGrupo()!=null) {
                jsonObject.put("subGrupo", gasto.getSubGrupo().toString());
            }
            jsonObject.put("tipoPagamento", gasto.getTipoPagamento());

            entity = new ByteArrayEntity(jsonObject.toString().getBytes("UTF-8"));
            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        client.post(context, BASE_URL+"/gasto", entity, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                System.out.println("sucesso");

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                System.out.println("erro: " + responseString);
            }
        });

    }

    public void buscarGasto(Context context, Integer id, final IRecebeGasto recebe) {


        client.get(BASE_URL+"/gasto/"+id, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Gasto gasto = null;
                try {

                    gasto = new Gasto();
                    gasto.setValor(BigDecimal.valueOf(response.getDouble("valor")));
                    gasto.setDescricao(response.getString("descricao"));
                    gasto.setData(new Date(response.getLong("data")));
                    gasto.setGrupo(GastoGrupo.valueOf(response.getString("grupo")));
                    gasto.setSubGrupo(GastoGrupoCusto.valueOf(response.getString("subGrupo")));
                    gasto.setTipoPagamento(TipoPagamento.valueOf(response.getString("tipoPagamento")));
                    String imagem = response.getString("imagem");
                    if (imagem!=null) {
                        gasto.setImagem(Base64.decode(imagem, Base64.DEFAULT));
                    }

                } catch (Exception e) {
                    gasto = null;
                    e.printStackTrace();
                }

                recebe.result(gasto);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                System.out.println("erro: " + responseString);
            }
        });
    }

    public void ping(Context context, final IRecebePing recebe) {

        Header[] headers = new Header[1];
        headers[0] = new Header() {
            @Override
            public String getName() {
                return "User-Agent";
            }

            @Override
            public String getValue() {
                Usuario usuario = new UsuarioDAO(MainActivity.instance.getApplicationContext()).select();
                return usuario.getEmail();
            }

            @Override
            public HeaderElement[] getElements() throws ParseException {
                return new HeaderElement[0];
            }
        };


        SyncHttpClient clientS = new SyncHttpClient();
        clientS.setTimeout(3000);
        clientS.get(context, BASE_URL+"/ping", headers, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                recebe.recebePing();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                System.out.println("Servidor: OFF: " + responseString);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                System.out.println("Servidor: OFF: " + errorResponse);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                System.out.println("Servidor: OFF: " + errorResponse);
            }
        });

    }

}
