package mx.unam.battlehack.paquini.adaptador;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import mx.unam.battlehack.paquini.R;
import mx.unam.battlehack.paquini.controlador.ActivityComprarCupon;
import mx.unam.battlehack.paquini.objetos.Cupon;

public class CuponesAdapter extends RecyclerView.Adapter<CuponesAdapter.ViewHolder>{

    private List<Cupon> items;
    private Context contexto;

    public CuponesAdapter(List<Cupon> items, Context contexto){
        this.contexto = contexto;
        this.items = items;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView img_cupon;
        public TextView lbl_nombre_cupon;
        public TextView lbl_descripcion_cupon;
        public TextView lbl_costo_cupon;
        public Button btn_comprar;
        public ViewHolder(View v) {
            super(v);
            img_cupon = (ImageView)v.findViewById(R.id.img_cupon);
            lbl_nombre_cupon= (TextView)v.findViewById(R.id.lbl_nombre_cupon);
            lbl_descripcion_cupon= (TextView)v.findViewById(R.id.lbl_descripcion_cupon);
            lbl_costo_cupon= (TextView)v.findViewById(R.id.lbl_costo_cupon);
            btn_comprar= (Button)v.findViewById(R.id.btn_comprar);
        }
    }
    @Override
    public CuponesAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_cupones,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CuponesAdapter.ViewHolder viewHolder, int i) {
        final Cupon cupon = this.items.get(i);
        viewHolder.img_cupon.setImageBitmap(cupon.getLogo());
        viewHolder.lbl_nombre_cupon.setText(cupon.getNombre());
        viewHolder.lbl_descripcion_cupon.setText(cupon.getDescripcion());
        viewHolder.lbl_costo_cupon.setText(cupon.getPuntos() + "");
        viewHolder.btn_comprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irACompra(cupon);
            }
        });

    }

    private void irACompra(Cupon cupon) {
        Intent intent = new Intent(contexto, ActivityComprarCupon.class);
        intent.putExtra("id",cupon.getId_cupones());
        intent.putExtra("nombre",cupon.getNombre());
        intent.putExtra("descripcion",cupon.getDescripcion());
        intent.putExtra("puntos",cupon.getPuntos());
        intent.putExtra("empresa",cupon.getEmpresa());
        contexto.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
