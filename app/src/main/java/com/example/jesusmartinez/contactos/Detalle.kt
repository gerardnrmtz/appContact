package com.example.jesusmartinez.contactos

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.template_contacto.*

class Detalle : AppCompatActivity() {

    var index=0
    var from:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle)

        val toolbar=findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val actionbar=supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)
         index=intent.getStringExtra("ID").toString().toInt()
        from=intent.getStringExtra("From").toString()
    }
    fun mapearDatos(){

        var contacto:Contacto?=null
        if(from=="ListView")
        {
            contacto=MainActivity.obtenerContacto(index)
        }
        else
        {
            contacto=MainActivity.obtenerContactogrid(index)
        }


        var tvnombre=findViewById<TextView>(R.id.tvnombre)
        var tvEmpresa=findViewById<TextView>(R.id.tvEmpresa)
        var tvEdad=findViewById<TextView>(R.id.tvEdad)
        var tvPeso=findViewById<TextView>(R.id.tvPeso)
        var tvTelefono=findViewById<TextView>(R.id.tvTelefono)
        var tvEmail=findViewById<TextView>(R.id.tvEmail)
        var tvDireccion=findViewById<TextView>(R.id.tvDireccion)
        var id_foto=findViewById<ImageView>(R.id.ivFoto)

        tvnombre.text=contacto.nombre+" "+contacto.apellidos
        tvEmpresa.text=contacto.empresa
        tvEdad.text=contacto.edad.toString()+" años"
        tvPeso.text=contacto.peso.toString()+"Kg"
        tvTelefono.text=contacto.telefono
        tvEmail.text=contacto.email
        tvDireccion.text=contacto.direccion
        id_foto.setImageResource(contacto.photo)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalle,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item?.itemId)
        {
            android.R.id.home->{
                finish()
                return true
            }
            R.id.Eliminar->{

                MainActivity.eliminarContacto(index)
                finish()
                return true

            }
            R.id.Editar->{
                val intent=Intent(this,Nuevo::class.java)
                intent.putExtra("ID",index.toString())
                startActivity(intent)

                return true

            }



            else->{return super.onOptionsItemSelected(item)}
        }

    }

    override fun onResume() {
        mapearDatos()
        super.onResume()
    }
}
