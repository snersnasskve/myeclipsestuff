package com.sners.ramblerswalks4

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
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



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Timber.i("onCreateView called from karen")

        var view = inflater.inflate(R.layout.fragment_main, container, false)
        val groupButton = view?.findViewById<Button>(R.id.manage_groups_button)
        groupButton?.setOnClickListener {
            //  All fragments and activities have access to navigation
            Timber.i("Group button preseed called from karen")
            Navigation.findNavController(it).navigate(R.id.action_mainFragment4_to_groupsFragment)
        }

        val distanceButton = view?.findViewById<Button>(R.id.manage_distance_button)
        distanceButton?.setOnClickListener {
            //  All fragments and activities have access to navigation
            Timber.i("Distance button preseed called from karen")
            Navigation.findNavController(it).navigate(R.id.action_mainFragment4_to_distanceFragment)
        }

        val daysButton = view?.findViewById<Button>(R.id.manage_days_button)
        daysButton?.setOnClickListener {
            //  All fragments and activities have access to navigation
            Timber.i("Days button preseed called from karen")
            Navigation.findNavController(it).navigate(R.id.action_mainFragment4_to_daysFragment)
        }

        return view
}


}
