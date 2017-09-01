package com.example.khotiun.ekaterinoslav.fragments;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.khotiun.ekaterinoslav.R;

/**
 * Created by hotun on 13.08.2017.
 */

public class LinkPageFragment extends Fragment {
    private static final String ARG_URI = "link_page_url";

    private String mUri;
    private WebView mWebView;
    private ProgressBar mProgressBar;


    public static LinkPageFragment newInstance(String uri) {
        Bundle args = new Bundle();
        args.putString(ARG_URI, uri);
        LinkPageFragment fragment = new LinkPageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUri = getArguments().getString(ARG_URI);
    }
    //@SuppressLint("SetJavaScriptEnabled") - отключить Android Lint
    @SuppressLint("SetJavaScriptEnabled")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_link_page, container, false);
        mProgressBar = (ProgressBar) v.findViewById(R.id.fragment_link_page_progress_bar);
        mProgressBar.setMax(100);// Значения в диапазоне 0-100
        mWebView = (WebView) v.findViewById(R.id.fragment_link_page_web_view);
        mWebView.getSettings().setBuiltInZoomControls(true);// устанавливаем Zoom control
        mWebView.getSettings().setJavaScriptEnabled(true);//включение JavaScript для того чтобы отображать веб страничку в приложении
        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView webView, int newProgress) {
                //Если результат равен 100, значит, загрузка страницы завершена, поэтому мы скрываем ProgressBar, задавая режим View.GONE.
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setProgress(newProgress);
                }
            }
            //на панели инструментов появляется подзаголовок с текстом, полученным в onReceivedTitle(…)
//            public void onReceivedTitle(WebView webView, String title) {
//                AppCompatActivity activity = (AppCompatActivity) getActivity();
//                activity.getSupportActionBar().setSubtitle(title);
//            }
        });

        mWebView.setWebViewClient(new WebViewClient());
        mWebView.loadUrl(mUri);//какой URL-адрес необходимо загрузить
        return v;
    }
}
