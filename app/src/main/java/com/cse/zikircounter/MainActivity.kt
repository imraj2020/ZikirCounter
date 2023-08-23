package com.cse.zikircounter

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.room.Room
import com.cse.zikircounter.RoomDB.ZikirCount
import com.cse.zikircounter.RoomDB.ZikirDatabase
import com.cse.zikircounter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var score : Int = 0
    private lateinit var field1EditText: EditText
    private lateinit var field2EditText: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.inecrementBtn.setOnClickListener(View.OnClickListener {
            score++
            setvalue(score)
        })

        binding.resetBtn.setOnClickListener {
            score = 0
            setvalue(score)
        }



        binding.saveBtn.setOnClickListener(View.OnClickListener {

            showAlertDialog()
//            val Contacts = Contact(
//                0,
//                binding.editText1.text.toString(),
//                binding.editText2.text.toString(),
//                binding.editText3.text.toString()
//            )
//
//            db.getContactDao().InsertContact(Contacts)


        })


    }

    private fun showCustomAlertDialog(context: Context) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_layout, null)
        field1EditText = dialogView.findViewById(R.id.editText1) // Initialize the EditText field
        field2EditText = dialogView.findViewById(R.id.editText2) // Initialize the EditText field
        field2EditText.setText("$score")

        val alertDialogBuilder = AlertDialog.Builder(context)
            .setView(dialogView)
            .setTitle("Custom Dialog")
            .setPositiveButton("Save") { dialog, _ ->
                val field1Text = field1EditText.text.toString()
                val field2Text = field2EditText.text.toString()
                val intValue: Int = field2Text.toInt()

                var db = Room.databaseBuilder(applicationContext, ZikirDatabase::class.java, "Zikir_list.db")
                    .allowMainThreadQueries().build()

                val  ZikirCount = ZikirCount(
                    field1Text,
                    intValue
                )


                try{
                    db.getZikirDao().InsertCount(ZikirCount)
                } catch (e: NumberFormatException) {
                    // Handle the case where the string is not a valid integer
                    Toast.makeText(context, ""+e, Toast.LENGTH_SHORT).show()
                }


                // Process the entered text as needed
                // For example, display a toast message with the entered text
                Toast.makeText(context, "Field 1: $field1Text\nField 2: $field2Text", Toast.LENGTH_SHORT).show()

                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun showAlertDialog() {
        showCustomAlertDialog(this)
    }


    private fun setvalue(score: Int) {
        binding.scoreTv.text = "$score"
    }
}