package com.example.jesusmartinez.contactos

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var lista:ListView?=null
    var grid:GridView?=null
    var viewswicher: ViewSwitcher?=null

    //Identifica como objeto estatico
companion object {
        var adaptador:AdaptadorCustom?=null
        var adaptadorGrid:AdaptadorCustomGrid?=null
    var contactos:ArrayList<Contacto>?=null
        fun AgregarContacto(contacto: Contacto)
        {
            adaptador?.addItem(contacto)
            adaptadorGrid?.addItem(contacto)
        }
        fun obtenerContacto(index:Int):Contacto{
            return adaptador?.getItem(index) as Contacto
        }
        fun obtenerContactogrid(index:Int):Contacto
        {
            return adaptadorGrid?.getItem(index) as Contacto
        }
        fun eliminarContacto(index:Int){
            adaptador?.removeItem(index)
            adaptadorGrid?.removeItem(index)
        }
        fun actualizarContacto(index:Int,nuevoContacto:Contacto)
        {
            adaptadorGrid?.updateItem(index,nuevoContacto)
            adaptador?.updateItem(index,nuevoContacto)
        }
}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar=findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        contactos= ArrayList()
        contactos?.add(Contacto("Gerardo","Martinez","Bisimplex",24,100.0F,"Zaragoza 247","8717854636","gerardnrmtz@gmail.com",R.drawable.foto_01))
        contactos?.add(Contacto("Jesus","Palacios","Bisimplex",24,100.0F,"Zaragoza 247","8717854636","gerardnrmtz@gmail.com",R.drawable.foto_02))
        contactos?.add(Contacto("Juan","Dorado","Bisimplex",24,100.0F,"Zaragoza 247","8717854636","gerardnrmtz@gmail.com",R.drawable.foto_03))

        lista=findViewById(R.id.lista)
        grid=findViewById(R.id.grid)

        adaptador= AdaptadorCustom(this, contactos!!)
        adaptadorGrid= AdaptadorCustomGrid(this, contactos!!)

        viewswicher=findViewById(R.id.vs)

        lista?.adapter=adaptador
        grid?.adapter= adaptadorGrid
        
        lista?.setOnItemClickListener { parent, view, position, id ->

            val intent=Intent(this,Detalle::class.java)
            intent.putExtra("ID",position.toString())
            intent.putExtra("From","ListView")
            startActivity(intent)

        }
        grid?.setOnItemClickListener { parent, view, position, id ->
            val intent=Intent(this,Detalle::class.java)
            intent.putExtra("ID",position.toString())
            intent.putExtra("From","GridView")
            startActivity(intent)

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        val search_manager=getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val itemBusqueda=menu?.findItem(R.id.searchView)
        val searchview=itemBusqueda?.actionView as android.support.v7.widget.SearchView
        //swtch

        val itemswitch=menu?.findItem(R.id.switchView)
        itemswitch.setActionView(R.layout.switch_item)
        val switchView=itemswitch?.actionView!!.findViewById<Switch>(R.id.sCambiaVista)




        searchview.setSearchableInfo(search_manager.getSearchableInfo(componentName))
        searchview.queryHint="Buscar contacto"
        searchview.setOnQueryTextFocusChangeListener { v, hasFocus ->
        //Preparar los datos

        }
        searchview.setOnQueryTextListener(object :android.support.v7.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {
                //filtrar
                adaptador?.filtrar(newText!!)
                return true

            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                //filtrar
               return true
            }
        })
        switchView.setOnCheckedChangeListener { buttonView, isChecked ->
            viewswicher?.showNext()

        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId)
        {
            R.id.iditemNuevo->{

                val intent=Intent(this,Nuevo::class.java)
                startActivity(intent)
                return true

            }

            else->{return super.onOptionsItemSelected(item)}
        }

    }

    override fun onResume() {
        super.onResume()
        adaptador?.notifyDataSetChanged()
        adaptadorGrid?.notifyDataSetChanged()

    }

}
