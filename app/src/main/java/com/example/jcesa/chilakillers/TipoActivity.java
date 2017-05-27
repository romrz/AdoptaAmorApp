package com.example.jcesa.chilakillers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class TipoActivity extends AppCompatActivity {

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
        final String type = b.getString("type");
        setTitle(type);


        final String[] myDataset = {
                "Mascota 1",
                "Mascota 2",
                "Mascota 3",
                "Mascota 4",
                "Mascota 1",
                "Mascota 2",
                "Mascota 3",
                "Mascota 4",
                "Mascota 1",
                "Mascota 2",
                "Mascota 3",
                "Mascota 4"
        };


        ListView listView = (ListView) findViewById(R.id.tipo_list);
        listView.setAdapter(new TipoAdapter(this, myDataset));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(TipoActivity.this.getBaseContext(), MascotaActivity.class);
                i.putExtra("name", myDataset[position]);
                //i.putExtra("type",type);
                //startActivityForResult(i,1);
                startActivity(i);
            }
        });
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
        if (id == R.id.por_raza) {
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


    public class TipoAdapter extends ArrayAdapter<String> {
        private final Context context;
        private final String[] values;

        public TipoAdapter(Context context, String[] values) {
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
            textView.setText(values[position]);

            return rowView;
        }
    }

}
