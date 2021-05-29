package com.rsschool.android2021

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import java.lang.NullPointerException
import java.lang.NumberFormatException

class FirstFragment : Fragment() {

    private var generateButton: Button? = null
    private var previousResult: TextView? = null
    private lateinit var activityInterface: MainActivityInterface

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        activityInterface = activity as MainActivityInterface
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = "Previous result: ${result.toString()}"

        // TODO: val min = ...
        // TODO: val max = ...
        val minEditText = view.findViewById<EditText>(R.id.min_value)
        val maxEditText = view.findViewById<EditText>(R.id.max_value)

        generateButton?.setOnClickListener {
            // TODO: send min and max to the SecondFragment

            if (checkParserIntValue(minEditText.text.toString()) &&
                checkParserIntValue(maxEditText.text.toString())
            ) {
                val min: Int = minEditText.text.toString().toInt()
                val max: Int = maxEditText.text.toString().toInt()

                if (checkValue(min, max)) {
                    activityInterface.setSecondFragment(min, max)
                } else {
                    Toast.makeText(
                        view.context,
                        "Max value mast be greater than min, please try value again",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else {
                Toast.makeText(
                    view.context,
                    "Invalid value, please try value again",
                    Toast.LENGTH_LONG
                ).show()
            }
            minEditText.text = null
            maxEditText.text = null

        }
    }

    private fun checkParserIntValue(value: String): Boolean {
        return try {
            value.toInt()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }

    private fun checkValue(min: Int, max: Int): Boolean {
        return (min < max)


    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }
}