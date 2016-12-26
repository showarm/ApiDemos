package com.example.android.customview.views.ownview.basepage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.android.apis.R;

import java.util.ArrayList;
import java.util.List;


public class BasePageActivity extends AppCompatActivity {

    List<BasePage> pages = new ArrayList<>();
    NewsPage newsPage;
    MinePage minePage;
    FrameLayout container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_page);
        container = (FrameLayout) findViewById(R.id.container);
        pages.clear();
    }
    public void onMe(View view){
        Toast.makeText(BasePageActivity.this, "me", Toast.LENGTH_SHORT).show();

        if (minePage == null){
            minePage = new MinePage(this);
            pages.add(minePage);
        }
        container.removeAllViews();
        container.addView(minePage.getView());


    }
    public void onNews(View view){
        Toast.makeText(BasePageActivity.this, "news", Toast.LENGTH_SHORT).show();

        if (newsPage == null){
            newsPage = new NewsPage(this);
            pages.add(newsPage);
        }

        container.removeAllViews();
        container.addView(newsPage.getView());

    }
}
