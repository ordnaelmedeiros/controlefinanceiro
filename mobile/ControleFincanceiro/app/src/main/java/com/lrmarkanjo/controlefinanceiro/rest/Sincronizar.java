package com.lrmarkanjo.controlefinanceiro.rest;

/**
 * Created by lrmar on 10/11/2016.
 */
import android.content.Context;

import com.loopj.android.http.*;

import cz.msebera.android.httpclient.HttpEntity;

public class Sincronizar {

    private static final String BASE_URL = "http://192.168.100.4:8080/controlefinanceiro/rest/";

    private static AsyncHttpClient client = new AsyncHttpClient();

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
}
