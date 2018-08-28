package ar.android.lflanzoni.reclamosonline;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import ar.android.lflanzoni.reclamosonline.modelo.Reclamo;

public class NuevoReclamoActivity extends AppCompatActivity {
    Button btnTomarFoto,btnCancelar,btnHacerReclamo;
    ImageView imgFoto;
    Reclamo reclamo;
    EditText etMail,etDescripcion;
    private static final int REQUEST_IMAGE_CAPTURE = 888;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_reclamo);
        btnTomarFoto=(Button) findViewById(R.id.btnSubirFoto);
        btnCancelar=(Button) findViewById(R.id.btnCancelar);
        btnHacerReclamo=(Button) findViewById(R.id.btnHacerReclamo);
        imgFoto= (ImageView) findViewById(R.id.ivFoto);
        etDescripcion = (EditText) findViewById(R.id.etDescripcion);
        etMail = (EditText) findViewById(R.id.etMail);
        reclamo = new Reclamo();
        reclamo.setUbicacion((LatLng)getIntent().getParcelableExtra("punto"));




        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //VER Q HACER
                finish();

            }
        });

        btnTomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new
                        Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });

        btnHacerReclamo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new
                        Intent(NuevoReclamoActivity.this,MapsActivity.class);
                reclamo.setMailContacto(etMail.getText().toString());
                reclamo.setDescripcion(etDescripcion.getText().toString());
                Log.d("MALDITO",reclamo.toString());
                String dato = reclamo.toJson().toString();
                returnIntent.putExtra("result",dato);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imgFoto.setImageBitmap(imageBitmap);
            imgFoto.setVisibility(View.VISIBLE);
            this.reclamo.setPathImagen( this.saveToInternalStorage(imageBitmap));
        }
    }
    private String saveToInternalStorage(Bitmap bitmapImage){
    // ContextWrapper cw = new ContextWrapper(getApplicationContext());
    // path to /data/data/yourapp/app_data/imageDir
        File directory = getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,"profile.jpg");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

}
