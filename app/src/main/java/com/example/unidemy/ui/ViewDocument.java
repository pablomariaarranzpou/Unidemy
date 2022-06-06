package com.example.unidemy.ui;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.unidemy.R;

import model.DocumentCard;

public class ViewDocument extends AppCompatActivity {

    WebView webView;
    DocumentCard dc;
    int option_d = 0, option_a = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_document);
        option_a = 0;
        option_d = 1;
        webView = (WebView) findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setWebViewClient(new WebViewClient());
        String pdf;



        downloadOrOnlineDialog();

    }



    public void downloadOrOnlineDialog(){

            final Dialog dialogDownloadOrOnline = new Dialog(ViewDocument.this);
        dialogDownloadOrOnline.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (dialogDownloadOrOnline.getWindow() != null) {
                ColorDrawable colorDrawable = new ColorDrawable(Color.TRANSPARENT);
                dialogDownloadOrOnline.getWindow().setBackgroundDrawable(colorDrawable);
            }
        dialogDownloadOrOnline.setContentView(R.layout.dialog_document_view);
        dialogDownloadOrOnline.setCancelable(false);
        dialogDownloadOrOnline.show();

            TextView resumeText = (TextView) dialogDownloadOrOnline.findViewById(R.id.correct_answer);


            Button btn_descargar = (Button) dialogDownloadOrOnline.findViewById(R.id.dialogDescargar);
            Button btn_enlinea = (Button) dialogDownloadOrOnline.findViewById(R.id.dialogEnLinea);


            btn_descargar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogDownloadOrOnline.dismiss();
                    String pdf;
                    if (getIntent().hasExtra("selectedDocument")) {

                        dc = (DocumentCard) getIntent().getParcelableExtra("selectedDocument");
                        downloadOrOnlineDialog();
                        pdf = dc.getDocument_url();
                        Uri webpage = Uri.parse(pdf);
                        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                        startActivity(intent);

                    }else{
                        pdf = "http://www.adobe.com/devnet/acrobat/pdfs/pdf_open_parameters.pdf";
                        webView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + pdf);
                    }
                    finish();
                }
            });



        btn_enlinea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDownloadOrOnline.dismiss();
                String pdf;
                if (getIntent().hasExtra("selectedDocument")) {
                    dc = (DocumentCard) getIntent().getParcelableExtra("selectedDocument");
                    pdf = dc.getDocument_url();
                    webView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + pdf);
                }else{
                    pdf = "http://www.adobe.com/devnet/acrobat/pdfs/pdf_open_parameters.pdf";
                    webView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + pdf);
                }
            }
        });


    }



}
