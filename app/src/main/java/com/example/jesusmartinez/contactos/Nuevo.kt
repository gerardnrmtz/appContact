package com.example.jesusmartinez.contactos

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import kotlinx.android.synthetic.main.activity_nuevo.*

class Nuevo : AppCompatActivity() {

    var fotoindex:Int=0
    val fotos= arrayOf(R.drawable.foto_01,R.drawable.foto_02,R.drawable.foto_03,R.drawable.foto_04,R.drawable.foto_05,R.drawable.foto_06)
    var foto:ImageView?=null
    var index:Int=-1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo)

        val toolbar=findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionbar=supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)

       foto=findViewById(R.id.ivFoto)
        foto?.setOnClickListener(){
            seleccionarFoto()
        }

        //reconocer accion de nuevo vs editar
        if(intent.hasExtra("ID"))
        {
            index=intent.getStringExtra("ID").toInt()
            rellenarDatos(index)
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_nuevo,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId)
        {
            R.id.home->{finish()
            return true}
            R.id.iCrearnuevo->{

                val nombre=findViewById<EditText>(R.id.tvnombre)
                val apellido=findViewById<EditText>(R.id.tvApellidos)
                val empresa=findViewById<EditText>(R.id.tvEmpresa)
                val peso=findViewById<EditText>(R.id.tvPeso)
                val telefono=findViewById<EditText>(R.id.tvTelefono)
                val edad=findViewById<EditText>(R.id.tvEdad)
                val email=findViewById<EditText>(R.id.tvEmail)
                val direccion=findViewById<EditText>(R.id.tvDireccion)
                //validar datos
                var campos=ArrayList<String>()
                campos.add(nombre.text.toString())
                campos.add(apellido.text.toString())
                campos.add(empresa.text.toString())
                campos.add(edad.text.toString())
                campos.add(peso.text.toString())
                campos.add(direccion.text.toString())
                campos.add(telefono.text.toString())
                campos.add(email.text.toString())
                var flag:Int=0

                for (campo in campos)
                {
                    if(campo.isNullOrEmpty())
                    {
                     flag++
                    }
                }
                if(flag>0)
                    Toast.makeText(this,"Rellena todos los campos",Toast.LENGTH_SHORT).show()
                else{
                    if(index>-1)
                    {
                        MainActivity.actualizarContacto(index,Contacto(campos.get(0),campos.get(1),campos.get(2),campos.get(3).toInt(),campos.get(4).toFloat(),campos.get(5),campos.get(6),campos.get(7),obtenerFoto(fotoindex)))
                        finish()
                    }
                    else{
                        MainActivity.AgregarContacto(Contacto(campos.get(0),campos.get(1),campos.get(2),campos.get(3).toInt(),campos.get(4).toFloat(),campos.get(5),campos.get(6),campos.get(7),obtenerFoto(fotoindex)))
                        finish()
                    }



                }


                //Crear nuevo elemento de tipo contacto
                return true

            }

            else->{return super.onOptionsItemSelected(item)}
        }



    }
    fun seleccionarFoto(){
        val builder=AlertDialog.Builder(this)
        builder.setTitle("Selecciona imagen de perfil")

        val adapter=ArrayAdapter<String>(this,android.R.layout.simple_selectable_list_item)
        adapter.add("foto_01")
        adapter.add("foto_02")
        adapter.add("foto_03")
        adapter.add("foto_04")
        adapter.add("foto_05")
        adapter.add("foto_06")
        builder.setAdapter(adapter){
            dialog,which->
            fotoindex=which
            foto?.setImageResource(obtenerFoto(fotoindex))


        }
         builder.setNegativeButton("Cancelar") { dialog, wihch ->
            dialog.dismiss()
        }
        builder.show()
    }
    fun obtenerFoto(index:Int):Int{
        return  fotos.get(index)
    }
    fun rellenarDatos(index:Int){
        val contacto=MainActivity.obtenerContacto(index)
        var tvnombre=findViewById<EditText>(R.id.tvnombre)
        var tvappelido=findViewById<EditText>(R.id.tvApellidos)
        var tvEmpresa=findViewById<EditText>(R.id.tvEmpresa)
        var tvEdad=findViewById<EditText>(R.id.tvEdad)
        var tvPeso=findViewById<EditText>(R.id.tvPeso)
        var tvTelefono=findViewById<EditText>(R.id.tvTelefono)
        var tvEmail=findViewById<EditText>(R.id.tvEmail)
        var tvDireccion=findViewById<EditText>(R.id.tvDireccion)
        var id_foto=findViewById<ImageView>(R.id.ivFoto)

        tvnombre.setText(contacto.nombre,TextView.BufferType.EDITABLE)
        tvappelido.setText(contacto.apellidos,TextView.BufferType.EDITABLE)
        tvEmpresa.setText(contacto.empresa,TextView.BufferType.EDITABLE)
        tvEdad.setText(contacto.edad.toString(),TextView.BufferType.EDITABLE)
        tvPeso.setText(contacto.peso.toString(),TextView.BufferType.EDITABLE)
        tvTelefono.setText(contacto.telefono,TextView.BufferType.EDITABLE)
        tvEmail.setText(contacto.email,TextView.BufferType.EDITABLE)
        tvDireccion.setText(contacto.direccion,TextView.BufferType.EDITABLE)
        tvnombre.setText(contacto.nombre,TextView.BufferType.EDITABLE)
        ivFoto.setImageResource(contacto.photo)

        var posicion:Int=0
        for (foto in fotos)
        {
            if(contacto.photo==foto)
                fotoindex=posicion
            posicion++
        }
    }
}
