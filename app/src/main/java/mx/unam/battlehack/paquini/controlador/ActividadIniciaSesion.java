package mx.unam.battlehack.paquini.controlador;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import mx.unam.battlehack.paquini.R;
import mx.unam.battlehack.paquini.objetos.ConnectionDetector;
import mx.unam.battlehack.paquini.objetos.Constants;
import mx.unam.battlehack.paquini.util.ServerUtilities;


public class ActividadIniciaSesion extends ActionBarActivity implements View.OnClickListener {

    private UiLifecycleHelper uiHelper;
    private EditText edit_correo_init;
    private EditText edit_pass_init;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicia_sesion);

        uiHelper = new UiLifecycleHelper(this,callback);
        uiHelper.onCreate(savedInstanceState);

        if (!Constants.getName(this).equals("")) {
            goToActivity();
        }

        if (!(ConnectionDetector.isConnectedToInternet(this))) {
            Toast.makeText(this,"No estás Conectado a Internet",Toast.LENGTH_SHORT).show();
        }

        findViewById(R.id.lbl_registro).setOnClickListener(this);
        findViewById(R.id.btn_inicia_sesion).setOnClickListener(this);

        LoginButton authButton = (LoginButton) findViewById(R.id.login_fb);
        authButton.setReadPermissions(Arrays.asList("public_profile","email"));
        authButton.setSessionStatusCallback(callback);

        edit_correo_init = (EditText)findViewById(R.id.edit_correo_init);
        edit_pass_init = (EditText)findViewById(R.id.edit_pass_init);
    }

    private boolean validar(){

        if(!Patterns.EMAIL_ADDRESS.matcher(edit_correo_init.getText().toString()).matches()) {
            Toast.makeText(this, "El correo es invalido", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(edit_pass_init.getText().toString().length() >8 ){
            Toast.makeText(this,"La contraseña debe ser mayor a 8 caracteres ",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session,state,exception);
        }
    };

    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        if (session != null && state.isOpened()) {
            Log.i(Constants.TAG, "Logged in...");
            if (Constants.getName(this).equals("")) {
                Session.setActiveSession(session);
                makeARequest(session);
            } else {
                if(!Constants.getName(this).equals(""));
            }

        } else if (state.isClosed()) {
            Log.i(Constants.TAG, "Logged out...");
            Constants.setName(this, "");
        }
    }

    private void makeARequest(final Session session) {

        Request request = Request.newMeRequest(session, new Request.GraphUserCallback() {
            @Override
            public void onCompleted(GraphUser user, Response response) {
                if (session == Session.getActiveSession()) {
                    if (user != null) {
                        String nombre = user.getFirstName() +" "+ user.getMiddleName() +" "+ user.getLastName();
                        String email = user.getProperty("email").toString();
                        Log.d(Constants.TAG, "EL USUARIO ES : " + nombre);
                        Constants.setName(getApplicationContext(), nombre);
                        Constants.setEmail(getApplicationContext(), email);
                        try {
                            registrar(nombre,email);
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if (response.getError() != null) {
                        Log.d(Constants.TAG, "EL Error ES : " + response.getRawResponse());
                    }
                }
            }
        });
        request.executeAsync();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);

    }

    @Override
    protected void onResume() {
        super.onResume();
        uiHelper.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
    }

    private void goToActivity() {
        startActivity(new Intent(ActividadIniciaSesion.this,ActivityMain.class).
                setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.lbl_registro){
            startActivity(new Intent(ActividadIniciaSesion.this,RegisterActivity.class));
        }else
        if(view.getId() == R.id.btn_inicia_sesion){
            if(validar()){
                try {
                    logIn(edit_correo_init.getText().toString(), edit_pass_init.getText().toString());
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void registrar(String nombre, String correo) throws ExecutionException, InterruptedException {
        ServerUtilities.Registrar logIn = new ServerUtilities.Registrar(ActividadIniciaSesion.this);
        logIn.execute(nombre,correo,"");
        if(true){
            goToActivity();
        }else{
            Session.getActiveSession().close();
        }
    }

    private void logIn(String correo, String pass) throws ExecutionException, InterruptedException {
      ServerUtilities.LogIn logIn =  new ServerUtilities.LogIn(ActividadIniciaSesion.this);
      logIn.execute(correo, pass);

    }
}
