package com.example.estudiar1

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.color.utilities.CorePalette


class formulario : AppCompatActivity() {

    private lateinit var Run: EditText
    private lateinit var Nombre: EditText
    private lateinit var Apellido: EditText
    private lateinit var Correo: EditText
    private lateinit var Telefono: EditText
    private var ListaDatos = ArrayList<String>()
    private lateinit var  adaptador: ArrayAdapter<String>
    private  lateinit var Lista: ListView

@SuppressLint("MissingInflateId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_formulario)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_form)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    Run=findViewById(R.id.txt_run)
    Nombre=findViewById(R.id.txt_nombre)
    Apellido=findViewById(R.id.txt_apellido)
    Correo=findViewById(R.id.txt_correo)
    Telefono=findViewById(R.id.txt_telefono)
    Lista=findViewById(R.id.lista)
    adaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1, ListaDatos)
    Lista.adapter=adaptador

    //Preciona para info
    Lista.setOnItemClickListener {parent, view, position, id ->
        val item = ListaDatos[position]
        mostrarAlerta("informacion de registro", item)
    }
    //Mantener para borrar
        Lista.setOnItemLongClickListener {parent, view, position, id ->
        val item =ListaDatos[position]
        ListaDatos.removeAt(position)
        adaptador.notifyDataSetChanged()
        Toast.makeText(this,"Registro eliminado", Toast.LENGTH_SHORT).show()
        true

        }

    }

    //Valida y Guarda
    fun procesarForm(view : View){

        var run: String =Run.text.toString().trim()
        var nombre : String= Nombre.text.toString().trim()
        var apellido : String= Apellido.text.toString().trim()
        var correo : String= Correo.text.toString().trim()
        var telefono :String = Telefono.text.toString().trim()

        //valida la id
        val existe = ListaDatos.any {it.contains("run: $run")}

        var error = ""

        when {

            run.isBlank() -> error = "debe ingresar run"
            nombre.isBlank() -> error = "debe ingresar nombre"
            apellido.isBlank() -> error = "debe ingresar apellido"
            correo.isBlank() -> error  = "debe ingresar correo"
            telefono.isBlank() -> error = "debe ingresar telefono"
        }
        if (error.isNotEmpty())
        {
            Toast.makeText(this, error, Toast.LENGTH_LONG).show()
            return
        }

        if(existe){
            Toast.makeText(this, "el Run ya existe", Toast.LENGTH_LONG).show()
        }else{
            val mensaje = "run: $run ,nombre: $nombre ,apellido: $apellido ,correo: $correo , telefono: $telefono"
            ListaDatos.add(mensaje)
            adaptador.notifyDataSetChanged()
            Toast.makeText(this, "guardado", Toast.LENGTH_LONG).show()
            mostrarAlerta("formulario de registro", mensaje)
            limpiar()
        }


    }

    fun limpiar(){
        Run.text.clear()
        Nombre.text.clear()
        Apellido.text.clear()
        Correo.text.clear()
        Telefono.text.clear()
    }



    //Muestra Alerta antes de guardar
    fun mostrarAlerta (titulo : String, mensaje : String) {
        var builder = AlertDialog.Builder(this);
        builder.setTitle(titulo)
        builder.setMessage(mensaje)
        builder.setPositiveButton("Aceptar"){
                dialog, _ ->
            dialog.dismiss()
        }
        var dialog = builder.create()
        dialog.show()
    }

    fun volver(view : View){

        var intent = Intent (this, MainActivity::class.java)
        startActivity(intent)

    }












}