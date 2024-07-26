package com.lu.assign2.frag

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.fragment.app.Fragment
import com.lu.assign2.R
import com.lu.assign2.cons.FixedValues
import com.lu.assign2.cons.FixedValues.Companion.CIRCLE
import com.lu.assign2.cons.FixedValues.Companion.RECT
import com.lu.assign2.cons.FixedValues.Companion.SQUARE
import com.lu.assign2.cons.FixedValues.Companion.TRIANGLE

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AreaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AreaFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val TAG: String = "AreaFragment"

    private val options = arrayOf(
        RECT,
        SQUARE,
        CIRCLE,
        TRIANGLE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val inflate = inflater.inflate(R.layout.frag_area, container, false)

        val areaSpinner = inflate.findViewById<Spinner>(R.id.spArea)
        val adapter = ArrayAdapter(inflater.context, android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        areaSpinner.adapter = adapter

        val subHeadTV: TextView = inflate.findViewById(R.id.fragAreaSubHeadTV)
        subHeadTV.text = RECT
        var optionChosen = RECT

        val edTxt1: EditText = inflate.findViewById(R.id.edTxt1)
        val edTxt2: EditText = inflate.findViewById(R.id.edTxt2)

        val areaCalBtn: Button = inflate.findViewById(R.id.areaResultBtn)
        val areaResultTV: TextView = inflate.findViewById(R.id.areaResultTV)

        areaSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                subHeadTV.text = options[position]
                optionChosen = options[position]

                when (optionChosen) {
                    RECT, TRIANGLE -> {
                        edTxt2.visibility = View.VISIBLE
                    }

                    SQUARE, CIRCLE -> {
                        edTxt2.visibility = View.INVISIBLE
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(inflate.context, FixedValues.CHOOSE_OPTION, Toast.LENGTH_SHORT).show()
            }

        }

        areaCalBtn.setOnClickListener {
            val side: Double?
            try {
                side = edTxt1.text.toString().toDouble()
            } catch (ex: RuntimeException) {
                Log.e(TAG, ex.stackTraceToString())
                return@setOnClickListener
            }

            when (optionChosen) {
                RECT -> {
                    val breadth = edTxt2.text.toString().toFloat()
                    val area = side * breadth
                    areaResultTV.text = "Reactable area is " + String.format("%.2f", area) + " sq units"
                }

                SQUARE -> {
                    val area = side * side
                    areaResultTV.text = "Square area is " + String.format("%.2f", area) + " sq units"
                }

                TRIANGLE -> {
                    val height = edTxt2.text.toString().toFloat()
                    val area = 0.5 * side * height
                    areaResultTV.text = "Triangle area is " + String.format("%.2f", area) + " sq units"
                }

                CIRCLE -> {
                    val area = Math.PI * side * side
                    areaResultTV.text = "Circle area is " + String.format("%.2f", area) + " sq units"
                }
            }
        }

        return inflate
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AreaFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AreaFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
