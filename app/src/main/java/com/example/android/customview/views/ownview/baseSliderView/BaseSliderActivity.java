package com.example.android.customview.views.ownview.baseSliderView;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.example.android.apis.R;

import java.util.HashMap;


public class BaseSliderActivity extends Activity implements BaseSliderView.OnSliderClickListener{

    ViewPager pager;
    SliderAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_slider);
        pager = (ViewPager) findViewById(R.id.pager);

        adapter = new SliderAdapter(this);


        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Hannibal",R.drawable.hannibal);
        file_maps.put("Big Bang Theory", R.drawable.bigbang);
        file_maps.put("House of Cards", R.drawable.house);
        file_maps.put("Game of Thrones", R.drawable.game_of_thrones);

        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            adapter.addSlider(textSliderView);
        }
        pager.setAdapter(adapter);


    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(BaseSliderActivity.this, slider.getDescription(), Toast.LENGTH_SHORT).show();
    }
}
