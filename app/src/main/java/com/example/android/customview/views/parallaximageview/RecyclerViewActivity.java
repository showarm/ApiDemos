package com.example.android.customview.views.parallaximageview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.example.android.apis.R;
import com.example.android.imageloader.ryg.ImageLoader;
import com.example.android.util.ScreenUtils;


public class RecyclerViewActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ParallaxRecyclerViewController mParallaxRecyclerViewController;
    private RadioGroup radioGroup;
    private RecyclerViewAdapter recyclerViewAdapter;
    private StaggerGridRecyclerViewAdapter staggerGridRecyclerViewAdapter;
    ImageLoader mImageLoader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        viewInit();
    }

    private void viewInit() {
        mImageLoader = ImageLoader.build(this);

        radioGroup = (RadioGroup) findViewById(R.id.mradiogroup);
        radioGroup.setOnCheckedChangeListener(onCheckedChangeListener);

        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);
        recyclerViewAdapter = new RecyclerViewAdapter(mImageLoader, getLayoutInflater());
        staggerGridRecyclerViewAdapter = new StaggerGridRecyclerViewAdapter(mImageLoader, getLayoutInflater());
        mRecyclerView.setAdapter(recyclerViewAdapter);
        setLinear();
    }

    RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            switch (checkedId) {
                case R.id.rd_linear:
                    setLinear();
                    break;
                case R.id.rd_staggered_grid:
                    setStaggeredGrid();
                    break;
                case R.id.rd_grid:
                    setGrid();
                    break;

            }

        }
    };

    private void setStaggeredGrid() {
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mParallaxRecyclerViewController = new ParallaxRecyclerViewController(staggeredGridLayoutManager, R.id.img);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        mRecyclerView.addOnScrollListener(mParallaxRecyclerViewController);
        mRecyclerView.setAdapter(staggerGridRecyclerViewAdapter);
    }

    private void setGrid() {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mParallaxRecyclerViewController = new ParallaxRecyclerViewController(gridLayoutManager, R.id.img);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.addOnScrollListener(mParallaxRecyclerViewController);
        mRecyclerView.setAdapter(recyclerViewAdapter);
    }

    private void setLinear() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mParallaxRecyclerViewController = new ParallaxRecyclerViewController(linearLayoutManager, R.id.img);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addOnScrollListener(mParallaxRecyclerViewController);
        mRecyclerView.setAdapter(recyclerViewAdapter);
    }


    public static class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {


        private LayoutInflater mInflater;
        ImageLoader mImageLoader;

        public RecyclerViewAdapter(ImageLoader imageLoader, LayoutInflater mInflater) {
            this.mImageLoader = imageLoader;
            this.mInflater = mInflater;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder viewHolder;
            viewHolder = new MyViewHolder(
                    mInflater.inflate(R.layout.list_item, parent, false));
            return viewHolder;
        }

        @Override
        public int getItemCount() {
            return ids.length;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {

            mImageLoader.bindBitmap("android.resource://com.example.android.apis/"+ids[position],holder.parallaxImageView);
        }


        static class MyViewHolder extends RecyclerView.ViewHolder {

            ParallaxImageView parallaxImageView;

            public MyViewHolder(View view) {
                super(view);
                parallaxImageView = (ParallaxImageView) view.findViewById(R.id.img);
            }
        }

        private int[] ids = new int[]{
                R.mipmap.test1, R.mipmap.test2, R.mipmap.test3
                , R.mipmap.test4
                , R.mipmap.test5
                , R.mipmap.test6
                , R.mipmap.test7
                , R.mipmap.test8
                , R.mipmap.test1, R.mipmap.test2, R.mipmap.test3
                , R.mipmap.test4
                , R.mipmap.test5
                , R.mipmap.test6
                , R.mipmap.test7
                , R.mipmap.test8, R.mipmap.test6
                , R.mipmap.test7
                , R.mipmap.test8
                , R.mipmap.test1, R.mipmap.test2, R.mipmap.test3
                , R.mipmap.test4, R.mipmap.test6
                , R.mipmap.test7
                , R.mipmap.test8
                , R.mipmap.test1, R.mipmap.test2, R.mipmap.test3
                , R.mipmap.test4, R.mipmap.test6
                , R.mipmap.test7
                , R.mipmap.test8
                , R.mipmap.test1, R.mipmap.test2, R.mipmap.test3
                , R.mipmap.test4
        };


    }


    public static class StaggerGridRecyclerViewAdapter extends RecyclerView.Adapter<StaggerGridRecyclerViewAdapter.MyViewHolder> {

        private static final String TAG = "StaggerGrid";
        ImageLoader mImageLoader;

        private LayoutInflater mInflater;
        private static final float DEFAULT_PARALLAX_RATIO = 0.2f;
        private int itemWidth;

        public StaggerGridRecyclerViewAdapter(ImageLoader mImageLoader, LayoutInflater mInflater) {
            this.mImageLoader = mImageLoader;
            this.mInflater = mInflater;
            itemWidth = ScreenUtils.getScreenWidth(mInflater.getContext()) / 2;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder viewHolder;
            viewHolder = new MyViewHolder(
                    mInflater.inflate(R.layout.recycler_item, parent, false));
            return viewHolder;
        }

        @Override
        public int getItemCount() {
            return ids2.length;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            final float imgRatio = ratios[position] - DEFAULT_PARALLAX_RATIO;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(itemWidth, (int) (itemWidth * imgRatio));
            holder.linearLayout.setLayoutParams(params);//为了防止瀑布流闪动，在重新刷新组件的时候就需要确定好宽高
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.parallaxImageView.getLayoutParams();
            layoutParams.weight = itemWidth;
            layoutParams.height = (int) (itemWidth * ratios[position]);
            holder.parallaxImageView.setLayoutParams(layoutParams);

            String uri = "android.resource://com.example.android.apis/"+ids2[position];
//            Bitmap b = mImageLoader.loadBitmap(uri);
//
//            holder.parallaxImageView.setParallax_Drawable(resource, imgRatio, DEFAULT_PARALLAX_RATIO);
        }

        static class MyViewHolder extends RecyclerView.ViewHolder {

            ParallaxImageView parallaxImageView;
            LinearLayout linearLayout;

            public MyViewHolder(View view) {
                super(view);
                linearLayout = (LinearLayout) view.findViewById(R.id.item_linear);
                parallaxImageView = (ParallaxImageView) view.findViewById(R.id.img);
            }
        }

        private int[] ids2 = new int[]{
                R.mipmap.test1, R.mipmap.test9, R.mipmap.test10, R.mipmap.test11,
                R.mipmap.test12, R.mipmap.test13, R.mipmap.test14, R.mipmap.test15,
                R.mipmap.test16, R.mipmap.test17, R.mipmap.test18, R.mipmap.test19,
                R.mipmap.test20, R.mipmap.test21, R.mipmap.test22, R.mipmap.test23,
                R.mipmap.test24, R.mipmap.test25,
                R.mipmap.test26, R.mipmap.test28, R.mipmap.test27, R.mipmap.test29,
                R.mipmap.test30, R.mipmap.test31, R.mipmap.test32
        };

        private float[] ratios = new float[]{
                0.9680328f,
                0.7854545f,
                1.3f,
                1.2709193f,
                1.168f,
                1.3f,
                1.131111f,
                1.2983606f,
                1.3419102f,
                1.3019607f,
                1.1333333f,
                1.2297521f,
                1.340041f,
                1.278424f,
                1.5751479f,
                1.3015015f,
                1.2746717f,
                1.2209591f,
                1.0028985f,
                1.0821011f,
                1.3f,
                1.3015015f,
                1.4747f,
                1.3f,
                1.33333f,
        };

    }


}
