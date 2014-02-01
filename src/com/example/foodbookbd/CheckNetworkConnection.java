package com.example.foodbookbd;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class CheckNetworkConnection extends AsyncTask<Void, Void, Void> {
	boolean networkState;
	Context context;
	public AsyncResponse delegate = null;

	public CheckNetworkConnection(Context context) {
		this.context = context;

	}

	NetworkData checkOnlineStatus = new NetworkData();

	@Override
	protected Void doInBackground(Void... params) {
		networkState = checkOnlineStatus.hasActiveInternetConnection(context);

		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		if (networkState) {
			delegate.hasconnection(true);

		} else {
			delegate.processFinish(false);

		}
	}

}
