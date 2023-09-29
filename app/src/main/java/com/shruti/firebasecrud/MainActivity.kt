package com.shruti.firebasecrud

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.shruti.firebasecrud.databinding.ActivityMainBinding
import com.shruti.firebasecrud.databinding.ItemLayoutBinding

class MainActivity : AppCompatActivity(),NotesInterface {
    var binding : ActivityMainBinding ?= null
    lateinit var adapter: RecyclerAdapter
    lateinit var layoutManager: LinearLayoutManager
    var item  = arrayListOf<NotesDataClass>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        layoutManager = LinearLayoutManager(this)
        binding?.recyceler?.layoutManager = layoutManager
        adapter = RecyclerAdapter(item, this)
        binding?.recyceler?.adapter = adapter
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
                    item.add(
                        NotesDataClass(
                            title = dialogBinding.ettitle.text.toString(),
                            descriptions = dialogBinding.etdescription.text.toString()
                        )
                    )
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
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        dialog.setContentView(dialogBinding.root)
        dialogBinding.save.setOnClickListener {
            if (dialogBinding.ettitle.text.isNullOrEmpty()) {
                dialogBinding.ettitle.error = "Enter title"
            } else if (dialogBinding.etdescription.text.isNullOrEmpty()) {
                dialogBinding.etdescription.error = "Enter description"
            } else {
                item.set(position,
                    NotesDataClass(
                        title = dialogBinding.ettitle.text.toString(),
                        descriptions = dialogBinding.etdescription.text.toString()
                    )
                )
                dialog.dismiss()
            }
        }
    }

    override fun delete(notesDataClass: NotesDataClass, position: Int) {
       item.removeAt(position)
    }
}
