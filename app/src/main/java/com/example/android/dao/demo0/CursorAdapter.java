package com.example.android.dao.demo0;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.apis.R;
import com.example.android.dao.demo0.db.DataBean;


/**
 * Created by idisfkj on 16/4/2.
 * Email : idisfkj@qq.com.
 */
public class CursorAdapter extends RecyclerBaseCursorAdapter<CursorAdapter.CursorViewHolder> {
    private LayoutInflater mLayoutInflater;

    public CursorAdapter(Context context) {
        super(context, null);
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public void onBindViewHolder(CursorViewHolder holder, Cursor cursor) {
        holder.itemTv.setText(DataBean.formCursor(cursor).contetn);
    }

    @Override
    public CursorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_text, parent, false);
        return new CursorViewHolder(view);
    }

    static class CursorViewHolder extends RecyclerView.ViewHolder {

        TextView itemTv;

        CursorViewHolder(View view) {
            super(view);
            itemTv = (TextView) view.findViewById(R.id.item_tv);
        }
    }
}
