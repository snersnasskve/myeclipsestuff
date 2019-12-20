package com.sners.ramblerswalks4


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsSeekBar
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import com.sners.ramblerswalks4.data.DistanceViewModel
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class DistanceFragment : Fragment() {

    companion object {
        fun newInstance() = DistanceFragment()
        var distancDescription = "None"
    }

    lateinit var minDistanceSeekBar: AbsSeekBar
    lateinit var maxDistanceSeekBar: AbsSeekBar
    lateinit var minDistance: TextView
    lateinit var maxDistance: TextView

    var desription = "Any distance"

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

        this.minDistance = view.findViewById(R.id.minimum_distance_value)
        this.maxDistance = view.findViewById(R.id.maximum_distance_value)
        this.minDistanceSeekBar = view.findViewById(R.id.seekBarMinimum)
        this.maxDistanceSeekBar = view.findViewById(R.id.seekBarMaximum)

        val backButton = view?.findViewById<Button>(R.id.distance_back_button)
        backButton?.setOnClickListener {
            backButtonPressed()
        }
        return view
    }

    private fun backButtonPressed() {
        Timber.i("Days back button preseed called from karen")

        var mainFragment = MainFragment.newInstance()
        mainFragment.distanceSelected = distancDescription
        //  Of course this stomps over any other fields so need to figure
        //mainFragment.daysSelected = viewModel.description()

        //  Add to stored memory

        val ft = fragmentManager!!.beginTransaction()
        ft.replace(R.id.container, mainFragment)
        ft.commitNow()
    }
}
