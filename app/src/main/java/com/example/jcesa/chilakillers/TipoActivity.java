package com.example.jcesa.chilakillers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class TipoActivity extends AppCompatActivity {
    private Mascota[] mMascotas;
    private boolean mType;
    protected int mRaza = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo);
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
        String type = b.containsKey("type") ? b.getString("type") : "";
        mType = type.equals("Perros");

        mRaza = b.containsKey("raza") ? b.getInt("raza") : 0;

        setTitle(type);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new HttpRequestTask().execute();
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == 1){
            if(resultCode == Activity.RESULT_OK){
                String type = data.getStringExtra("type");
            }
        }
    }
*/

    @Override /*Invocamos el Menú*/
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tipo, menu);
        return true;
    }

    @Override /*Implementamos lo que hace al escoger las opciones del menú*/
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //comparamos para ver que opción has seleccionado en el menú
        if (id == R.id.todos) {
            return true;
        }
        if (id == R.id.por_raza) {
            Intent intR = new Intent(getBaseContext(),RazasActivity.class);
            startActivity(intR);
            return true;
        }
        if (id == R.id.por_edad) {
            return true;
        }
        if (id == R.id.por_color) {
            return true;
        }
        if (id == R.id.por_estado) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void updateMascotas() {

        ListView listView = (ListView) findViewById(R.id.tipo_list);
        listView.setAdapter(new TipoAdapter(this, mMascotas));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getBaseContext(), MascotaActivity.class);
                i.putExtra("id", mMascotas[position].getId_mascota());
                startActivity(i);
            }
        });
    }


    public class TipoAdapter extends ArrayAdapter<Mascota> {
        private final Context context;
        private final Mascota[] values;

        public TipoAdapter(Context context, Mascota[] values) {
            super(context, R.layout.tipo_item, values);
            this.context = context;
            this.values = values;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View rowView = inflater.inflate(R.layout.tipo_item, parent, false);
            TextView textView = (TextView) rowView.findViewById(R.id.item_name);
            textView.setText(values[position].getNombreM());

            ImageView image = (ImageView) rowView.findViewById(R.id.icon);
            new DownloadImageTask(image).execute("http://172.16.15.220:8080/" + mMascotas[position].getImagen1());

            return rowView;
        }
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, Mascota[]> {
        @Override
        protected Mascota[] doInBackground(Void... params) {
            try {
                String url = "http://172.16.15.220:8080/MascotaWs/wr/universal_ws/";
                if(mRaza == 0) {
                    url += "mascotas/" + (mType ? "true" : "false");
                }
                else {
                    url += "razas/" + mRaza;
                }

                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Mascota[] mascotas = restTemplate.getForObject(url, Mascota[].class);

                //Mascota[] mascotas = entity.getBody();
                return mascotas;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Mascota[] mascotas) {
            mMascotas = mascotas;
            updateMascotas();
        }

    }

}
