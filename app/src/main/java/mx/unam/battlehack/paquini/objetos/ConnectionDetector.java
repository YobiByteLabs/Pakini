package mx.unam.battlehack.paquini.objetos;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by jagspage2013 on 21/07/14.
 */
//clase que se encargara de detetectar los posibles cambios en la red
public class ConnectionDetector {

    public static boolean isConnectedToInternet(Context _context){
        ConnectivityManager connectivityManager = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager !=null){
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();//se usara para saber el estado de la red

            if(networkInfo!=null){
                for(int i = 0; i<networkInfo.length;i++){
                    if(networkInfo[i].getState() == NetworkInfo.State.CONNECTED ){//si se esta conectado regresara un true
                        return true;
                    }
                }
            }
        }
        return false;//en caso de que no se tenga acceso a la red se regresa un false
    }
}
