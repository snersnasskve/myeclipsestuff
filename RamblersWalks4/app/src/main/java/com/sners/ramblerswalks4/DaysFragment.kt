package com.sners.ramblerswalks4


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class DaysFragment : Fragment() {

    companion object {
        fun newInstance() = DaysFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_days, container, false)

        val backButton = view?.findViewById<Button>(R.id.days_back_button)
        backButton?.setOnClickListener {
            //  All fragments and activities have access to navigation
            Timber.i("Days back button preseed called from karen")

            val ft = fragmentManager!!.beginTransaction()
            ft.replace(R.id.container, MainFragment.newInstance())
            ft.commitNow()
        }
        return view
    }


}
