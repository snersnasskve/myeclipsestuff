package com.sners.ramblerswalks4


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.CompoundButton
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sners.ramblerswalks4.data.DaysViewModel
import kotlinx.android.synthetic.main.fragment_days.*
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class DaysFragment : Fragment(), CompoundButton.OnCheckedChangeListener {

//    lateinit var monday_checkbox : CheckBox
//    lateinit var tuesday_checkbox : CheckBox
//    lateinit var wednesday_checkbox : CheckBox
//    lateinit var thursday_checkbox : CheckBox
//    lateinit var friday_checkbox : CheckBox
//    lateinit var saturday_checkbox : CheckBox
//    lateinit var sunday_checkbox : CheckBox
//    lateinit var weekdays_checkbox : CheckBox
//    lateinit var weekends_checkbox : CheckBox
//    lateinit var everyday_checkbox : CheckBox

    //var description = "Any Day"

    companion object {
        fun newInstance() = DaysFragment()

        var daysDescription = "None"


    }

    private lateinit var viewModel : DaysViewModel
    //--------------------------------------------------------------------
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //  Add the ViewModel for this fragment
        viewModel = ViewModelProviders.of(this).get(DaysViewModel::class.java)

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_days, container, false)

//        }
        return view
    }

    //--------------------------------------------------------------------
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        monday_checkbox.setOnCheckedChangeListener(this)
        tuesday_checkbox.setOnCheckedChangeListener(this)
        wednesday_checkbox.setOnCheckedChangeListener(this)
        thursday_checkbox.setOnCheckedChangeListener(this)
        friday_checkbox.setOnCheckedChangeListener(this)
        saturday_checkbox.setOnCheckedChangeListener(this)
        sunday_checkbox.setOnCheckedChangeListener(this)
        weekdays_checkbox.setOnCheckedChangeListener(this)
        weekends_checkbox.setOnCheckedChangeListener(this)
        everyday_checkbox.setOnCheckedChangeListener(this)

        viewModel.monday.observe(this, Observer{newValue ->
            monday_checkbox.isChecked = newValue
        })
        viewModel.tuesday.observe(this, Observer{newValue ->
            tuesday_checkbox.isChecked = newValue
        })
        viewModel.wednesday.observe(this, Observer{newValue ->
            wednesday_checkbox.isChecked = newValue
        })
        viewModel.thursday.observe(this, Observer{newValue ->
            thursday_checkbox.isChecked = newValue
        })
        viewModel.friday.observe(this, Observer{newValue ->
            friday_checkbox.isChecked = newValue
        })
        viewModel.saturday.observe(this, Observer{newValue ->
            saturday_checkbox.isChecked = newValue
        })
        viewModel.sunday.observe(this, Observer{newValue ->
            sunday_checkbox.isChecked = newValue
        })
        viewModel.weekdays.observe(this, Observer{newValue ->
            weekdays_checkbox.isChecked = newValue
        })
        viewModel.weekend.observe(this, Observer{newValue ->
            weekends_checkbox.isChecked = newValue
        })
        viewModel.everyday.observe(this, Observer{newValue ->
            everyday_checkbox.isChecked = newValue
        })


        days_back_button.setOnClickListener {
            //  All fragments and activities have access to navigation
            backButtonPressed()
        }
    }
    //--------------------------------------------------------------------
    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        Timber.i("tickbox changed from karen")
        val tickedName = buttonView?.text
        viewModel.tickBoxChanged(tickedName as String, isChecked)
        daysDescription = viewModel.daysDescription
    }

//    override fun onStop() {
//        super.onStop()
//        backButtonPressed()
//    }

    private fun backButtonPressed() {
        Timber.i("Days back button preseed called from karen")

        var mainFragment = MainFragment.newInstance()
        mainFragment.daysSelected = daysDescription
        //  Of course this stomps over any other fields so need to figure
        //mainFragment.daysSelected = viewModel.description()

        //  Add to stored memory

        val ft = fragmentManager!!.beginTransaction()
        ft.replace(R.id.container, mainFragment)
        ft.commitNow()
    }


}
