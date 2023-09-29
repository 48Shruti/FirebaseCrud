package com.shruti.firebasecrud

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.shruti.firebasecrud.databinding.ActivityMainBinding
import com.shruti.firebasecrud.databinding.ItemLayoutBinding

class MainActivity : AppCompatActivity(),NotesInterface {
    var binding : ActivityMainBinding ?= null
    lateinit var adapter: RecyclerAdapter
    lateinit var layoutManager: LinearLayoutManager
    var firestore = Firebase.firestore
    var item  = arrayListOf<NotesDataClass>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        layoutManager = LinearLayoutManager(this)
        binding?.recyceler?.layoutManager = layoutManager
        adapter = RecyclerAdapter(item, this)
        binding?.recyceler?.adapter = adapter
        getCollectionsData()
//        throw RuntimeException("Test Crash")
        binding?.fab?.setOnClickListener {
            var dialog = Dialog(this)
            var dialogBinding = ItemLayoutBinding.inflate(layoutInflater)
            dialog.setContentView(dialogBinding.root)
            dialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            dialogBinding.save.setOnClickListener {
                if (dialogBinding.ettitle.text.isNullOrEmpty()) {
                    dialogBinding.ettitle.error = "Enter title"
                } else if (dialogBinding.etdescription.text.isNullOrEmpty()) {
                    dialogBinding.etdescription.error = "Enter description"
                } else {
//                    item.add(
//                        NotesDataClass(
//                            title = dialogBinding.ettitle.text.toString(),
//                            descriptions = dialogBinding.etdescription.text.toString()
//                        )
//                    )
                    firestore.collection("Users").add(
                        NotesDataClass(
                            title = dialogBinding.ettitle.text.toString(),
                            descriptions = dialogBinding.etdescription.text.toString()
                        )
                    )
                        .addOnSuccessListener {
                            Toast.makeText(this,"DAta Added",Toast.LENGTH_SHORT).show()
                            getCollectionsData()
                        }
                        .addOnFailureListener{
                            Toast.makeText(this,"Data fail",Toast.LENGTH_SHORT).show()
                        }
                        .addOnCanceledListener {
                            Toast.makeText(this," Data Cancel",Toast.LENGTH_SHORT).show()
                        }
                    dialog.dismiss()
                }
            }
            dialog.show()
        }
    }
//    fun fabClick(){
//        val dialog = Dialog(this)
//        val dialogBinding = ItemLayoutBinding.inflate(layoutInflater)
//        dialog.setContentView(dialogBinding.root)
//        dialog.show()
//    }
//    fun save(){
//
//    }
    override fun update(notesDataClass: NotesDataClass, position: Int) {
        var dialog = Dialog(this)
        var dialogBinding = ItemLayoutBinding.inflate(layoutInflater)

        dialog.setContentView(dialogBinding.root)
    dialog.window?.setLayout(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )
    dialogBinding.ettitle.setText(notesDataClass.title)
    dialogBinding.etdescription.setText(notesDataClass.descriptions)
        dialogBinding.save.setOnClickListener {
            if (dialogBinding.ettitle.text.isNullOrEmpty()) {
                dialogBinding.ettitle.error = "Enter title"
            } else if (dialogBinding.etdescription.text.isNullOrEmpty()) {
                dialogBinding.etdescription.error = "Enter description"
            } else {
                var updateNotes =
                    NotesDataClass(
                        title = dialogBinding.ettitle.text.toString(),
                        descriptions = dialogBinding.etdescription.text.toString()
                    )
                firestore.collection("Users").document(notesDataClass.id?:"")
                    .set(updateNotes)
                    .addOnSuccessListener {
                        Toast.makeText(this,"Data Suceess",Toast.LENGTH_SHORT).show()
                        getCollectionsData()
                    }
                    .addOnCanceledListener {
                        Toast.makeText(this,"Data Cancel",Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener{
                        Toast.makeText(this,"Data Fail",Toast.LENGTH_SHORT).show()
                    }
                adapter.notifyDataSetChanged()
                dialog.dismiss()
            }
        }
    dialog.show()
    }

    override fun delete(notesDataClass: NotesDataClass, position: Int) {
       item.removeAt(position)
        adapter.notifyDataSetChanged()
    }
    private fun getCollectionsData(){
        item.clear()
        firestore.collection("Users").get()
            .addOnSuccessListener {
                for (items in it.documents){
                    var firestore = items.toObject(NotesDataClass::class.java)?: NotesDataClass()
                    firestore.id =  items.id
                    item.add(firestore)
                }
                adapter.notifyDataSetChanged()
            }
    }
}
