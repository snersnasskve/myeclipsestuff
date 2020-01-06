package com.sners.ramblerswalks4

//import androidx.navigation.Navigation
//import androidx.navigation.findNavController
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sners.ramblerswalks4.controller.SearchManager
import com.sners.ramblerswalks4.data.DAYS
import com.sners.ramblerswalks4.data.MIN_DIST
import com.sners.ramblerswalks4.data.RamblersViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import timber.log.Timber



/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [MainFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
        //  A companion is the Controller part of this class.
        //  The data model is the data part

        //  newInstance is a singleton
        //  I now have a problem because view is never supposed to talk direct to data
        //  But ... ok I suppose there is a clue there

    }

    private lateinit var search: SearchManager
    private lateinit var viewModel : RamblersViewModel

    private lateinit var daysSelected : String
    private lateinit var distanceSelected : String




    //--------------------------------------------------------------------
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Timber.i("onCreateView called from karen")

        //  Setup search controller - make it become a lifecycle observer
        //      Then it will automatically know when stuff happens and it needs to save data
        this.search = SearchManager(this.lifecycle)

        //  Add the ViewModel for this fragment
        viewModel = ViewModelProviders.of(this).get(RamblersViewModel::class.java)

        val view = inflater.inflate(R.layout.fragment_main, container, false)

        // Restor last values
        //  view model actually saves instance state. Replace this with
        //  https://www.raywenderlich.com/2705552-introduction-to-android-activities-with-kotlin/
        //  Something from this link - he does prefs and stuff
        daysSelected = savedInstanceState?.getString(MIN_DIST)
            ?: getString(R.string.none)
        distanceSelected = savedInstanceState?.getString(DAYS)
            ?: getString(R.string.none)

            return view
}

    //--------------------------------------------------------------------
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addButtonListeners(view)
        Timber.i("onViewCreated from MainFrag from Karen")

         viewModel.daysDescription.observe(this, Observer{newValue ->
            days_label.text = newValue
         })
        viewModel.distanceDescription.observe(this, Observer{newValue ->
            distance_label.text = newValue
        })

        //    days_label.text = this.daysSelected
       //  distance_label.text = this.distanceSelected

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Timber.i("onAttach from MainFrag from Karen")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.i("onCreate from MainFrag from Karen")
    }

    override fun onStart() {
        super.onStart()
        Timber.i("onStart from MainFrag from Karen")
    }

    override fun onResume() {
        super.onResume()
        Timber.i("onResume from MainFrag from Karen")

    }


    //--------------------------------------------------------------------
    private fun addButtonListeners(view: View?) {
        manage_groups_button?.setOnClickListener {
            //  All fragments and activities have access to navigation
            Timber.i("Group button preseed called from karen")
            val groupsFragment = GroupsFragment.newInstance()
            val ft = fragmentManager!!.beginTransaction()
            ft.replace(R.id.container, groupsFragment).addToBackStack(null)
            ft.commit()
}

       // val distanceButton = view?.findViewById<Button>(R.id.manage_distance_button)
        manage_distance_button?.setOnClickListener {
            //  All fragments and activities have access to navigation
            Timber.i("Distance button preseed called from karen")
            val distanceFragment = DistanceFragment.newInstance()
            distanceFragment.reportBackFragment = this
            val ft = fragmentManager!!.beginTransaction()
            ft.replace(R.id.container, distanceFragment).addToBackStack(null)
            ft.commit()
        }

       // val daysButton = view?.findViewById<Button>(R.id.manage_days_button)
        manage_days_button?.setOnClickListener {
            //  All fragments and activities have access to navigation
            Timber.i("Days button preseed called from karen")
            val daysFragment = DaysFragment.newInstance()
            daysFragment.reportBackFragment = this
            daysFragment.daysDescription = this.viewModel.daysDescription.value.toString()
            val ft = fragmentManager!!.beginTransaction()
            ft.replace(R.id.container, daysFragment).addToBackStack(null)
            ft.commit()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        //  Add data items here
        outState.putString(MIN_DIST, this.distanceSelected)
        outState.putString(DAYS, this.daysSelected)
        super.onSaveInstanceState(outState)

    }

    public fun setDaysDescription(desc: String)
    {
        this.viewModel.setDaysDescription(desc)
    }

    public fun setDistanceDescription(desc: String)
    {
        this.viewModel.setDistanceDescription(desc)
    }


}
