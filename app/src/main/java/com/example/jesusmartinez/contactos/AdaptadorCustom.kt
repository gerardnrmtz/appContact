package com.example.jesusmartinez.contactos

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class AdaptadorCustom(var context:Context,items:ArrayList<Contacto>):BaseAdapter() {

    //Almacenar los elementos que se van a mostar en el listView
    var items:ArrayList<Contacto>?=null
    var copiaItem:ArrayList<Contacto>?=null
    init {
        this.items= ArrayList(items)
        this.copiaItem=items
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var viewHolder:ViewHolder?=null
        var vista=convertView
        if(vista==null)
        {
            vista=LayoutInflater.from(context).inflate(R.layout.template_contacto,null)
            viewHolder= ViewHolder(vista)
            vista.tag=viewHolder

        }else{
            viewHolder=vista.tag as? ViewHolder
        }
        //asignacion de valores ha elementos graficos
        val item=getItem(position) as Contacto
        viewHolder?.nombre?.text=item.nombre+" "+item.apellidos
        viewHolder?.empresa?.text=item.empresa
        viewHolder?.foto?.setImageResource(item.photo)
        return vista!!

    }

    override fun getItem(position: Int): Any {
       return this.items?.get(position)!!
    }

    override fun getItemId(position: Int): Long {
       return position.toLong()
    }

    override fun getCount(): Int {
       return this.items?.count()!!
    }
    fun addItem(item:Contacto)
    {
        copiaItem?.add(item)
        items= ArrayList(copiaItem)
        notifyDataSetChanged()
    }
    fun removeItem(index:Int)
    {
        copiaItem?.removeAt(index)
        items= ArrayList(copiaItem)
        notifyDataSetChanged()
    }
    fun updateItem(index:Int,newItem:Contacto)
    {
        copiaItem?.set(index,newItem)
        items= ArrayList(copiaItem)
        notifyDataSetChanged()
    }
    fun filtrar(str:String)
    {
        items?.clear()
        if(str.isNullOrEmpty())
        {
            items= ArrayList(copiaItem)
            notifyDataSetChanged()
            return
        }
        var busqueta=str
        busqueta=busqueta.toLowerCase()
        for (item in copiaItem!!)
        {
            val nombre=item.nombre.toLowerCase()

            if(nombre.contains(busqueta))
            {
                items?.add(item)
            }
        }
        notifyDataSetChanged()

    }


    private class ViewHolder(vista:View){
        var nombre:TextView?=null
        var foto:ImageView?=null
        var empresa:TextView?=null

        init {
            this.nombre=vista.findViewById(R.id.tvnombre)
            this.empresa=vista.findViewById(R.id.tvempresa)
            this.foto=vista.findViewById(R.id.ivfoto)
        }
    }
}