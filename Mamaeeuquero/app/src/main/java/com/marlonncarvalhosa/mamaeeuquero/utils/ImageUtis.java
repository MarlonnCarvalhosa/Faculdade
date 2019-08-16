package com.marlonncarvalhosa.mamaeeuquero.utils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class ImageUtis {

    public static byte[] imageViewParaBytes(ImageView imageView) {
        imageView.setDrawingCacheEnabled(true);
        Bitmap bitmap = imageView.getDrawingCache();
        ByteArrayOutputStream baos =new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        imageView.setDrawingCacheEnabled(false);
        return  baos.toByteArray();


    }
}