package ar.android.lflanzoni.reclamosonline.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class ReclamoDAOsql implements ReclamoDAO {

    public ReclamoDAOsql(Context ctx){
        DatabaseManager.initializeInstance(new ReclamosOpenHelper(ctx));

    }



    @Override
    public void agregar(Reclamo reclamo) {
        SQLiteDatabase dbConn = DatabaseManager.getInstance().openDatabase();
        try {
            dbConn.beginTransaction();
            ContentValues cvReclamo = new ContentValues();
            cvReclamo.put("MAIL",reclamo.getMailContacto());
            cvReclamo.put("DESCRIPCION",reclamo.getDescripcion());
            cvReclamo.put("LATITUDE",reclamo.getUbicacion().latitude);
            cvReclamo.put("LONGITUDE",reclamo.getUbicacion().longitude);
            cvReclamo.put("PATH_IMAGE",reclamo.getPathImagen());
            cvReclamo.put("RESUELTO",reclamo.getResuelto()? 1:0);
            dbConn.insert("RECLAMO", "_id", cvReclamo);
            dbConn.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbConn.endTransaction();
        }
    }

    @Override
    public void eliminar(Reclamo reclamo) {
        SQLiteDatabase dbConn = DatabaseManager.getInstance().openDatabase();
        try {
            dbConn.beginTransaction();
            dbConn.delete("RECLAMO", "_id="+reclamo.getId(),null);
            dbConn.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbConn.endTransaction();
        }
    }



    @Override
    public void actualizar(Reclamo reclamo) {
        SQLiteDatabase dbConn = DatabaseManager.getInstance().openDatabase();
        try {
            dbConn.beginTransaction();
            ContentValues cvReclamo = new ContentValues();
            cvReclamo.put("MAIL",reclamo.getMailContacto());
            cvReclamo.put("DESCRIPCION",reclamo.getDescripcion());
            cvReclamo.put("LATITUDE",reclamo.getUbicacion().latitude);
            cvReclamo.put("LONGITUDE",reclamo.getUbicacion().longitude);
            cvReclamo.put("PATH_IMAGE",reclamo.getPathImagen());
            cvReclamo.put("RESUELTO",reclamo.getResuelto()? 1:0);

            dbConn.update("RECLAMO",  cvReclamo,"_id="+reclamo.getId(),null);
            dbConn.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbConn.endTransaction();
        }
    }


    @Override
    public Reclamo buscarReclamo(int id) {
        List<Reclamo> resultado = new ArrayList<>();
        SQLiteDatabase dbConn = DatabaseManager.getInstance().openDatabase();
        Cursor cursor = dbConn.rawQuery("SELECT _ID,MAIL,DESCRIPCION,LATITUDE,LONGITUDE,PAHT_IMAGE,RESUELTO from RECLAMO ", null);
        Reclamo aux = new Reclamo();
        if(cursor.getCount()==0){
            DatabaseManager.getInstance().closeDatabase();
            return null;
        }
        else {
            while (cursor.moveToNext()) {
                aux.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                aux.setMailContacto(cursor.getString(cursor.getColumnIndex("MAIL")));
                aux.setDescripcion(cursor.getString(cursor.getColumnIndex("DESCRIPCION")));
                Double latitude = cursor.getDouble(cursor.getColumnIndex("LATITUDE"));
                Double longitude = cursor.getDouble(cursor.getColumnIndex("LONGITUDE"));
                aux.setUbicacion(new LatLng(latitude, longitude));
                aux.setPathImagen(cursor.getString(cursor.getColumnIndex("PATH_IMAGE")));
                aux.setResuelto(cursor.getInt(cursor.getColumnIndex("RESUELTO")) > 0);
            }
            DatabaseManager.getInstance().closeDatabase();
            return aux;
        }
    }



    @Override
    public List<Reclamo> listarReclamos() {
        List<Reclamo> resultado = new ArrayList<>();
        SQLiteDatabase dbConn = DatabaseManager.getInstance().openDatabase();
        Cursor cursor = dbConn.rawQuery("SELECT _ID,MAIL,DESCRIPCION,LATITUDE,LONGITUDE,RESUELTO,PATH_IMAGE from  RECLAMO ", null);
        while (cursor.moveToNext()) {
            Reclamo aux = new Reclamo();
            aux.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            aux.setMailContacto(cursor.getString(cursor.getColumnIndex("MAIL")));
            aux.setDescripcion(cursor.getString(cursor.getColumnIndex("DESCRIPCION")));
            Double latitude = cursor.getDouble(cursor.getColumnIndex("LATITUDE"));
            Double longitude = cursor.getDouble(cursor.getColumnIndex("LONGITUDE"));
            aux.setUbicacion(new LatLng(latitude, longitude));
            aux.setPathImagen(cursor.getString(cursor.getColumnIndex("PATH_IMAGE")));
            aux.setResuelto(cursor.getInt(cursor.getColumnIndex("RESUELTO")) > 0);
            resultado.add(aux);
        }
        DatabaseManager.getInstance().closeDatabase();
        return resultado;
    }


}

