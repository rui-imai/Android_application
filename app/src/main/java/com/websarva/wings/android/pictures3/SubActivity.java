package com.websarva.wings.android.pictures3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SubActivity extends AppCompatActivity {
    private float X, startX;//座標
    private float adjust = 100;//フリック
    private int position;
    private int listsize=159;
    private ImageView imageview;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        Intent intent = getIntent();
        int position = intent.getIntExtra("POSITION",0);//position(配列の添字)取得
        showPicture(position);//表示メソッドの呼び出し
    }

    public boolean onTouchEvent(MotionEvent evt){
        switch(evt.getAction()){
            case MotionEvent.ACTION_DOWN:
                startX = evt.getX();//開始位置
                break;

            case MotionEvent.ACTION_UP:
                X = evt.getX();//終了位置
                //右フリック
                if(X - startX > adjust){
                    if(position !=0){
                        position--;
                    }else{
                        position = listsize -1;
                    }
                }
                //左フリック
                else if(startX - X > adjust){
                    if(position != listsize -1){
                        position++;
                    }else{
                        position = 0;
                    }
                }
                showPicture(position);//表示メソッドの呼び出し
        }
        return false;
    }

    public void showPicture(int position){//1枚の画像を表示するメソッド
        this.position=position;
        ImageAdapter imageAdapter=new ImageAdapter(this);
        Bitmap bitmap=imageAdapter.getpicture(this.position);//画像情報の取得メソッドの呼び出し
        imageview = findViewById(R.id.image_view);
        imageview.setImageBitmap(bitmap);//ImageViewにセット
    }

    public void onBackPressed(){
        finish();
    }
}
