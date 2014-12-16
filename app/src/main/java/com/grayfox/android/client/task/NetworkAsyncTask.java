package com.grayfox.android.client.task;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.grayfox.android.R;
import com.grayfox.android.http.RequestBuilder;

import roboguice.util.RoboAsyncTask;

abstract class NetworkAsyncTask<T> extends RoboAsyncTask<T> {

    protected NetworkAsyncTask(Context context) {
        super(context);
    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void request() {
        if (isConnected()) execute();
        else Toast.makeText(getContext(), R.string.network_unavailable, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onException(Exception e) throws RuntimeException {
        if (e instanceof RequestBuilder.RequestException) onRequestException(e.getCause());
    }

    protected void onRequestException(Throwable e) {
        Toast.makeText(getContext(), R.string.grayfox_api_request_error, Toast.LENGTH_LONG).show();
    }
}