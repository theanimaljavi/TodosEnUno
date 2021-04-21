package com.javigu.todosenuno.Ahorcado;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.javigu.todosenuno.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GestionFotos {
    public static ArrayList<Foto> FOTOS = new ArrayList<Foto>();

    void cargarFotos(Context c) {
        InputStream raw = c.getResources().openRawResource(R.raw.url_imagenes_ahorcado);
        Reader rd = new BufferedReader(new InputStreamReader(raw));
        Type listType = new TypeToken<List<Foto>>() {
        }.getType();
        Gson gson = new Gson();
        FOTOS = gson.fromJson(rd, listType);
    }

    public class Foto {
        String url_image;

        public Foto(String url_image) {
            this.url_image = url_image;
        }

        public String getUrl() {
            return url_image;
        }

        public void setUrl(String url_image) {
            this.url_image = url_image;
        }
    }
}
