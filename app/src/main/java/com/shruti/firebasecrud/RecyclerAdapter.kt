package com.shruti.firebasecrud

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(var item : ArrayList<NotesDataClass>, var notesInterface: NotesInterface) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    class ViewHolder(var view : View) : RecyclerView.ViewHolder(view) {
        var title = view.findViewById<TextView>(R.id.tvtitleview)
        var description = view.findViewById<TextView>(R.id.tvdescriptionview)
        var updates = view.findViewById<ImageButton>(R.id.btnupdate)
        var delete = view.findViewById<ImageButton>(R.id.btndelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout_view,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return item.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.setText(item[position].title)
        holder.description.setText(item[position].descriptions)
        holder.updates.setOnClickListener{
            notesInterface.update(item[position],position)
        }
        holder.delete.setOnClickListener{
            notesInterface.delete(item[position],position)
        }
    }
}