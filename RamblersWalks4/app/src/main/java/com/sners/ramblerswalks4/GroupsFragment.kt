package com.sners.ramblerswalks4


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import timber.log.Timber

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
        val view = inflater.inflate(R.layout.fragment_groups, container, false)

        val backButton = view?.findViewById<Button>(R.id.groups_back_button)
        backButton?.setOnClickListener {
            //  All fragments and activities have access to navigation
            Timber.i("Group back button preseed called from karen")

            val ft = fragmentManager!!.beginTransaction()
            ft.replace(R.id.container, MainFragment.newInstance())
            ft.commitNow()
        }
        return view
    }


//https://stackoverflow.com/questions/53973556/forward-one-fragment-to-another-fragment-in-kotlin
//companion object {
//    val TAG = GroupsFragment::class.java.simpleName
//    @JvmStatic
//    fun newInstance() = GroupsFragment()
//}
}
