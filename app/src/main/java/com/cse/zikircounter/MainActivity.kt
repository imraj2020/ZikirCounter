package com.cse.zikircounter

import ZikirCountAdapter
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cse.zikircounter.RoomDB.ZikirCount
import com.cse.zikircounter.RoomDB.ZikirDatabase
import com.cse.zikircounter.databinding.ActivityMainBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds


class MainActivity : AppCompatActivity(), ZikirCountAdapter.Listener{
    private lateinit var binding: ActivityMainBinding
    var score: Int = 0
    private lateinit var ETZikirName: EditText
    private lateinit var ETZikirCount: EditText
    private lateinit var enteredText: String
    lateinit var db: ZikirDatabase
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ZikirCountAdapter
    lateinit var mAdView : AdView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //ads function

        MobileAds.initialize(this) {}

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        //end ads


         db = ZikirDatabase.getInstance(applicationContext)



        binding.inecrementBtn.setOnClickListener(View.OnClickListener {

//            val anim = ScaleAnimation(1f, 0.9f, 1f, 0.9f)
//            anim.duration = 100
//            view.startAnimation(anim)

            val anim = ScaleAnimation(1f, 0.9f, 1f, 0.9f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f)
            anim.duration = 1
            binding.inecrementBtn.startAnimation(anim)

            score++
            setvalue(score)
        })

        binding.resetBtn.setOnClickListener {
            score = 0
            setvalue(score)
        }



        binding.saveBtn.setOnClickListener(View.OnClickListener {

            showAlertDialog()

        })

        binding.ZikirName.setOnClickListener(View.OnClickListener {

            zikirnameAlertDialog(this)
        })

        binding.menu.setOnClickListener(View.OnClickListener {

            binding.mylinear.visibility = View.GONE
            binding.zikirnumcard.visibility = View.GONE
            binding.zikirbuttoncard.visibility = View.GONE
            binding.myRelativeLayout.visibility = View.VISIBLE



            recyclerView = findViewById(R.id.recyclerView)
            recyclerView.layoutManager = LinearLayoutManager(this)

            val dataList = db.getZikirDao().AllCount()
            adapter = ZikirCountAdapter(dataList, db,this@MainActivity)
            recyclerView.adapter = adapter
        })

        binding.backbtn.setOnClickListener(View.OnClickListener {
            binding.mylinear.visibility = View.VISIBLE
            binding.zikirnumcard.visibility = View.VISIBLE
            binding.zikirbuttoncard.visibility = View.VISIBLE
            binding.myRelativeLayout.visibility = View.GONE
        })


    }


    private fun zikirnameAlertDialog(context: Context) {
        val dialogView =
            LayoutInflater.from(context).inflate(R.layout.alart_dialogue_zikir_name, null)


        val editText = dialogView.findViewById<EditText>(R.id.ZikirNameET)

        val alertDialogBuilder = AlertDialog.Builder(context)
            .setView(dialogView)
            .setTitle("Enter Your Zikir Name: ")
            .setPositiveButton("OK") { dialog, _ ->
                enteredText = editText.text.toString()
                binding.ZikirName.setText("$enteredText")
                // Do something with the entered text
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun showCustomAlertDialog(context: Context) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_layout, null)


        ETZikirName = dialogView.findViewById(R.id.ETZikirNames) // Initialize the EditText field
        ETZikirCount = dialogView.findViewById(R.id.ETZikirCounts) // Initialize the EditText field
        ETZikirName.setText(binding.ZikirName.text.toString())
        ETZikirCount.setText("$score")

        val alertDialogBuilder = AlertDialog.Builder(context)
            .setView(dialogView)
            .setTitle("Enter Zikir Name & Zikir Count")
            .setPositiveButton("Save") { dialog, _ ->
                val ETZikirName = ETZikirName.text.toString()
                val ETZikirCount = ETZikirCount.text.toString()
                val intValue: Int = ETZikirCount.toInt()

                db = ZikirDatabase.getInstance(this)

                val ZikirCount = ZikirCount(
                    ETZikirName,
                    intValue
                )


                try {
                    val existingZikirCount = db.getZikirDao().getZikirCountByName(ZikirCount.name)

                    if (existingZikirCount != null) {
                        // Update the existing record
                        db.getZikirDao().UpdateCount(ZikirCount)
                    } else {
                        // Insert a new record
                        db.getZikirDao().InsertCount(ZikirCount)
                    }
                } catch (e: Exception) {
                    // Handle the case where the string is not a valid integer
                    Toast.makeText(context, "Sorry Something Wrong" + e, Toast.LENGTH_SHORT).show()
                }


                Toast.makeText(context, "Saved: $ETZikirName\n$ETZikirCount Times", Toast.LENGTH_SHORT).show()

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

    override fun zikirClick(zikircount: ZikirCount) {
        val zikirname = zikircount.name
        val zikirno = zikircount.number
        binding.ZikirName.setText(zikirname)

        score = zikirno
        setvalue(score)


        binding.mylinear.visibility = View.VISIBLE
        binding.zikirnumcard.visibility = View.VISIBLE
        binding.zikirbuttoncard.visibility = View.VISIBLE
        binding.myRelativeLayout.visibility = View.GONE

    }


}