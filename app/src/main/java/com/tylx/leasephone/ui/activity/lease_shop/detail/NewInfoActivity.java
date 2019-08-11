//package com.tylx.leasephone.ui.activity.lease_shop.detail;
//
//import com.bookcoins.R;
//import com.bookcoin.entity.NewsEntity;
//import com.bookcoin.utils.ProbjectUtil;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.webkit.WebChromeClient;
//import android.webkit.WebView;
//import android.webkit.WebSettings.LayoutAlgorithm;
//import android.webkit.WebViewClient;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//
//public class NewInfoActivity extends Activity {
//	TextView titletextview;
//	TextView timetextview;
//	WebView webview;
//	NewsEntity newentity;
//	int currentmode;
//	boolean isoneload = false;
//	ProgressBar progressBar;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_newinfo);
//		titletextview = (TextView) findViewById(R.id.textView1);
//		timetextview = (TextView) findViewById(R.id.textView2);
//		webview = (WebView) findViewById(R.id.webView1);
//		progressBar = (ProgressBar) findViewById(R.id.progressBar1);
//		Intent intent = getIntent();
//		newentity = (NewsEntity) intent
//				.getSerializableExtra(ProbjectUtil.ENTITY);
//
//		if (intent != null) {
//			currentmode = intent.getIntExtra(ProbjectUtil.INTENT_MODE,
//					GongGaoActivity.NEW);
//			if (currentmode == GongGaoActivity.NEW) {
//				((TextView) findViewById(R.id.title_text_view))
//						.setText(R.string.trending_news);
//			} else {
//				((TextView) findViewById(R.id.title_text_view))
//						.setText(R.string.announcement);
//			}
//		}
//		titletextview.setText(newentity.getTitle());
//		// if (intent.getIntExtra(ProbjectUtil.INTENT_MODE, GongGaoActivity.NEW)
//		// == GongGaoActivity.NEW) {
//		// ((TextView) findViewById(R.id.title_text_view))
//		// .setText(R.string.trending_news);
//		// } else {
//		// ((TextView) findViewById(R.id.title_text_view))
//		// .setText(R.string.announcement);
//		// }
//		timetextview.setText(getResources().getString(R.string.publishdate)
//				+ "  " + ProbjectUtil.getCovertTime(newentity.getUpdateTime()));
//		webview.getSettings().setJavaScriptEnabled(true);
//		webview.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
//		webview.getSettings().setDefaultTextEncodingName("utf-8");
//		webview.loadDataWithBaseURL(null, newentity.getContent(),
//				"text/html; charset=UTF-8", null, null);
//		webview.setWebChromeClient(new myWebChromeClient());
//		webview.setWebViewClient(new WebViewClient() {
//			@Override
//			public void onPageFinished(WebView view, String url) {
//				// TODO Auto-generated method stub
//				super.onPageFinished(view, url);
//				if (url.equals("about:blank") && isoneload) {
//					isoneload = false;
//					view.loadDataWithBaseURL(null, newentity.getContent(),
//							"text/html; charset=UTF-8", null, null);
//				} else {
//					isoneload = true;
//				}
//				System.out.println("" + url);
//				// if (url.equals("about:blank")) {
//				// webview.loadDataWithBaseURL(null, newentity.getContent(),
//				// "text/html; charset=UTF-8", null, null);
//				// }
//			}
//
//			@Override
//			public boolean shouldOverrideUrlLoading(WebView view, String url) {
//				// TODO Auto-generated method stub
//				view.loadUrl(url);
//				return true;
//			}
//		});
//	}
//
//	class myWebChromeClient extends WebChromeClient {
//
//		@Override
//		public void onProgressChanged(WebView view, int newProgress) {
//			// TODO Auto-generated method stub
//			super.onProgressChanged(view, newProgress);
//			progressBar.setProgress(newProgress);
//		}
//
//	}
//
//	public void beback(View view) {
//		finish();
//	}
//
//	@Override
//	public void onBackPressed() {
//		// TODO Auto-generated method stub
//		if (webview.canGoBack()) {
//			webview.goBack();
//		} else
//			super.onBackPressed();
//	}
//}
