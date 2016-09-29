package app.ifthen.com.if_then;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.ifthen.backend.ifBeanApi.IfBeanApi;
import com.ifthen.backend.ifBeanApi.model.IfBean;
import com.ifthen.backend.thenBeanApi.ThenBeanApi;
import com.ifthen.backend.thenBeanApi.model.ThenBean;

import java.io.IOException;
import java.util.Collections;

/**
 * Created by bjeyara on 9/22/16.
 */
public class EndpointsAsyncTask extends AsyncTask<Void, String, IfThen> {
    private final MainActivity mainActivity;
    private final String thenStr;
    private final String ifStr;
    private static IfBeanApi ifBeanApi;
    private static ThenBeanApi thenBeanApi;
    private final GoogleAccountCredential credential;
    private final String sessionKey;

    public EndpointsAsyncTask(Context context, String ifStr, String thenStr, String sessionKey, GoogleAccountCredential credential) {
        this.mainActivity = (MainActivity) context;
        this.ifStr = ifStr;
        this.thenStr = thenStr;
        this.sessionKey=sessionKey;
        this.credential=credential;
    }

    @Override
    protected void onPreExecute() {
//        super.onPreExecute();
        mainActivity.getResultTextView().setText("Sending ....");
    }

    @Override
    protected void onProgressUpdate(String... values) {
//        super.onProgressUpdate(values);
        mainActivity.getResultTextView().setText(values[0]);
    }

    @Override
    protected IfThen doInBackground(Void... params) {
        buildIfBeanEndpoint();
        publishProgress("Making sense of If");
        buildThenBeanEndpoint();
        publishProgress("Making sense of Then");
        try {
            publishProgress("Giving up on making sense of your If and Then. Sending it anyway ....");
            IfBean ifBean = ifBeanApi.insert(new IfBean().setSessionId(sessionKey).setText(ifStr)).execute();
            ThenBean thenBean = thenBeanApi.insert(new ThenBean().setSessionId(sessionKey).setText(thenStr)).execute();
            Log.d("Tag", "doInBackground: " + ifBean == null ? "null" : ifBean.toString() + " " + thenBean == null ? "null" : thenBean.toString());
            publishProgress("Randomizing If and Then ....");
            return new IfThen(ifBeanApi.random(sessionKey).execute(), thenBeanApi.random(sessionKey).execute());
        } catch (Exception e) {
            Log.d("Tag", "doInBackground,error: " + e);
            return new IfThen(new IfBean(),new ThenBean());
        }
    }

    @Override
    protected void onPostExecute(IfThen ifThen) {
        TextView textResult = (TextView) mainActivity.findViewById(R.id.text_result);
        textResult.setText("If " + ifThen.getIfBean().getText() + " then " + ifThen.getThenBean().getText());
    }

    private void buildIfBeanEndpoint() {
        if (ifBeanApi == null) {
            IfBeanApi.Builder builder = new
                    IfBeanApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), credential)
                    .setRootUrl("https://if-then.appspot.com/_ah/api/");
            //uncomment for local dev - start
//                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
//                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
//                        @Override
//                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
//                            abstractGoogleClientRequest.setDisableGZipContent(true);
//                        }
//                    });
            //uncomment for local dev - end
            ifBeanApi = builder.build();
        }
    }

    private void buildThenBeanEndpoint() {
        if (thenBeanApi == null) {
            ThenBeanApi.Builder builder = new
                    ThenBeanApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl("https://if-then.appspot.com/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            thenBeanApi = builder.build();
        }
    }
}
