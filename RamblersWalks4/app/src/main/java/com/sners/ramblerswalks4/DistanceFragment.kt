package com.sners.ramblerswalks4


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProviders
import com.sners.ramblerswalks4.data.DistanceViewModel
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class DistanceFragment : Fragment() {

    companion object {
        fun newInstance() = DistanceFragment()
    }

    private lateinit var viewModel : DistanceViewModel
    //--------------------------------------------------------------------
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //  Add the ViewModel for this fragment
        viewModel = ViewModelProviders.of(this).get(DistanceViewModel::class.java)

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_distance, container, false)

        val backButton = view?.findViewById<Button>(R.id.distance_back_button)
        backButton?.setOnClickListener {
            //  All fragments and activities have access to navigation
            Timber.i("Distance back button preseed called from karen")

            val ft = fragmentManager!!.beginTransaction()
            ft.replace(R.id.container, MainFragment.newInstance())
            ft.commitNow()
        }
        return view
    }


}
