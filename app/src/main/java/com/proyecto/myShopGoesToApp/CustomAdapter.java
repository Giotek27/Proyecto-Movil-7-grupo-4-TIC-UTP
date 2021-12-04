package com.proyecto.myShopGoesToApp;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.proyectoapp1.R;
import com.google.firebase.database.annotations.Nullable;


import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private static final String TAG="customAdapter";
    private ArrayList<Productos> mDataset= new ArrayList<Productos>();


    public CustomAdapter(ArrayList<Productos>dataset){

        mDataset=dataset;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        View view= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.mostrardatos,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position){
        Bitmap decodedByte=null;
        byte[] decodeString;
        Productos productos= mDataset.get(position);
        viewHolder.getTv1().setText(productos.getNomproduc());
        viewHolder.getTv2().setText(productos.getCatproducto());
        viewHolder.getTv3().setText(productos.getPrecio());
        viewHolder.getEt().setText(productos.getDescproduc());
        //cargar foto
        decodeString= Base64.decode(productos.getImagen(),Base64.DEFAULT);
        decodedByte= BitmapFactory.decodeByteArray(decodeString,0,decodeString.length);
        viewHolder.getIv().setImageBitmap(decodedByte);

    }




    @Override
    public int getItemCount(){
        if (mDataset.size()>0){
            return mDataset.size();
        }
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        private final CardView cardv;
        private final ImageView iv;
        private final TextView tv1,tv2,tv3;
        private final EditText et;

        public ViewHolder(View view){
            super(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG,"Elemento"+getAdapterPosition()+"seleccionado");
                }
            });
            cardv=view.findViewById(R.id.cardview);
            iv=view.findViewById(R.id.imgView);
            tv1=view.findViewById(R.id.nomproducto);
            tv2=view.findViewById(R.id.catProduct);
            tv3=view.findViewById(R.id.precio);
            et=view.findViewById(R.id.descproduc);
            //iv.setImageResource(R.mipmap.ic_new_launcher);

        }



        public CardView getCardv() {
            return cardv;
        }
        public ImageView getimgView() {
            return iv;
        }

        public TextView getTv1() {
            return tv1;
        }

        public TextView getTv2() {
            return tv2;
        }

        public TextView getTv3() {
            return tv3;
        }

        public EditText getEt() {
            return et;
        }

        public ImageView getIv() {
            return iv;
        }


    }
}
