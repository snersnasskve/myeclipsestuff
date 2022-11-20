package com.sners.ramblerswalks4


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sners.ramblerswalks4.controller.DaysController
import com.sners.ramblerswalks4.data.DaysViewModel
import com.sners.ramblerswalks4.data.RamblersWalksVMFactory
import kotlinx.android.synthetic.main.fragment_days.*
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class DaysFragment : Fragment(), CompoundButton.OnCheckedChangeListener {



    companion object {
        fun newInstance() = DaysFragment()
    }

    private lateinit var viewModel : DaysViewModel
    lateinit var reportBackFragment: MainFragment

    lateinit var daysDescription : String

    //--------------------------------------------------------------------
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //  Add the ViewModel for this fragment
        val application = requireNotNull(activity).application
        val viewModelFactory = RamblersWalksVMFactory(application)
        viewModel =  ViewModelProvider(this).get(DaysViewModel::class.java)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_days, container, false)

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

        viewModel.setDaysFromArray(DaysController.daysFromDescription(this.daysDescription))

        viewModel.monday.observe(this.viewLifecycleOwner, Observer{newValue ->
            monday_checkbox.isChecked = newValue
        })
        viewModel.tuesday.observe(viewLifecycleOwner, Observer{newValue ->
            tuesday_checkbox.isChecked = newValue
        })
        viewModel.wednesday.observe(this.viewLifecycleOwner, Observer{newValue ->
            wednesday_checkbox.isChecked = newValue
        })
        viewModel.thursday.observe(this.viewLifecycleOwner, Observer{newValue ->
            thursday_checkbox.isChecked = newValue
        })
        viewModel.friday.observe(this.viewLifecycleOwner, Observer{newValue ->
            friday_checkbox.isChecked = newValue
        })
        viewModel.saturday.observe(this.viewLifecycleOwner, Observer{newValue ->
            saturday_checkbox.isChecked = newValue
        })
        viewModel.sunday.observe(this.viewLifecycleOwner, Observer{newValue ->
            sunday_checkbox.isChecked = newValue
        })
        viewModel.weekdays.observe(this.viewLifecycleOwner, Observer{newValue ->
            weekdays_checkbox.isChecked = newValue
        })
        viewModel.weekend.observe(this.viewLifecycleOwner, Observer{newValue ->
            weekends_checkbox.isChecked = newValue
        })
        viewModel.everyday.observe(this.viewLifecycleOwner, Observer{newValue ->
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
        //daysDescription = viewModel.daysDescription
    }



    override fun onDestroy() {
        Timber.i("onDestroy from DaysFrag from Karen")
        this.reportBackFragment.setDaysDescription(DaysController.daysDescription(viewModel.getDaysArray()))
        super.onDestroy()
    }

    override fun onDetach() {
        Timber.i("onDetach from DaysFrag from Karen")
        super.onDetach()
    }

    private fun backButtonPressed() {
        Timber.i("Days back button preseed called from karen")


        val ft = fragmentManager!!.popBackStackImmediate()
    }


}
