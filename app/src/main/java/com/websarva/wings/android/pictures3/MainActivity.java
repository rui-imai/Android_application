package com.websarva.wings.android.pictures3;

import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
//import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener {


    public final static String PHOTOS_FOLDER = "photos";
    public final static String PHOTOS_FOLDER_PATH = PHOTOS_FOLDER + "/";
    private final int SPAN_COUNT = 5;// グリッドの横の要素数

    private  RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ツールバーの設定
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolbarLayout = findViewById(R.id.toolbarLayout);
        toolbarLayout.setTitle(getString(R.string.toolbar_title));

        RecyclerView recyclerView = findViewById(R.id.Recyclerview);
        //レイアウトマネージャーの設定
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,SPAN_COUNT);
        recyclerView.setLayoutManager(gridLayoutManager);

        // bitmapのリストを作成
        List<Bitmap> beautiesList = createBeautiesList();
        //アダプターをセット
        adapter = new RecyclerViewAdapter(this,beautiesList);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    public void onItemClick(View view, int position) {//画面遷移メソッド。positionを渡す
//        String text=position + "番目";
//        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();//値の確認
        Intent intent = new Intent(getApplication(), SubActivity.class);
        intent.putExtra("POSITION",position);//タップされた画像のposition(配列の添字)を渡す
        startActivity(intent);
    }

    private List<String> getFileList() {
        AssetManager assetManager = getResources().getAssets();

        List<String> fileList = null;
        try {
            fileList = Arrays.asList(assetManager.list(PHOTOS_FOLDER));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileList;
    }

    private List<Bitmap> createBeautiesList() {

        DisplayMetrics dMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dMetrics);
        int screenWidth = dMetrics.widthPixels;

        Resources res = getResources();
        float leftMarginDp = res.getDimension(R.dimen.left_card_view_margin);
        float rightMarginDp = res.getDimension(R.dimen.right_card_view_margin);

        int imgWidth = (int) ((screenWidth - (rightMarginDp + leftMarginDp) * SPAN_COUNT) / SPAN_COUNT);

        List<String> fileList = getFileList();

        List<Bitmap> bitmaps = new ArrayList<>();

        try {
            for (String fileName : fileList) {
                Bitmap img = BitmapUtils.decodeSampledBitmap(MainActivity.this, PHOTOS_FOLDER_PATH + fileName, imgWidth, imgWidth, Bitmap.Config.RGB_565);
                bitmaps.add(img);
            }
        } catch (NullPointerException e) {
        }

        return bitmaps;
    }
}

