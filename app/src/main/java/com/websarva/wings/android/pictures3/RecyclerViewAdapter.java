package com.websarva.wings.android.pictures3;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
//import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<Bitmap> _beautiesList;
    private LayoutInflater _inflater;
    private Context _context;
    private ItemClickListener itemClickListener;

    //コンストラクタ
    public RecyclerViewAdapter(Context context, List<Bitmap> beautiesList) { //引数をフィールドに格納。
        this._context=context;
        this._inflater=LayoutInflater.from(_context);
        this._beautiesList = beautiesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //row.xmlをインフレートし、1行分の画面部品とする。
        View view = _inflater.inflate(R.layout.row, parent, false);//生成したビューホルダをリターン
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.imageView.setImageBitmap(_beautiesList.get(position));
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return _beautiesList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageview);
            imageView.setBackgroundColor(Color.rgb(0,122,122));//背景色をRGB指定
            itemView.setOnClickListener(this);//リスナ設定
        }
        @Override
        public void onClick(View view) {
            if (itemClickListener != null) itemClickListener.onItemClick(view, getAdapterPosition());
        }
    }
    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
