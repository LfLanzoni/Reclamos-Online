package ar.android.lflanzoni.reclamosonline.modelo;

import java.util.List;

public interface ReclamoDAO {
    public void agregar (Reclamo r);
    public void eliminar (Reclamo r);
    public void actualizar (Reclamo r);
    public Reclamo buscarReclamo (int id);
    public List<Reclamo>listarReclamos();

}
