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
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class DaysFragment : Fragment(), CompoundButton.OnCheckedChangeListener, LifecycleObserver {

    lateinit var monday_checkbox : CheckBox
    lateinit var tuesday_checkbox : CheckBox
    lateinit var wednesday_checkbox : CheckBox
    lateinit var thursday_checkbox : CheckBox
    lateinit var friday_checkbox : CheckBox
    lateinit var saturday_checkbox : CheckBox
    lateinit var sunday_checkbox : CheckBox
    lateinit var weekdays_checkbox : CheckBox
    lateinit var weekends_checkbox : CheckBox
    lateinit var everyday_checkbox : CheckBox


    companion object {
        fun newInstance() = DaysFragment()
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
        this.monday_checkbox = view.findViewById(R.id.monday_checkbox)
        this.tuesday_checkbox = view.findViewById(R.id.tuesday_checkbox)
        this.wednesday_checkbox = view.findViewById(R.id.wednesday_checkbox)
        this.thursday_checkbox = view.findViewById(R.id.thursday_checkbox)
        this.friday_checkbox = view.findViewById(R.id.friday_checkbox)
        this.saturday_checkbox = view.findViewById(R.id.saturday_checkbox)
        this.sunday_checkbox = view.findViewById(R.id.sunday_checkbox)
        this.weekdays_checkbox = view.findViewById(R.id.weekdays_checkbox)
        this.weekends_checkbox = view.findViewById(R.id.weekdends_checkbox)
        this.everyday_checkbox = view.findViewById(R.id.everyday_checkbox)

        this.monday_checkbox.setOnCheckedChangeListener(this)
        this.tuesday_checkbox.setOnCheckedChangeListener(this)
        this.wednesday_checkbox.setOnCheckedChangeListener(this)
        this.thursday_checkbox.setOnCheckedChangeListener(this)
        this.friday_checkbox.setOnCheckedChangeListener(this)
        this.saturday_checkbox.setOnCheckedChangeListener(this)
        this.sunday_checkbox.setOnCheckedChangeListener(this)
        this.weekdays_checkbox.setOnCheckedChangeListener(this)
        this.weekends_checkbox.setOnCheckedChangeListener(this)
        this.everyday_checkbox.setOnCheckedChangeListener(this)

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

        //this.updateValues()

        val backButton = view?.findViewById<Button>(R.id.days_back_button)
        backButton?.setOnClickListener {
            //  All fragments and activities have access to navigation
            Timber.i("Days back button preseed called from karen")

            var mainFragment = MainFragment.newInstance()
            //  Of course this stomps over any other fields so need to figure
            mainFragment.daysSelected = viewModel.description()
            val ft = fragmentManager!!.beginTransaction()
            ft.replace(R.id.container, mainFragment)
            ft.commitNow()
        }
        return view
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        Timber.i("tickbox changed from karen")
        val tickedName = buttonView?.text
        viewModel.tickBoxChanged(tickedName as String, isChecked)
        //this.updateValues()
    }




}
