package com.example.jcesa.chilakillers;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class MascotaActivity extends AppCompatActivity {

    public int mascotaId = 0;
    public Mascota mMascota = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mascota);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle b = getIntent().getExtras();
        int id = b.getInt("id");

        mascotaId = id;
    }

    @Override
    protected void onStart() {
        super.onStart();
        new HttpRequestTask().execute();
    }

    @Override /*Invocamos el Menú*/
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mi_futura_mascota, menu);
        return true;
    }

    @Override /*Implementamos lo que hace al escoger las opciones del menú*/
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //comparamos para ver que opción has seleccionado en el menú
        if (id == R.id.me_interesa) {
            Intent newIntentViewForm = new Intent(getBaseContext(),DatosUsuarioActivity.class);
            startActivity(newIntentViewForm);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void updateMascota() {
        TextView textView = (TextView) findViewById(R.id.item_name);
        String informacion = "";

        String nombre = mMascota.getNombreM() + "\n",
                tipo = mMascota.getTipo() + "\n",
                fecha_nacimiento = mMascota.getFecha_nacimiento() + "\n",
                genero = mMascota.getGenero() + "\n",
                peso = mMascota.getPeso() + "\n",
                color = mMascota.getColor() + "\n",
                estado = mMascota.getEstado() + "\n",
                descripcion = mMascota.getDescripcion() + "\n",
                fecha_ingreso = mMascota.getFecha_ingreso() + "\n",
                raza = mMascota.getNombreRaza() + "\n";

        informacion = informacion+"Nombre: "+ nombre+"Tipo: "+tipo+"Raza: "+raza+"Fecha de nacimiento: "+fecha_nacimiento+"Género: "+genero+"Peso: "+peso+"Color: "+color+"Estado: "+estado+"Fecha Ingreso: "+fecha_ingreso+"Descripcion: "+descripcion;
        textView.setText(informacion);


        ImageView image1 = (ImageView) findViewById(R.id.image1);
        ImageView image2 = (ImageView) findViewById(R.id.image2);
        new DownloadImageTask(image1).execute("http://172.16.15.220:8080/" + mMascota.getImagen1());
        new DownloadImageTask(image2).execute("http://172.16.15.220:8080/" + mMascota.getImagen2());
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, Mascota> {
        @Override
        protected Mascota doInBackground(Void... params) {
            try {
                String url = "http://172.16.15.220:8080/MascotaWs/wr/universal_ws/mascotaSola/" + mascotaId;

                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Mascota mascota = restTemplate.getForObject(url, Mascota.class);

                return mascota;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Mascota mascota) {
            mMascota = mascota;
            updateMascota();
        }

    }


}
