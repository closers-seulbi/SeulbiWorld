package cf.seulbi.world;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebView = (WebView) findViewById(R.id.webView);

        // 링크를 강제적으로 브라우저 대신 WebView 로 연결하도록 리다이렉트
        mWebView.setWebViewClient(new WebViewClient());

        //

        // Javascript 허용
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // 외부 리소스 사용
        mWebView.loadUrl("https://seulbi.cf");

        // 로컬 링크를 브라우저로 리다이렉트 되는 것을 방지
        mWebView.setWebViewClient(new AppWebViewClient());

        // 로컬 리소스 사용
        // mWebView.loadUrl("file:///android_asset/www/index.html");
    }

    // 회전 시 화면 초기화 방지
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
    }

    // 뒤로가기 버튼으로 앱이 종료되는 것을 방지
    @Override
    public void onBackPressed() {
        if(mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 메뉴를 전개
        getMenuInflater().inflate(R.menu.menu_cache, menu);
        getMenuInflater().inflate(R.menu.menu_update, menu);
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // noinspection SimplifiableIfStatement
        if (id == R.id.action_cache) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.action_cache) //팝업창 타이틀바
                    .setMessage(R.string.cache_alert) //팝업창 내용
                    .setNeutralButton(R.string.neutral_button,new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dlg, int hv) {

                        }
                    })
                    .show(); // 팝업창 보여줌

            mWebView.clearHistory();
            mWebView.clearFormData();
            mWebView.clearCache(true);
            mWebView.loadUrl("https://seulbi.cf");

            return true;
        }

        if (id == R.id.action_update) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.action_update) //팝업창 타이틀바
                    .setMessage(R.string.preparing_update) //팝업창 내용
                    .setNeutralButton(R.string.neutral_button,new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dlg, int hv) {

                        }
                    })
                    .show(); // 팝업창 보여줌

        }
        if (id == R.id.action_settings) {

            // 버전 네임과 코드 불러오기
            PackageInfo pInfo = null;
            try {
                pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            String versionName = pInfo.versionName;
            int versionCode = pInfo.versionCode;

            new AlertDialog.Builder(this)
                    .setTitle(R.string.action_version) //팝업창 타이틀바
                    .setMessage("versionCode " + versionCode + "\n" + "versionName " + versionName) //팝업창 내용
                    .setNeutralButton(R.string.neutral_button,new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dlg, int hv) {

                        }
                    })
                    .show(); // 팝업창 보여줌
        }

        return super.onOptionsItemSelected(item);
    }
}