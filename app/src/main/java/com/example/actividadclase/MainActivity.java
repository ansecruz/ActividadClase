package com.example.actividadclase;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText matricula, nombres, apellidos, edad, sintomas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        matricula = (EditText) findViewById(R.id.etMatricula);
        nombres = (EditText) findViewById(R.id.etNombres);
        apellidos = (EditText) findViewById(R.id.etApellidos);
        edad = (EditText) findViewById(R.id.etEdad);
        sintomas = (EditText) findViewById(R.id.etSintomas);
    }

    public void registrar(View view){
        SQLite admin = new SQLite(this,"administracion",null,1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        String matriculaText = matricula.getText().toString();
        String nombresText = nombres.getText().toString();
        String apellidosText = apellidos.getText().toString();
        String edadText = edad.getText().toString();
        String sintomasText = sintomas.getText().toString();

        if(!matriculaText.isEmpty()||!nombresText.isEmpty()||!apellidosText.isEmpty()||!edadText.isEmpty()||!sintomasText.isEmpty()){
            bd.execSQL("insert into estudiantes (id_estudiante,nombres,apellidos,edad,sintomas) values ("+matriculaText+",'"+nombresText+"','"+apellidosText+"',"+edadText+",'"+sintomasText+"')");
            bd.close();
            matricula.setText("");
            nombres.setText("");
            apellidos.setText("");
            edad.setText("");
            sintomas.setText("");
            Toast.makeText(this,"Se registro la cita medica, espere que su medico asinado le envie un correo con la fecha a asistir",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Por favor, llene todos los campos.",Toast.LENGTH_SHORT).show();
        }
    }

    public void consultar(View view){
        SQLite admin = new SQLite(this,"administracion",null,1);
        SQLiteDatabase bd = admin.getReadableDatabase();

        String matriculaText = matricula.getText().toString();

        if(!matriculaText.isEmpty()) {
            Cursor fila = bd.rawQuery("select nombres,apellidos,edad,sintomas from estudiantes where id_estudiante=" + matriculaText, null);
            if (fila.moveToFirst()) {
                nombres.setText(fila.getString(0));
                apellidos.setText(fila.getString(1));
                edad.setText(fila.getString(2));
                sintomas.setText(fila.getString(3));
                Toast.makeText(this, "Consulta exitosa.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No existe un estudiante con dicha matricula.", Toast.LENGTH_SHORT).show();
            }
            bd.close();
        }else{
            Toast.makeText(this, "Ingrese un numero de matricula.", Toast.LENGTH_SHORT).show();
        }
    }

    public void modificar(View v){
        SQLite admin = new SQLite( this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        String matriculaText = matricula.getText().toString();
        String nombresText = nombres.getText().toString();
        String apellidosText = apellidos.getText().toString();
        String edadText = edad.getText().toString();
        String sintomasText = sintomas.getText().toString();

        if(!matriculaText.isEmpty()){
            if(nombresText.isEmpty()||apellidosText.isEmpty()||edadText.isEmpty()||sintomasText.isEmpty()){
                Toast.makeText(this,"Ingrese todos los datos. ", Toast.LENGTH_SHORT).show();
            }
            //try{
            bd.execSQL("update estudiantes set id_estudiante="+matriculaText+",nombres='"+nombresText+"',apellidos='"+apellidosText+"',edad="+edadText+"id_sintomas'"+sintomasText+"' where id_estudiante="+matriculaText);
            matricula.setText("");
            nombres.setText("");
            apellidos.setText("");
            edad.setText("");
            sintomas.setText("");
            bd.close();
            Toast.makeText(this,"Se han modificado los datos de su registro medico.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Ingrese su numero de matricula.",Toast.LENGTH_SHORT).show();
        }
    }

    public void eliminar(View v) {
        SQLite admin = new SQLite(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String matriculaText = matricula.getText().toString();


        if (!matriculaText.isEmpty()) {
            bd.execSQL("delete from estudiantes where id_estudiante=" + matriculaText);
            bd.close();
            matricula.setText("");
            nombres.setText("");
            apellidos.setText("");
            edad.setText("");
            sintomas.setText("");
            Toast.makeText(this, "Estudiante eliminado de la base de datos", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Ingrese un numero de matricula.", Toast.LENGTH_SHORT).show();
        }
    }
}