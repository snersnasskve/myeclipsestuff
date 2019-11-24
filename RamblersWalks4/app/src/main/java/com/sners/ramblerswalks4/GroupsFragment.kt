package com.sners.ramblerswalks4


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProviders
import com.sners.ramblerswalks4.data.GroupsViewModel
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class GroupsFragment : Fragment() {

    companion object {
        fun newInstance() = GroupsFragment()
    }

    private lateinit var viewModel : GroupsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //  Add the ViewModel for this fragment
        viewModel = ViewModelProviders.of(this).get(GroupsViewModel::class.java)

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
