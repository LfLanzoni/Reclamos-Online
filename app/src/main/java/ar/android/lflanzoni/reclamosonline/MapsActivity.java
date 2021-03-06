package ar.android.lflanzoni.reclamosonline;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import ar.android.lflanzoni.reclamosonline.modelo.Reclamo;
import ar.android.lflanzoni.reclamosonline.modelo.ReclamoDAO;
import ar.android.lflanzoni.reclamosonline.modelo.ReclamoDAOsql;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int REQUEST_CREAR_RECLAMO = 777 ,REQUEST_ACTUALIZAR_MAPA=778;
    private GoogleMap mMap;
    private ReclamoDAO reclamoDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        reclamoDAO = new ReclamoDAOsql(this);
        //VER DE CARGAR LOS MARKER EN EL MAPA
        // RECORDA EN CAMBIO DE ESTADO CAMBIA EL MARKER DESDE LISTA RECLAMO
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.reclamos_menu,menu);
        return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == REQUEST_CREAR_RECLAMO && resultCode == RESULT_OK) {
            String reclamoJson = data.getStringExtra("result");
            Log.d("LAB_RECLAMO",reclamoJson);
            Reclamo r =new Reclamo();
            try {
                r.loadFromJson(new JSONObject(reclamoJson));
            } catch (JSONException e) {
                e.printStackTrace();
            };
            float color = r.getResuelto()?
                    BitmapDescriptorFactory.HUE_BLUE:BitmapDescriptorFactory.HUE_RED;
                    mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(r.getUbicacion().latitude,r.getUbicacion().longitude))
                    .title(r.getMailContacto())
                    .snippet(r.getDescripcion())
                    .icon(BitmapDescriptorFactory.defaultMarker(color)));
        }
        if(requestCode == REQUEST_ACTUALIZAR_MAPA && resultCode == RESULT_OK){
            cargarReclamos();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        //LOAD DE RECLAMOS
        cargarReclamos();

        //LLENAR MAPA DE MARKER CON RECLAMOS CARGADOS DE LA DB



        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                Intent nuevoReclamoIntent = new
                        Intent(MapsActivity.this,NuevoReclamoActivity.class);
                nuevoReclamoIntent.putExtra("punto",latLng);
                startActivityForResult(nuevoReclamoIntent,REQUEST_CREAR_RECLAMO);
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuListaReclamos:
                Toast.makeText(this,"MENU SELECCIONADO ",Toast.LENGTH_LONG).show();
                Intent intentListaReclamos = new Intent(this,ListaReclamosActivity.class);
                startActivityForResult(intentListaReclamos,REQUEST_ACTUALIZAR_MAPA);
                return true;
            default:
                return super.onContextItemSelected(item);
        }

    }

    public void cargarReclamos(){
        float color ;
        for (Reclamo reclamo:reclamoDAO.listarReclamos()) {
            color = reclamo.getResuelto()?
                    BitmapDescriptorFactory.HUE_BLUE:BitmapDescriptorFactory.HUE_RED;
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(reclamo.getUbicacion().latitude,reclamo.getUbicacion().longitude))
                    .title(reclamo.getMailContacto())
                    .snippet(reclamo.getDescripcion())
                    .icon(BitmapDescriptorFactory.defaultMarker(color)));
        }
    }
}
