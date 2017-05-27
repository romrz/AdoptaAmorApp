package com.example.jcesa.chilakillers;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        View.OnClickListener listener = new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(getBaseContext(),TipoActivity.class); /*Creamos el Intent*/
                i.putExtra("type",((Button)v).getText()); /*Poonemos parámetros extras*/
                startActivity(i);
            }
        };

        Button gatosBtn = (Button) findViewById(R.id.gatos);
        gatosBtn.setOnClickListener(listener);

        Button perrosBtn = (Button) findViewById(R.id.perros);
        perrosBtn.setOnClickListener(listener);

    }

    @Override /*Invocamos el Menú*/
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override /*Implementamos lo que hace al escoger las opciones del menú*/
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //comparamos para ver que opción has seleccionado en el menú
        if (id == R.id.facebook) {
            return true;
        }
        if(id == R.id.twitter){
            return true;
        }
        if(id == R.id.contactanos){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void irGatosActivity(View view){
        Intent newIntentGatos = new Intent(getBaseContext(),TipoActivity.class);
        startActivity(newIntentGatos);
    }

   /* public void irPerrosActivity(View v){
        Intent newIntentPerros = new Intent(getBaseContext(),PerrosActivity.class);
        startActivity(newIntentPerros);
    }
    */
}
