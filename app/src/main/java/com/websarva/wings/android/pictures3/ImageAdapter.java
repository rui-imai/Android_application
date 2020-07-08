package com.websarva.wings.android.pictures3;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

public class ImageAdapter extends BaseAdapter {
    private Context context;
    public String[] photolist;

    public ImageAdapter(Context c) {
        context = c;
        try {
            photolist = context.getAssets().list("photos");//参照するためのリスト

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Bitmap getpicture(int position) {//リストを参照し、画像情報を返すメソッド
        int target = position;
        String name = photolist[target];
        InputStream ist = null;
        try {
            ist = context.getAssets().open("photos/" + name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(ist);
        return bitmap;
    }

    //以下BaseAdapterの必須メソッド。ムリでした。

    public int getCount() {
        return photolist.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView img;
        if (convertView == null) {
            img = new ImageView(context);
            img.setLayoutParams(new GridView.LayoutParams(180,180));//画像の大きさ
            img.setScaleType(ImageView.ScaleType.FIT_CENTER);//画像の拡縮設定
            img.setBackgroundColor(Color.rgb(0,122,122));//画像エリアの背景設定。だいたい
        } else {
            img = (ImageView) convertView;
        }
        try {
            InputStream ims = context.getAssets().open("photos/" + photolist[position]);
            Bitmap bitmap = BitmapFactory.decodeStream(ims);
            img.setImageBitmap(bitmap);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }
}