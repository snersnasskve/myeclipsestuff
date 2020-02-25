package com.sners.ramblerswalks4


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.sners.ramblerswalks4.data.GroupsViewModel
import com.sners.ramblerswalks4.data.RamblersWalksVMFactory
import timber.log.Timber
import kotlinx.android.synthetic.main.fragment_groups.*

/**
 * A simple [Fragment] subclass.
 */
class GroupsFragment : Fragment() {

    companion object {
        fun newInstance() = GroupsFragment()
    }

    private lateinit var viewModel: GroupsViewModel
    private lateinit var spinnerArrayAdapter: ArrayAdapter<String>

    //--------------------------------------------------------------------
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //  Add the ViewModel for this fragment
        val application = requireNotNull(activity).application
        val viewModelFactory = RamblersWalksVMFactory(application)
        viewModel = ViewModelProvider(this).get(GroupsViewModel::class.java)


        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_groups, container, false)


        val backButton = view?.findViewById<Button>(R.id.group_back_button)
        backButton?.setOnClickListener {
            //  All fragments and activities have access to navigation
            Timber.i("Group back button preseed called from karen")

            val ft = fragmentManager!!.beginTransaction()
            ft.replace(R.id.container, MainFragment.newInstance())
            ft.commitNow()
        }
        return view
    }

    //--------------------------------------------------------------------
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //https://stackoverflow.com/questions/3283337/how-to-update-a-spinner-dynamically
        viewModel.areaNames.observe(this.viewLifecycleOwner, Observer { newValue ->
            spinnerArrayAdapter =
                ArrayAdapter(activity, android.R.layout.simple_spinner_item, newValue)
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            groupNameSpinner.adapter = spinnerArrayAdapter
        })

        groupNameSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Snackbar.make(view, "Please select a group", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }

            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                Timber.i(
                    "Karen has a position: " + (viewModel.areaNames.value?.get(position)
                        ?: "... or maybe not")
                )
            }

        }

//https://stackoverflow.com/questions/53973556/forward-one-fragment-to-another-fragment-in-kotlin
//companion object {
//    val TAG = GroupsFragment::class.java.simpleName
//    @JvmStatic
//    fun newInstance() = GroupsFragment()
//}
    }
}
