package com.gmail.blackbull8810.noiseapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChartFragment extends Fragment {

    private View view;
    private WebView webview;
    private Bundle webViewBundle;

    public ChartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        webview.saveState(outState);
        Log.d("VIVZ", "State saved !!!!!!!!!!!");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        webview.restoreState(savedInstanceState);
    }

    //newInstance저장.
    public static ChartFragment newInstance() {
        
        ChartFragment fragment = new ChartFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_chart, container, false);

        webview = (WebView)view.findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient());

        //webview.loadUrl("http://61.98.45.34:8081");
//        webview.loadUrl("http://www.google.com");
//        webview.loadUrl("http://119.198.33.102/google/mobile_chart2.php");

        if (webViewBundle == null) {
            webview.loadUrl("http://119.198.33.102/google/mobile_chart2.php");
        } else {
            webview.restoreState(webViewBundle);
        }

//        //웹뷰 줌 지원 안되게 하는법
//        WebSettings webSettings = webview.getSettings();
//        webSettings.setSupportZoom(false);

        return view;

//        return inflater.inflate(R.layout.fragment_chart, container, false);
    }

    @Override
    public void onPause() {
        super.onPause();

        webViewBundle = new Bundle();
        webview.saveState(webViewBundle);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
