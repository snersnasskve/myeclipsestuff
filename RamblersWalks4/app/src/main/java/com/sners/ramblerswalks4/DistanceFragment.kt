package com.sners.ramblerswalks4


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sners.ramblerswalks4.data.DistanceViewModel
import kotlinx.android.synthetic.main.fragment_distance.*
import timber.log.Timber


/**
 * A simple [Fragment] subclass.
 */
class DistanceFragment : Fragment() {

    companion object {
        fun newInstance() = DistanceFragment()
        var distancDescription = "None"
    }

//    lateinit var minDistanceSeekBar: SeekBar
//    lateinit var maxDistanceSeekBar: SeekBar
//    lateinit var minDistanceView: TextView
//    lateinit var maxDistanceView: TextView

    var minDistanceValue = 0
    var maxDistanceValue = 0

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


        return view
    }

    //--------------------------------------------------------------------
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//

        viewModel.minDistance.observe(this, Observer{newValue ->
            minimum_distance_value.text = newValue.toString()
            seekBarMinimum.progress = newValue
        })
        viewModel.maxDistance.observe(this, Observer{newValue ->
            maximum_distance_value.text = newValue.toString()
            seekBarMaximum.progress = newValue
        })


        seekBarMinimum.progress = viewModel.minDistance.value!!
        seekBarMaximum.progress = viewModel.maxDistance.value!!

        seekBarMinimum.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            var progressChangedValue = 0
            override fun onProgressChanged(
                seekBar: SeekBar,
                progress: Int,
                fromUser: Boolean
            ) {
                progressChangedValue = progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) { // TODO Auto-generated method stub
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                this@DistanceFragment.viewModel.sliderChanged("min", this.progressChangedValue)

            }
        })

        seekBarMaximum.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            var progressChangedValue = 0
            override fun onProgressChanged(
                seekBar: SeekBar,
                progress: Int,
                fromUser: Boolean
            ) {
                progressChangedValue = progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) { // TODO Auto-generated method stub
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                this@DistanceFragment.viewModel.sliderChanged("max", this.progressChangedValue)

            }
        })
        distance_back_button?.setOnClickListener {
            backButtonPressed()
        }
    }
    private fun backButtonPressed() {
        Timber.i("Days back button preseed called from karen")

        var mainFragment = MainFragment.newInstance()
        mainFragment.distanceSelected = viewModel.description()
        //  Of course this stomps over any other fields so need to figure
        //mainFragment.daysSelected = viewModel.description()

        //  Add to stored memory

        val ft = fragmentManager!!.beginTransaction()
        ft.replace(R.id.container, mainFragment)
        ft.commitNow()
    }



}
