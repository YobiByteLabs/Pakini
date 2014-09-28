package mx.unam.battlehack.paquini.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

import mx.unam.battlehack.paquini.R;
import mx.unam.battlehack.paquini.objetos.Constants;
import mx.unam.battlehack.paquini.objetos.Cupon;

/**
 * Created by jagspage2013 on 27/09/14.
 */
public class ManejadorListas {

    public static ArrayList<Cupon> cupones;

    public static void init(Context context){
        cupones = new ArrayList<Cupon>();
        //obtenerCupones(context);

        addCupon(0,"Cupon 1","Descripción de la cerveza",BitmapFactory.decodeResource(context.getResources(),R.drawable.cuponcafe),"Empresa ",120);
        addCupon(1,"Cupon 2","Descripción del café",BitmapFactory.decodeResource(context.getResources(),R.drawable.cuponcafe),"Empresa ",220);
        addCupon(2,"Cupon 3","Descripción 1",BitmapFactory.decodeResource(context.getResources(),R.drawable.cuponhelado),"Empresa ",80);
        addCupon(3,"Cupon 4","Descripción 1",BitmapFactory.decodeResource(context.getResources(),R.drawable.cuponpanque),"Empresa ",120);
        addCupon(4,"Cupon 5","Descripción 1",BitmapFactory.decodeResource(context.getResources(),R.drawable.cuponpizza),"Empresa ",30);
        addCupon(5,"Cupon 6","Descripción 1",BitmapFactory.decodeResource(context.getResources(),R.drawable.cuponrefresco),"Empresa ",320);
        addCupon(6,"Cupon 7","Descripción 1",BitmapFactory.decodeResource(context.getResources(),R.drawable.cuponpizza),"Empresa ",90);
        addCupon(7,"Cupon 8","Descripción 1",BitmapFactory.decodeResource(context.getResources(),R.drawable.cuponcafe),"Empresa ",160);
        addCupon(8,"Cupon 9","Descripción 1",BitmapFactory.decodeResource(context.getResources(),R.drawable.cuponcafe),"Empresa ",140);
        addCupon(9,"Cupon 10","Descripción 1",BitmapFactory.decodeResource(context.getResources(),R.drawable.cuponcerveza),"Empresa ",200);
        addCupon(10,"Cupon 11","Descripción 1",BitmapFactory.decodeResource(context.getResources(),R.drawable.cuponpizza),"Empresa ",250);


    }

    private static void obtenerCupones(Context context) {
        ServerUtilities.ObtenerCupones obten = new ServerUtilities.ObtenerCupones(context);
        obten.execute(String.valueOf(Constants.getId(context)));
        String resp ="";
        try {
            resp= obten.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Log.d(Constants.TAG,"Cupones "+ resp);
    }

    public static void addCupon(int id,String nombre,String descripcion,byte[] data_img,String empresa,double puntos){
        cupones.add(new Cupon(id,nombre,descripcion,TratadorDeImagenes.fromByteArray(data_img),empresa,puntos));
    }

    public static void addCupon(int id,String nombre,String descripcion,Bitmap data_img,String empresa,double puntos){
        cupones.add(new Cupon(id,nombre,descripcion,data_img,empresa,puntos));
    }

    public static String getById(int id){
        Cupon cupon = null;
        Iterator<Cupon> iter = cupones.iterator();

        while(iter.hasNext()){
            cupon = iter.next();
            if(cupon.getId_cupones()==id)
                return String.valueOf(cupon.getId_cupones());
        }
        return "-1";
    }

}
