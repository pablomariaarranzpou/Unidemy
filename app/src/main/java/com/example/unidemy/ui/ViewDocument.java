package com.example.unidemy.ui;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.unidemy.R;

public class ViewDocument extends AppCompatActivity {

    WebView webView;
    DocumentCard dc;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_document);

        webView = (WebView) findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        String pdf = "http://www.adobe.com/devnet/acrobat/pdfs/pdf_open_parameters.pdf";
        webView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + pdf);

        if (getIntent().hasExtra("selectedDocument")) {
            dc = (DocumentCard) getIntent().getParcelableExtra("selectedVideo");
            pdf = dc.getDocument_url();
            webView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + pdf);
        }else{
            pdf = "http://www.adobe.com/devnet/acrobat/pdfs/pdf_open_parameters.pdf";
            webView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + pdf);
        }
    }
}
