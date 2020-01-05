package com.sners.ramblerswalks4


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sners.ramblerswalks4.data.DistanceViewModel
import com.sners.ramblerswalks4.data.SLIDER_MAX
import com.sners.ramblerswalks4.data.SLIDER_MIN
import kotlinx.android.synthetic.main.fragment_distance.*
import timber.log.Timber


/**
 * A simple [Fragment] subclass.
 */
class DistanceFragment : Fragment() {


    companion object {
        fun newInstance() = DistanceFragment()
    }


    private lateinit var viewModel : DistanceViewModel
    lateinit var reportBackFragment: MainFragment

    //--------------------------------------------------------------------
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //  Add the ViewModel for this fragment
        viewModel = ViewModelProviders.of(this).get(DistanceViewModel::class.java)

        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_distance, container, false)
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
                this@DistanceFragment.viewModel.sliderChanged(SLIDER_MIN, this.progressChangedValue)

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
                this@DistanceFragment.viewModel.sliderChanged(SLIDER_MAX, this.progressChangedValue)

            }
        })
        distance_back_button?.setOnClickListener {
            backButtonPressed()
        }
    }

    override fun onDestroy() {
        this.reportBackFragment.setDistanceDescription(this.viewModel.description())
        super.onDestroy()
    }

    private fun backButtonPressed() {
        Timber.i("Days back button preseed called from karen")

//        var mainFragment = MainFragment.newInstance()
//        mainFragment.distanceSelected = viewModel.description()
//        //  Of course this stomps over any other fields so need to figure
//        //mainFragment.daysSelected = viewModel.description()
//
//        //  Add to stored memory
//
//        val ft = fragmentManager!!.beginTransaction()
//        ft.replace(R.id.container, mainFragment)
//        ft.commitNow()
        fragmentManager!!.popBackStack()

    }



}
