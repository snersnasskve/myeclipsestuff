package com.sners.ramblerswalks4

//import androidx.navigation.Navigation
//import androidx.navigation.findNavController
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.sners.ramblerswalks4.controller.SearchManager
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

    private var daysSelected : String = "None"
    private var distanceSelected : String = "None"


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

        var view = inflater.inflate(R.layout.fragment_main, container, false)

            return view
}

    //--------------------------------------------------------------------
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addButtonListeners(view)

        days_label.text = this.daysSelected
         distance_label.text = this.distanceSelected

    }
    //--------------------------------------------------------------------
    private fun addButtonListeners(view: View?) {
        manage_groups_button?.setOnClickListener {
            //  All fragments and activities have access to navigation
            Timber.i("Group button preseed called from karen")

            val ft = fragmentManager!!.beginTransaction()
            ft.replace(R.id.container, GroupsFragment.newInstance()).addToBackStack(null)
            ft.commit()
}

       // val distanceButton = view?.findViewById<Button>(R.id.manage_distance_button)
        manage_distance_button?.setOnClickListener {
            //  All fragments and activities have access to navigation
            Timber.i("Distance button preseed called from karen")
            val ft = fragmentManager!!.beginTransaction()
            ft.replace(R.id.container, DistanceFragment.newInstance()).addToBackStack(null)
            ft.commit()
        }

       // val daysButton = view?.findViewById<Button>(R.id.manage_days_button)
        manage_days_button?.setOnClickListener {
            //  All fragments and activities have access to navigation
            Timber.i("Days button preseed called from karen")
            val ft = fragmentManager!!.beginTransaction()
            ft.replace(R.id.container, DaysFragment.newInstance()).addToBackStack(null)
            ft.commit()
        }
    }



}
