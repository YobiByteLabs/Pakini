package mx.unam.battlehack.paquini.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by jagspage2013 on 27/09/14.
 */
public class TratadorDeImagenes {

    public static Bitmap fromByteArray(byte[] datos){
        return BitmapFactory.decodeByteArray(datos,0,datos.length);
    }
}
