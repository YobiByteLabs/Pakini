package mx.unam.battlehack.paquini.controlador;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

import mx.unam.battlehack.paquini.R;
import mx.unam.battlehack.paquini.objetos.Constants;
import mx.unam.battlehack.paquini.objetos.Cupon;
import mx.unam.battlehack.paquini.util.ManejadorListas;
import mx.unam.battlehack.paquini.util.ServerUtilities;

public class ActivityComprarCupon extends ActionBarActivity {


    private String nombre, descripcion, puntos, empresa;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comprar_cupon);

        id = getIntent().getIntExtra("id", 0);
        nombre = getIntent().getStringExtra("nombre");
        descripcion = getIntent().getStringExtra("descripcion");
        puntos = String.valueOf(getIntent().getDoubleExtra("puntos",-1.0));
        empresa = getIntent().getStringExtra("empresa");

        ImageView image = (ImageView)findViewById(R.id.img_cupon_compra);
        image.setImageBitmap(ManejadorListas.cupones.get(id).getLogo());

        ((TextView)findViewById(R.id.lbl_nombre_cupon)).setText(nombre);
        ((TextView)findViewById(R.id.lbl_descripcion_cupon)).setText(descripcion);
        ((TextView)findViewById(R.id.lbl_puntos)).setText(puntos);
        ((TextView)findViewById(R.id.lbl_empresa_cupon)).setText(empresa);
        ((Button)findViewById(R.id.btn_pagar)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(id!=-1)
                    realizarTransaccion(String.valueOf(Constants.getId(getApplicationContext())), String.valueOf(id),puntos);
            }
        });

    }

    private void realizarTransaccion(String id, String email, String puntos) {
        ServerUtilities.Transaccion trans = new ServerUtilities.Transaccion(this);
        trans.execute(id,email,puntos);

    }


}
