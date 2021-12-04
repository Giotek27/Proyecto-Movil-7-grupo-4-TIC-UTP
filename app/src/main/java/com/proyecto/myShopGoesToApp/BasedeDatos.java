package com.proyecto.myShopGoesToApp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BasedeDatos extends SQLiteOpenHelper {

    public BasedeDatos(Context context, String nombre, SQLiteDatabase.CursorFactory f, int version){
        super(context, nombre, f, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE productos(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nomproduct TEXT, cantidad INTEGER, precio INTEGER,catproduct TEXT," +
                "descriproduct TEXT, garanproduct TEXT, imagenproducto BLOB)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int verAnterior, int verNueva){
        db.execSQL("DROP TABLE IF EXISTS productos");
        db.execSQL("CREATE TABLE productos(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nomproduct TEXT, cantidad INTEGER, precio INTEGER,catproduct TEXT," +
                "descriproduct TEXT, garanproduct TEXT, imagenproducto BLOB)");
    }
}
