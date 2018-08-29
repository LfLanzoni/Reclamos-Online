package ar.android.lflanzoni.reclamosonline.modelo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ReclamosOpenHelper extends SQLiteOpenHelper {
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "reclamo.db";

        public ReclamosOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            Log.d("CREANDO","TABLAS ADENTRO");
            db.execSQL("CREATE TABLE RECLAMO ( _id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, MAIL TEXT, DESCRIPCION TEXT,LATITUDE DOUBLE,LONGITUDE DOUBLE,RESUELTO INTEGER,PATH_IMAGE TEXT) ");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int old_version, int new_version) {
         }
}






