package mx.unam.battlehack.paquini.controlador;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import mx.unam.battlehack.paquini.R;
import mx.unam.battlehack.paquini.util.ServerUtilities;

public class RegisterActivity extends ActionBarActivity implements View.OnClickListener {

    private EditText edit_nombre;
    private EditText edit_correo;
    private EditText edit_pass;
    private EditText edit_con_pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edit_nombre = (EditText) findViewById(R.id.edit_nombre);
        edit_correo = (EditText) findViewById(R.id.edit_correo);
        edit_pass = (EditText) findViewById(R.id.edit_pass);
        edit_con_pass = (EditText) findViewById(R.id.edit_con_pass);

        Button btn_enviar = (Button) findViewById(R.id.btn_enviar);
        btn_enviar.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_enviar) {
            if (validar()) {
                ServerUtilities.Registrar registro = new ServerUtilities.Registrar(this);
                registro.execute(edit_nombre.getText().toString(),
                        edit_correo.getText().toString(),
                        edit_pass.getText().toString());
            }
        }
    }

    private boolean validar() {

        if (edit_nombre.getText().length() < 8) {
            Toast.makeText(this, "El usuario debe ser mayor a 8 caracteres", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(edit_correo.getText().toString()).matches()) {
            Toast.makeText(this, "El correo es invalido", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!edit_pass.getText().toString().equals(edit_con_pass.getText().toString())) {
            Toast.makeText(this, "Las contraseñas deben coincidir", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (edit_pass.getText().toString().length() > 8) {
            Toast.makeText(this, "La contraseña debe ser mayor a 8 caracteres ", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
