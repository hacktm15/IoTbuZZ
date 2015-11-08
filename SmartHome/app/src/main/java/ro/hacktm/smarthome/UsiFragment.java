package ro.hacktm.smarthome;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * Created by user on 07.11.2015.
 */
public class UsiFragment extends Fragment {

    private ContentReceiver contentReceiver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_usi, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        WebView webViewBariera = (WebView) getView().findViewById(R.id.webViewBariera);
        String customHtml = "<iframe src='http://10.0.100.169:8081' width='100%' height='100%' style='border: none;'></iframe>";
        webViewBariera.loadUrl("http://10.0.100.169:8081");
        webViewBariera.getSettings().setJavaScriptEnabled(true);
        webViewBariera.loadDataWithBaseURL("", customHtml, "text/html", "UTF-8", "");



        WebView webViewUsa = (WebView) getView().findViewById(R.id.webViewUsa);
        webViewUsa.loadUrl("http://10.0.100.169:8081");
        webViewUsa.getSettings().setJavaScriptEnabled(true);
        webViewUsa.loadDataWithBaseURL("", customHtml, "text/html", "UTF-8", "");
    }
}
