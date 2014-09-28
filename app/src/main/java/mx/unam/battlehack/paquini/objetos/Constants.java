package mx.unam.battlehack.paquini.objetos;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by jagspage2013 on 27/09/14.
 */
public class Constants {

    public static final String TAG = "Paquini";
    public static int CURRENT_F ;


    public static void setName(Context context, String name) {
        SharedPreferences.Editor edit = context.getSharedPreferences(Constants.TAG, Context.MODE_PRIVATE).edit();
        edit.putString("nombre", name);
        Log.d(TAG, "Nuevo nombre:" + name);
        edit.commit();
    }

    public static String getName(Context context) {
        return context.getSharedPreferences(Constants.TAG, Context.MODE_PRIVATE).getString("nombre", "");
    }

    public static void setEmail(Context context, String email) {
        SharedPreferences.Editor edit = context.getSharedPreferences(Constants.TAG, Context.MODE_PRIVATE).edit();
        edit.putString("email", email);
        Log.d(TAG, "Nuevo email:" + email);
        edit.commit();
    }

    public static String getEmail(Context context) {
        return context.getSharedPreferences(Constants.TAG, Context.MODE_PRIVATE).getString("email", "");
    }

    public static String getId(Context context) {
        return context.getSharedPreferences(Constants.TAG, Context.MODE_PRIVATE).getString("id", "0");
    }

    public static void setId(Context context, String id) {
        SharedPreferences.Editor edit = context.getSharedPreferences(Constants.TAG, Context.MODE_PRIVATE).edit();
        edit.putString("id",id );
        edit.commit();
        Log.d(TAG, "Nuevo ID:" + id);

    }
}
