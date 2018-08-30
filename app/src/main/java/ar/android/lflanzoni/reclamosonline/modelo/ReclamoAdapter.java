package ar.android.lflanzoni.reclamosonline.modelo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import ar.android.lflanzoni.reclamosonline.R;

public class ReclamoAdapter extends ArrayAdapter<Reclamo> {
    private Context context;
    private List<Reclamo> listaReclamos;


    public ReclamoAdapter(Context ctx, List<Reclamo> lista){
        super(ctx, 0 , lista);
        this.context = ctx;
        this.listaReclamos = lista;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View fila =convertView;
        if(fila==null) {
            fila =  LayoutInflater.from(this.context).inflate(R.layout.fila_reclamos, parent, false);
        }
        final Reclamo reclamo = this.listaReclamos.get(position);

        TextView tvMailContacto = (TextView) fila.findViewById(R.id.fila_tvMail);
        TextView tvDescripcion = (TextView) fila.findViewById(R.id.fila_tvDescripcion);
        CheckBox cbEstado = (CheckBox) fila.findViewById(R.id.fila_cbEstado);
        tvMailContacto.setText(reclamo.getMailContacto());
        tvDescripcion.setText(reclamo.getDescripcion());
        if(reclamo.getResuelto()){
            cbEstado.setChecked(true);
        }else{
            cbEstado.setChecked(false);
        }
        return fila;
    }
}


