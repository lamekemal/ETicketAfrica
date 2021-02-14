package bj.archeos.eticketafrica;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.IOException;

import bj.archeos.eticketafrica.data.TinyDB;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainValid extends AppCompatActivity {
    private static final int ZXING_CAMERA_PERMISSION = 1;
    private Class<?> mClss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_valid);
        //Intent intxx = getIntent();
        //Integer catD = intxx.getIntExtra("Categories",0);
        TinyDB edatast = new TinyDB(getApplicationContext());
        TextView txtval = (TextView) findViewById(R.id.validatorid);
        //txtval.setText(catD.toString());
        MaterialButton fab = findViewById(R.id.save_bt);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanQrCode();
                //run("");
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("ction", null).show();*/
            }
        });
    }

    OkHttpClient client = new OkHttpClient();
    String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public void scanQrCode() {
        IntentIntegrator myIntegrator = new IntentIntegrator(this);
        myIntegrator.setCaptureActivity(CaptureAct.class);
        myIntegrator.setOrientationLocked(false);
        myIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        myIntegrator.setPrompt("Lecture en cour...");
        myIntegrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (result!= null){
            if (result.getContents()!= null){
                ImageView imgt = (ImageView) findViewById(R.id.imageView2);
                imgt.setVisibility(View.GONE);
                WebView wtc = (WebView) findViewById(R.id.ticket_cc);
                wtc.getSettings().setJavaScriptEnabled(true);
                wtc.setWebViewClient(new WebViewClient(){
                    @SuppressWarnings("deprecation")
                    @Override
                    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                        super.onReceivedError(view, request, error);
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT);
                    }
                });
                wtc.loadUrl("google.fr");
                wtc.getSettings().setPluginState(WebSettings.PluginState.ON);
                AlertDialog.Builder build = new AlertDialog.Builder(this);
                build.setMessage(result.getContents());
                build.setTitle("Information");
                build.setPositiveButton("Re-Scanné", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        scanQrCode();
                    }
                }).setNegativeButton("Terminer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                AlertDialog dialog = build.create();
                dialog.show();
            }else {
                Toast.makeText(this, "Aucun résultat", Toast.LENGTH_SHORT);
            }
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}