package mx.unam.battlehack.paquini.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

import mx.unam.battlehack.paquini.objetos.Constants;

/**
 * Created by jagspage2013 on 27/09/14.
 */
public class ServerUtilities {

    public static class LogIn extends AsyncTask<String, String, String> {

        ProgressDialog dialog;
        Context context;

        public LogIn(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(context);
            dialog.setMessage("Iniciando Sesión");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String response = "";
            try{
                HttpParams httpParameters = new BasicHttpParams();
                int timeoutConnection =5000;
                int timeoutSocket = 5000;
                HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
                HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

                HttpClient httpClient = new DefaultHttpClient(httpParameters);
                HttpPost httpPost = new HttpPost("http://pakini.hol.es/login.php");
                List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(3);
                nameValuePair.add(new BasicNameValuePair("mail",strings[0]));
                nameValuePair.add(new BasicNameValuePair("pass",strings[1]));
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
                HttpResponse resp = httpClient.execute(httpPost);
                httpPost.setHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
                response =  EntityUtils.toString(resp.getEntity());
                Log.d("Http Log In Resp:",  response);
            } catch (Exception e) {
                Log.d("Error", "Error al enviar datos");
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d(Constants.TAG,"Respuesta server: "+s);
            Constants.setId(context,s);
            if(!s.equals("-1"))
                Toast.makeText(context,"Bienvenido",Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }
    }

    public static class Registrar extends AsyncTask<String, String, String> {

        ProgressDialog dialog;
        Context context;

        public Registrar(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(context);
            dialog.setMessage("Registrandote");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String response = "";
            try{
                HttpParams httpParameters = new BasicHttpParams();
                int timeoutConnection =15000;
                int timeoutSocket = 15000;
                HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
                HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

                HttpClient httpClient = new DefaultHttpClient(httpParameters);
                HttpPost httpPost = new HttpPost("http://pakini.hol.es/register.php");
                List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(3);
                nameValuePair.add(new BasicNameValuePair("user",strings[0]));
                nameValuePair.add(new BasicNameValuePair("mail",strings[1]));
                nameValuePair.add(new BasicNameValuePair("pass",strings[2]));
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
                HttpResponse resp = httpClient.execute(httpPost);
                httpPost.setHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
                response =  EntityUtils.toString(resp.getEntity());
                Log.d("Http Response:",  response.toString());
            } catch (Exception e) {
                Log.d("Error", "Error al enviar datos");
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d(Constants.TAG,"Respuesta server: "+s);
            dialog.dismiss();
        }
    }

    public static class Transaccion extends AsyncTask<String, String, String> {

        ProgressDialog dialog;
        Context context;

        public Transaccion(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(context);
            dialog.setMessage("Realizando Transacción");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String response = "";
            try{
                HttpParams httpParameters = new BasicHttpParams();
                int timeoutConnection =15000;
                int timeoutSocket = 15000;
                HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
                HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

                HttpClient httpClient = new DefaultHttpClient(httpParameters);
                HttpPost httpPost = new HttpPost("http://pakini.hol.es/buy.php");
                List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
                nameValuePair.add(new BasicNameValuePair("user",strings[0]));
                nameValuePair.add(new BasicNameValuePair("cupon",strings[1]));

                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
                HttpResponse resp = httpClient.execute(httpPost);
                httpPost.setHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
                response =  EntityUtils.toString(resp.getEntity());
                Log.d("Http Response:",  response);
            } catch (Exception e) {
                Log.d("Error", "Error al enviar datos");
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d(Constants.TAG,"Respuesta server: "+s);
            Toast.makeText(context,"Cupon Adquirido",Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }
    }

    public static class ObtenerCupones extends AsyncTask<String, String, String> {

        ProgressDialog dialog;
        Context context;

        public ObtenerCupones(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(context);
            dialog.setMessage("Actualizando Cupones");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String response = "";
            try{
                HttpParams httpParameters = new BasicHttpParams();
                int timeoutConnection =15000;
                int timeoutSocket = 15000;
                HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
                HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

                HttpClient httpClient = new DefaultHttpClient(httpParameters);
                HttpPost httpPost = new HttpPost("http://pakini.hol.es/don.php");
                List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
                nameValuePair.add(new BasicNameValuePair("id",strings[0]));
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
                HttpResponse resp = httpClient.execute(httpPost);
                httpPost.setHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
                response =  EntityUtils.toString(resp.getEntity());
                Log.d("Http Response:",  response);
            } catch (Exception e) {
                Log.d("Error", "Error al enviar datos");
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d(Constants.TAG,"Respuesta server: "+s);
            dialog.dismiss();
        }


    }

    public static class Ayudar extends AsyncTask<String, String, String> {

        ProgressDialog dialog;
        Context context;

        public Ayudar(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(context);
            dialog.setMessage("Actualizando Cupones");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String response = "";
            try{
                HttpParams httpParameters = new BasicHttpParams();
                int timeoutConnection =5000;
                int timeoutSocket = 5000;
                HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
                HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

                HttpClient httpClient = new DefaultHttpClient(httpParameters);
                HttpPost httpPost = new HttpPost("http://pakini.hol.es/donap.php");
                List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
                nameValuePair.add(new BasicNameValuePair("user",strings[0]));
                nameValuePair.add(new BasicNameValuePair("ong",strings[1]));
                nameValuePair.add(new BasicNameValuePair("mon",strings[2]));
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
                HttpResponse resp = httpClient.execute(httpPost);
                httpPost.setHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
                response =  EntityUtils.toString(resp.getEntity());
                Log.d("Http Response:",  response);
            } catch (Exception e) {
                Log.d("Error", "Error al enviar datos");
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d(Constants.TAG,"Respuesta server donacion: "+s);
            Toast.makeText(context," Muchas Gracias!",Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }
    }
}
