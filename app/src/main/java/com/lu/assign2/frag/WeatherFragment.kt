package com.lu.assign2.frag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.fragment.app.Fragment
import com.lu.assign2.R
import com.lu.assign2.cons.FixedValues
import com.lu.assign2.cons.FixedValues.Companion.CEL_TO_FAH
import com.lu.assign2.cons.FixedValues.Companion.CEL_TO_KEL
import com.lu.assign2.cons.FixedValues.Companion.FAH_TO_CEL
import com.lu.assign2.cons.FixedValues.Companion.KEL_TO_CEL
import com.lu.assign2.cons.FixedValues.Companion.RECT

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WeatherFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WeatherFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val options = arrayOf(
        CEL_TO_FAH,
        FAH_TO_CEL,
        CEL_TO_KEL,
        KEL_TO_CEL
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
        val inflate = inflater.inflate(R.layout.frag_weather, container, false)
        val areaSpinner = inflate.findViewById<Spinner>(R.id.spWeather)
        val adapter = ArrayAdapter(inflater.context, android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        areaSpinner.adapter = adapter

        val subHeadTV: TextView = inflate.findViewById(R.id.weatherFragSubHeadTV)
        subHeadTV.text = RECT
        var optionChosen = RECT

        val edTxt1: EditText = inflate.findViewById(R.id.edTxt1)

        val areaCalBtn: Button = inflate.findViewById(R.id.weatherResultBtn)
        val weatherResTV: TextView = inflate.findViewById(R.id.weatherResultTV)

        areaSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                subHeadTV.text = options[position]
                optionChosen = options[position]

                when (optionChosen) {
                    CEL_TO_FAH -> {
                        edTxt1.hint = "value in celsius"
                    }

                    FAH_TO_CEL -> {
                        edTxt1.hint = "value in fahrenheit"
                    }

                    CEL_TO_KEL -> {
                        edTxt1.hint = "value in celsius"
                    }

                    KEL_TO_CEL -> {
                        edTxt1.hint = "value in kelvin"
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(inflate.context, FixedValues.CHOOSE_OPTION, Toast.LENGTH_SHORT).show()
            }

        }

        areaCalBtn.setOnClickListener {
            val inputVal = edTxt1.text.toString().toDouble()
            val value: Double?
            when (optionChosen) {
                CEL_TO_FAH -> {
                    value = ((inputVal * 9) / 5) + 32
                    weatherResTV.text = "Fahrenheit is " + String.format("%.2f", value) + " F"
                }

                FAH_TO_CEL -> {
                    value = ((inputVal - 32) * 5) / 9
                    weatherResTV.text = "Celsius is " + String.format("%.2f", value) + " C"
                }

                CEL_TO_KEL -> {
                    value = inputVal + 273.15
                    weatherResTV.text = "Kelvin is " + String.format("%.2f", value) + " K"
                }

                KEL_TO_CEL -> {
                    value = inputVal - 273.15
                    weatherResTV.text = "Celsius is " + String.format("%.2f", value) + " C"
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
         * @return A new instance of fragment WeatherFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WeatherFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
