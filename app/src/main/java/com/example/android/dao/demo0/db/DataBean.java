package com.example.android.dao.demo0.db;

import android.database.Cursor;

/**
 * Created by idisfkj on 16/3/31.
 * Email : idisfkj@qq.com.
 */
public class DataBean {
    public String contetn;

    public String getContetn() {
        return contetn;
    }

    public void setContetn(String contetn) {
        this.contetn = contetn;
    }

    public static DataBean formCursor(Cursor cursor) {
        DataBean info = new DataBean();
        info.contetn = cursor.getString(cursor.getColumnIndex(DataProviderHelper.ItemDBInfo.ITEM_CONTENT));
        return info;
    }

}
