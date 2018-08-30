package ar.android.lflanzoni.reclamosonline;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import ar.android.lflanzoni.reclamosonline.modelo.Reclamo;
import ar.android.lflanzoni.reclamosonline.modelo.ReclamoAdapter;
import ar.android.lflanzoni.reclamosonline.modelo.ReclamoDAO;
import ar.android.lflanzoni.reclamosonline.modelo.ReclamoDAOsql;

public class ListaReclamosActivity extends AppCompatActivity {
    ReclamoDAO reclamoDAO;
    ListView listaReclamos;
    ArrayAdapter<Reclamo> adaptadorReclamos;
    Button btnVerMapa ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_reclamos);
        listaReclamos = (ListView) findViewById(R.id.listaReclamos);
        btnVerMapa = (Button) findViewById(R.id.btnVerMapa);

        reclamoDAO = new ReclamoDAOsql(this);
        adaptadorReclamos = new ReclamoAdapter(this,reclamoDAO.listarReclamos());
        listaReclamos.setAdapter(adaptadorReclamos);


        btnVerMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);
                finish();
            }
        });




        this.listaReclamos.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                int itemPosition = position;
                Reclamo itemValue = (Reclamo) listaReclamos.getItemAtPosition(position);
                itemValue.setResuelto(true);
                reclamoDAO.actualizar(itemValue);
                adaptadorReclamos.notifyDataSetChanged();
                return false;
            }
        });


    }
}
