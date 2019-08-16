package com.marlonncarvalhosa.mamaeeuquero.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.marlonncarvalhosa.mamaeeuquero.R;
import com.marlonncarvalhosa.mamaeeuquero.model.Imagem;

import java.util.List;

public class ImagensAdapter extends PagerAdapter {
    private List<Imagem> imagems;
    private Context context;
    LayoutInflater inflater;

    public ImagensAdapter(Context context, List<Imagem> imagems) {
        this.context = context;
        this.imagems= imagems;
    }

    @Override
    public int getCount() {
        return imagems.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view== object);
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {


        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.slide,container,false);

        Imagem imagem = imagems.get(position);
        FrameLayout layoutslide = view.findViewById(R.id.slidelinearlayout);
        ImageView imgslide = view.findViewById(R.id.imageView);
        TextView txttitle= view.findViewById(R.id.txttitle);


        if(position!=imagems.size()-1){
            txttitle.setText("Próxima Imagem >");
        }else {
            txttitle.setText("< Imagem Anterior");
        }

        layoutslide.setBackgroundColor(Color.rgb(55,55,55));
        Glide.with(context).load(imagem.getUrl()).into(imgslide);

        container.addView(view);
        return view;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((FrameLayout)object);
    }

}
