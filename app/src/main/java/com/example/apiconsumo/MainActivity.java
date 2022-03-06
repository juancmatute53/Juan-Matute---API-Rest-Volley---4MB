package com.example.apiconsumo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    //referencia para mi campo de texto
    private TextView tv1;
    //objeto requesqueue
    private RequestQueue rq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1=findViewById(R.id.tv1);
        //efecto de scroll
        tv1.setMovementMethod(new ScrollingMovementMethod());
        //llamo mi requesqueue de la liberia de volley
        rq= Volley.newRequestQueue(this);
    }

    public  void recuperar(View v){
        tv1.setText("");
        //direccion del servidor para acceder a los datos
        String url="http://scratchya.com.ar/videosandroidjava/volley/listar.php";
        //arreglo para los archivos JSON
        //el metdo get me ayuda a la petidion de datos
        JsonArrayRequest requerimiento=new JsonArrayRequest(Request.Method.GET,
                //url a quien le vamos a pedir la informacion
                url,
                //null me sirve para aclacar que no se va a enviar datos
                null,
                //implementa la interfaz listener del objeto response
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //me devuleve la cantidad de objetos del JSON
                        for (int f=0;f<response.length();f++){
                            try {
                                //recorre y mustra los datos en el textView
                                JSONObject objeto = new JSONObject(response.get(f).toString());
                                //muestran los datos requeridos en el textView
                                tv1.append("Codigo:"+objeto.getString("codigo")+"\n");
                                tv1.append("Descripcion:"+objeto.getString("descripcion")+"\n");
                                tv1.append("Precio:"+objeto.getString("precio")+"\n");
                                tv1.append("\n");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                //objeto de la interfaz listener que me mostrara si hay un error
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //me muestra un error de seguridad por no entrar a dominios seguros
                        Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
        //metodo del requerimiento
        rq.add(requerimiento);
    }
}