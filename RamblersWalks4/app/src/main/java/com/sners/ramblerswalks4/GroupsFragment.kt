package com.sners.ramblerswalks4


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * A simple [Fragment] subclass.
 */
class GroupsFragment : Fragment() {

    companion object {
        fun newInstance() = GroupsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_groups, container, false)
    }


//https://stackoverflow.com/questions/53973556/forward-one-fragment-to-another-fragment-in-kotlin
//companion object {
//    val TAG = GroupsFragment::class.java.simpleName
//    @JvmStatic
//    fun newInstance() = GroupsFragment()
//}
}
