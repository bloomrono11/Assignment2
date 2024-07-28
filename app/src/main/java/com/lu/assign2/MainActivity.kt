package com.lu.assign2

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.lu.assign2.cons.FixedValues
import com.lu.assign2.cons.FixedValues.Companion.AREA
import com.lu.assign2.cons.FixedValues.Companion.INTENT
import com.lu.assign2.cons.FixedValues.Companion.NO_OPTION
import com.lu.assign2.cons.FixedValues.Companion.WEATHER
import com.lu.assign2.dialog.InstructionDialogFrag
import com.lu.assign2.dialog.MovieDialogFrag
import com.lu.assign2.frag.AreaFragment
import com.lu.assign2.frag.IntentFragment
import com.lu.assign2.frag.WeatherFragment

class MainActivity : AppCompatActivity() {

    private val options = arrayOf(NO_OPTION, AREA, WEATHER, INTENT)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val context = this

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val spFrag: Spinner = findViewById(R.id.spFragOptions)
        val arrayAdapter = ArrayAdapter(context, R.layout.spinner_item, options)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spFrag.adapter = arrayAdapter

        //How to customize spinner
        // https://www.broculos.net/2013/09/how-to-change-spinner-text-size-color.html
        spFrag.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                val chosen = options[position]
                var frag: Fragment? = null
                //keep the fragment stack empty so if no selection is made we clear the stack
                supportFragmentManager.popBackStack()
                when (chosen) {
                    AREA -> {
                        frag = AreaFragment()
                    }

                    WEATHER -> {
                        frag = WeatherFragment()
                    }

                    INTENT -> {
                        frag = IntentFragment()
                    }

                    else -> {
                        supportFragmentManager.popBackStack()
                    }

                }
                if (frag == null) return

                try {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameView, frag)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null)
                        .commit()
                } catch (ex:RuntimeException){
                    Log.e(TAG, ex.stackTraceToString())
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(context, FixedValues.CHOOSE_OPTION, Toast.LENGTH_SHORT).show()
            }

        }

        //Back button press handlers
        onBackPressedDispatcher.addCallback(context, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                myHandleBackPressed()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_options, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.movieItem -> {
                val dialog = MovieDialogFrag()
                dialog.show(supportFragmentManager, "Movie Rating Dialog")
                Toast.makeText(this, "Movie Rating Item", Toast.LENGTH_SHORT).show()
            }

            R.id.instItem -> {
                val dialog = InstructionDialogFrag()
                dialog.show(supportFragmentManager, "Instruction Feedback Dialog")
                Toast.makeText(this, "Instruction Feedback Item", Toast.LENGTH_SHORT).show()
            }

            R.id.item3 -> {
                Toast.makeText(this, "Item 3", Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }

    /**
     * Alternative to {@link #onBackPressed}
     * https://stackoverflow.com/questions/72634225/onbackpressed-is-deprecated-what-is-the-alternative
     * Below is how to do Alert Dialog
     * https://www.youtube.com/watch?v=VAvtYh_Dwdg&ab_channel=coursecode
     */
    fun myHandleBackPressed() {
        AlertDialog.Builder(this)
            .setTitle("Alert Dialog")
            .setMessage("Do you really want to quit")
            .setCancelable(false)
            .setPositiveButton("Yes") { _, _ -> finish() }
            .setNegativeButton("No") { dialog, _ -> dialog.cancel() }
            .create()
            .show()
    }

    fun receiveFeedback(text: CharSequence?) {
        val feedbackTV: TextView = findViewById(R.id.feedbackTV)
        if (text.isNullOrBlank()) {
            feedbackTV.visibility = View.INVISIBLE
            return
        }
        feedbackTV.text = text
        feedbackTV.visibility = View.VISIBLE
    }

    companion object{
        const val TAG = "MainActivity"
    }

}
