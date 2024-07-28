package com.lu.assign2.frag

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.AlarmClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.lu.assign2.R


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [IntentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class IntentFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var pkgManager:PackageManager

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
        val inflate = inflater.inflate(R.layout.frag_intent, container, false)
        pkgManager = inflate.context.packageManager

        val alarmIntentBtn: Button = inflate.findViewById(R.id.alarmBtn)
        val mapIntentBtn: Button = inflate.findViewById(R.id.mapBtn)
        val locationBtn: Button = inflate.findViewById(R.id.directionBtn)
        //Todo call the intents based on the button clicks

        alarmIntentBtn.setOnClickListener { callAlarmIntent(inflate.context) }
        mapIntentBtn.setOnClickListener { callMapIntent(inflate.context) }
        locationBtn.setOnClickListener { directionBetween2Loc(inflate.context) }
        return inflate
    }

    private fun callAlarmIntent(context: Context) {
        val i = Intent(AlarmClock.ACTION_SET_ALARM)
        i.putExtra(AlarmClock.EXTRA_MESSAGE, "New Alarm")
        i.putExtra(AlarmClock.EXTRA_HOUR, 10)
        i.putExtra(AlarmClock.EXTRA_MINUTES, 30)
        startActivity(i)
    }

    /**
     * https://developers.google.com/maps/documentation/urls/android-intents#kotlin
     */
    private fun callMapIntent(context: Context) {
        // Create a Uri from an intent string. Use the result to create an Intent.
        val mapIntentUri = Uri.parse("google.streetview:cbll=46.414382,10.013988")

        // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
        val mapIntent = Intent(Intent.ACTION_VIEW, mapIntentUri)

        // Make the Intent explicit by setting the Google Maps package
        mapIntent.setPackage("com.google.android.apps.maps")

        // Attempt to start an activity that can handle the Intent
        mapIntent.resolveActivity(pkgManager)?.let {
            startActivity(mapIntent)
        }
    }

    private fun directionBetween2Loc(context: Context) {

        val srcLat ="48.37367724754728"
        val srcLon="-89.31227499427553"
        val destLat="48.420801866250024"
        val destLon="-89.26020416200309"
        // Create a Uri from an intent string. Open map using intent to show direction between two specific locations
        val mapUri =
            Uri.parse("https://maps.google.com/maps?saddr=$srcLat,$srcLon&daddr=$destLat,$destLon")
        val intent = Intent(Intent.ACTION_VIEW, mapUri)
        // Make the Intent explicit by setting the Google Maps package
        intent.setPackage("com.google.android.apps.maps")
        //intent.resolveActivity(pkgManager)?.let {
            startActivity(intent)
        //}
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment IntentFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            IntentFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
