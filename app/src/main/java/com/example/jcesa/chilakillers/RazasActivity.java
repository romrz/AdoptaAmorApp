package com.example.jcesa.chilakillers;

import android.app.ActionBar;
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
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class RazasActivity extends AppCompatActivity {

    public Raza[] mRazas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_razas);
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

    }

    @Override
    protected void onStart() {
        super.onStart();
        new HttpRequestTask().execute();
    }

    public void updateRazas() {
        ListView listView = (ListView) findViewById(R.id.razas_list_view);
        listView.setAdapter(new RazasAdapter(this, mRazas));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getBaseContext(), TipoActivity.class);
                i.putExtra("raza", mRazas[position].getId_raza());
                i.putExtra("type", mRazas[position].getRaza());
                startActivity(i);
            }
        });
    }

    public class RazasAdapter extends ArrayAdapter<Raza> {
        private Context mContext;
        public RazasAdapter(Context context, Raza[] razas) {
            super(context, 0, razas);
            mContext = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Raza raza = getItem(position);

            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View rowView = inflater.inflate(R.layout.raza_item, parent, false);
            TextView textView = (TextView) rowView.findViewById(R.id.item_name);
            textView.setText(raza.getRaza());

            //LinearLayout layout = new LinearLayout(mContext);
            //layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 100));
            //TextView tv = new TextView(mContext);
            //tv.setText(raza.getRaza());
            //layout.addView(tv);


            return rowView;
        }
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, Raza[]> {
        @Override
        protected Raza[] doInBackground(Void... params) {
            try {
                final String url = "http://172.16.15.220:8080/MascotaWs/wr/universal_ws/razas";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Raza[] razas = restTemplate.getForObject(url, Raza[].class);

                return razas;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Raza[] razas) {
            mRazas = razas;
            updateRazas();
        }

    }


}
