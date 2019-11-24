package com.sners.ramblerswalks4

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProviders
//import androidx.navigation.Navigation
//import androidx.navigation.findNavController
import com.sners.ramblerswalks4.controller.SearchManager
import com.sners.ramblerswalks4.data.RamblersViewModel
import timber.log.Timber

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"



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
    }

    private lateinit var search: SearchManager
    private lateinit var viewModel : RamblersViewModel

    //  https://medium.com/thoughts-overflow/how-to-add-a-fragment-in-kotlin-way-73203c5a450b
    //  It is not clear where this function should go
//    inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
//        val fragmentTransaction = beginTransaction()
//        fragmentTransaction.func()
//        fragmentTransaction.commit()
//    }

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

        addButtonListeners(view)

        return view
}

    //--------------------------------------------------------------------
    private fun addButtonListeners(view: View?) {
        val groupButton = view?.findViewById<Button>(R.id.manage_groups_button)
        groupButton?.setOnClickListener {
            //  All fragments and activities have access to navigation
            Timber.i("Group button preseed called from karen")

            val ft = fragmentManager!!.beginTransaction()
            ft.replace(R.id.container, GroupsFragment.newInstance())
            ft.commitNow()
}

        val distanceButton = view?.findViewById<Button>(R.id.manage_distance_button)
        distanceButton?.setOnClickListener {
            //  All fragments and activities have access to navigation
            Timber.i("Distance button preseed called from karen")
            val ft = fragmentManager!!.beginTransaction()
            ft.replace(R.id.container, DistanceFragment.newInstance())
            ft.commitNow()
        }

        val daysButton = view?.findViewById<Button>(R.id.manage_days_button)
        daysButton?.setOnClickListener {
            //  All fragments and activities have access to navigation
            Timber.i("Days button preseed called from karen")
            val ft = fragmentManager!!.beginTransaction()
            ft.replace(R.id.container, DaysFragment.newInstance())
            ft.commitNow()
        }
    }



}
