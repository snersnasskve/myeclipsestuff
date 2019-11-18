package com.sners.ramblerswalks4

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.OneShotPreDrawListener.add
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
//import androidx.navigation.Navigation
//import androidx.navigation.findNavController
import com.sners.ramblerswalks4.Controller.SearchManager
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

    private lateinit var search: SearchManager

    //  https://medium.com/thoughts-overflow/how-to-add-a-fragment-in-kotlin-way-73203c5a450b
    //  It is not clear where this function should go
//    inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
//        val fragmentTransaction = beginTransaction()
//        fragmentTransaction.func()
//        fragmentTransaction.commit()
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Timber.i("onCreateView called from karen")

        //  Setup search controller - make it become a lifecycle observer
        //      Then it will automatically know when stuff happens and it needs to save data
        this.search = SearchManager(this.lifecycle)

        var view = addButtonListeners(inflater, container)

        return view
}

    //--------------------------------------------------------------------
    private fun addButtonListeners(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_main, container, false)
        val groupButton = view?.findViewById<Button>(R.id.manage_groups_button)
        groupButton?.setOnClickListener {
            //  All fragments and activities have access to navigation
            Timber.i("Group button preseed called from karen")

//            val ft = fragmentManager!!.beginTransaction()
//            ft.replace(R.layout.fragment_groups, GroupsFragment(), "GroupsFragmentTag")
//            ft.commit()

            //  Using a kotlin extension function - on of the dependencies - just tell view to sort it
           // view.findNavController().navigate(R.id.action_mainFragment4_to_groupsFragment)
            //  Go direct to Navigation to create the on click listener and manage it
            //  The navigation thing doesn't work. This will be because I have missed a dependency somewhere
            //  Works just as well without
            // Navigation.createNavigateOnClickListener(R.id.action_mainFragment4_to_groupsFragment)
            val frag=GroupsFragment.newInstance()
            (activity as MainActivity).replaceFragment(frag,MainFragment.TAG)        }

        val distanceButton = view?.findViewById<Button>(R.id.manage_distance_button)
        distanceButton?.setOnClickListener {
            //  All fragments and activities have access to navigation
            Timber.i("Distance button preseed called from karen")
           // view.findNavController().navigate(R.id.action_mainFragment4_to_distanceFragment)
            // Navigation.createNavigateOnClickListener(R.id.action_mainFragment4_to_distanceFragment)
        }

        val daysButton = view?.findViewById<Button>(R.id.manage_days_button)
        daysButton?.setOnClickListener {
            //  All fragments and activities have access to navigation
            Timber.i("Days button preseed called from karen")
           // view.findNavController().navigate(R.id.action_mainFragment4_to_daysFragment)
            // Navigation.createNavigateOnClickListener(R.id.action_mainFragment4_to_daysFragment)
        }
        return view
    }

    companion object {
        val TAG = MainFragment::class.java.simpleName
        @JvmStatic
        fun newInstance() = MainFragment()
    }

}
