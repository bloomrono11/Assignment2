package com.lu.assign2.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.lu.assign2.MainActivity
import com.lu.assign2.R

/**
 * @author : bloomrono11@gmail.com
 * @company : Personal
 * created    : 7/25/2024, Thursday
 * @copyright : None
 */
class MovieDialogFrag: DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, bundle: Bundle?): View {

        val rootView: View = inflater.inflate(R.layout.movie_rating_dialog, container, false)

        val radioGrp: RadioGroup = rootView.findViewById(R.id.movieRadioGroup)

        val cancelBtn: Button = rootView.findViewById(R.id.dismissBtn)
        cancelBtn.setOnClickListener {
            val mainActivity: MainActivity = activity as MainActivity
            mainActivity.receiveFeedback(null)
            dismiss()
        }
        val submitBtn: Button = rootView.findViewById(R.id.confirmBtn)
        submitBtn.setOnClickListener {

            val radioSelected: RadioButton = rootView.findViewById(radioGrp.checkedRadioButtonId)

            Toast.makeText(context, radioSelected.text, Toast.LENGTH_LONG).show()
            dismiss()
            val mainActivity:MainActivity = activity as MainActivity
            mainActivity.receiveFeedback(radioSelected.text)
        }
        return rootView
    }
}
