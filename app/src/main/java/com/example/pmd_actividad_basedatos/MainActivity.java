package com.example.pmd_actividad_basedatos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String NOMBRE_DB="usuarios.db";
    private static final int VERSION_ACTUAL = 1;

    private MiBaseDatos miBaseDatos;
    private SQLiteDatabase database;

    private Button botonRegistro;
    private Button botonMostrar;
    private EditText textoUsuario;
    private EditText textoPassword;
    private TextView textViewResults;
    private String nombre;
    private String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botonRegistro = (Button)findViewById(R.id.buttonRegister);
        botonMostrar = (Button)findViewById(R.id.buttonShowRegister);
        textoUsuario = (EditText)findViewById(R.id.eTUser);
        textoPassword = (EditText)findViewById(R.id.eTPassword);
        textViewResults = (TextView)findViewById(R.id.textViewResult);
        botonRegistro.setOnClickListener(this);
        botonMostrar.setOnClickListener(this);

        miBaseDatos = new MiBaseDatos(getApplicationContext(),NOMBRE_DB,null,VERSION_ACTUAL);
        database = miBaseDatos.getWritableDatabase();

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.buttonRegister:
                nombre = textoUsuario.getText().toString();
                password = textoPassword.getText().toString();

                ContentValues nuevoRegistro = new ContentValues();
                nuevoRegistro.put("nombre",nombre );
                nuevoRegistro.put("password",password);
                database.insert("usuario",null,nuevoRegistro);
                Toast.makeText(getApplicationContext(),"Datos almacenados", Toast.LENGTH_SHORT).show();
                break;

            case R.id.buttonShowRegister:

                Toast.makeText(getApplicationContext(),"Consultando datos", Toast.LENGTH_SHORT).show();

                Cursor micursor = database.rawQuery("SELECT * FROM usuario",null);
                textViewResults.setText("");
                if(micursor.moveToFirst()){
                    do{
                        nombre = micursor.getString(0);
                        password = micursor.getString(1);
                        textViewResults.append(" "+nombre+" - "+password+"\n");
                    }while (micursor.moveToNext());
                }
                micursor.close();
                break;
        }

    }
}