package app.ifthen.com.if_then;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {

    private static final String IF ="if";
    private static final String THEN ="then";
    private static final int REQUEST_ACCOUNT_PICKER = 2;
    private SharedPreferences settings;
    private GoogleAccountCredential credential;
    private String accountName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        //account stuff
//        //Account stuff
//        settings = getSharedPreferences("FamousQuotesAndroid", 0);
//        credential = GoogleAccountCredential.usingAudience(this,"server:client_id:956984926970-59s38oierppv3e8ld188gkebm2vkkegf.apps.googleusercontent.com");
//        setAccountName(settings.getString("ACCOUNT_NAME", null));
//        if (credential.getSelectedAccountName() != null) {
//            // Already signed in, begin app!
//            Toast.makeText(getBaseContext(), "Logged in with : " + credential.getSelectedAccountName(), Toast.LENGTH_SHORT).show();
//            //Toast.makeText(getBaseContext(), GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext()),Toast.LENGTH_SHORT).show();
//        } else {
//            // Not signed in, show login window or request an account.
//            chooseAccount();
//        }
    }

    public void sendMessage(View view) {
        EditText editIf=(EditText)findViewById(R.id.edit_if);
        String ifStr=editIf.getText().toString();
        EditText editThen=(EditText)findViewById(R.id.edit_then);
        String thenStr=editThen.getText().toString();
        new EndpointsAsyncTask(this, ifStr, thenStr,credential).execute();
    }
//
//    private void setAccountName(String accountName) {
//        SharedPreferences.Editor editor = settings.edit();
//        editor.putString("ACCOUNT_NAME", accountName);
//        editor.commit();
//        credential.setSelectedAccountName(accountName);
//        this.accountName = accountName;
//    }
//
//    void chooseAccount() {
//        startActivityForResult(credential.newChooseAccountIntent(),
//                REQUEST_ACCOUNT_PICKER);
//    }

    public TextView getResultTextView(){
        return (TextView)findViewById(R.id.text_result);
    }
}
