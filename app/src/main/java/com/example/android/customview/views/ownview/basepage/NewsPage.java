package com.example.android.customview.views.ownview.basepage;


import android.content.Context;
import android.widget.TextView;

import com.example.android.apis.R;


public class NewsPage extends BasePage {

	TextView textView;
	public NewsPage(Context context) {
		super(context);
	}

	@Override
	protected int layoutRes() {
		return R.layout.page_news;
	}

	@Override
	protected void findViews() {
		textView =  findViewThroughId(R.id.textView);
	}

	@Override
	protected void initData() {

		failedViewDismiss();
		loadingViewDismiss();

	}

}
