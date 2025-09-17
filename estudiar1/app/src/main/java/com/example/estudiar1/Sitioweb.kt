package com.example.estudiar1

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Patterns
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
import org.intellij.lang.annotations.Pattern

class Sitioweb : AppCompatActivity() {

    private lateinit var Nombreweb: EditText
    private lateinit var Url: EditText
    private var ListaDatosWeb = ArrayList<String>()
    private lateinit var Adaptador: ArrayAdapter<String>
    private lateinit var ListaWeb: ListView
    @SuppressLint("MissingInflatedId")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sitioweb)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        Nombreweb = findViewById(R.id.txt_nombreweb)
        Url = findViewById(R.id.txt_url)
        ListaWeb = findViewById(R.id.listweb)
        Adaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1,ListaDatosWeb)
        ListaWeb.adapter = Adaptador

        ListaWeb.setOnItemClickListener {parent, view, position, id ->
            val item = ListaDatosWeb[position]
            mostrarAlerta("informacion de refistro", item)
        }

        ListaWeb.setOnItemLongClickListener { parent, view, position, id ->

            val item = ListaDatosWeb[position]
            ListaDatosWeb.removeAt(position)
            Adaptador.notifyDataSetChanged()
            Toast.makeText(this, "registro eliminado", Toast.LENGTH_LONG).show()
            true
        }

    }
    fun ProcesarWeb(view: View){
        var nombreweb: String = Nombreweb.text.toString().trim()
        var url: String = Url.text.toString().trim()

        val existe = ListaDatosWeb.any {it.contains("nombre: $nombreweb")}

        var error = ""

        when {

            nombreweb.isBlank() ->  error = "debe ingresar nombre de la web"
            url.isBlank() -> error = "debe ingresar url"

        }
        if(error.isNotEmpty()){
            Toast.makeText(this,error, Toast.LENGTH_LONG).show()
            return
        }
        if(existe){
            Toast.makeText(this, "nombre existente", Toast.LENGTH_LONG).show()
        }else{

            if(validarUrl(url)){
                val mensaje = "nombre: $nombreweb url: $url"
                ListaDatosWeb.add(mensaje)
                Adaptador.notifyDataSetChanged()
                Toast.makeText(this, "guardado", Toast.LENGTH_LONG).show()
                mostrarAlerta("formulario de registro", mensaje)
                limpiar()
            }
            else{
                Toast.makeText(this,"Url invalida", Toast.LENGTH_LONG).show()
            }

        }

    }
    fun mostrarAlerta (titulo: String, mensaje : String){
        var builder = AlertDialog.Builder(this)
        builder.setTitle(titulo)
        builder.setMessage(mensaje)
        builder.setPositiveButton("aceptar"){dialog, _ ->
            dialog.dismiss()
        }
        var dialog = builder.create()
        dialog.show()

    }
    fun limpiar(){
        Nombreweb.text.clear()
        Url.text.clear()
    }

    fun validarUrl(urlSitio: String): Boolean{
        return Patterns.WEB_URL.matcher(urlSitio).matches()
    }



}