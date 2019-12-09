package com.sners.xmascardlist_v4

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModelProviders
import com.sners.xmascardlist_v4.controller.ContactController
import com.sners.xmascardlist_v4.data.Contact
import com.sners.xmascardlist_v4.data.ContactViewModelFactory
import kotlinx.android.synthetic.main.activity_item_detail.*
import kotlinx.android.synthetic.main.item_detail.view.*

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a [XmasCardListActivity]
 * in two-pane mode (on tablets) or a [ContactDetailActivity]
 * on handsets.
 */
class ContactDetailFragment : Fragment() , LifecycleObserver{

    public var contactController: ContactController? = null
    private lateinit var contactViewModel : Contact
    private  lateinit var viewModelFractory: ContactViewModelFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                // Load the dummy content specified by the fragment
                // arguments. In a real-world scenario, use a Loader
                // to load content from a content provider.
//                if (null != contactController)
//                {
//                    contact = contactController!!.contactMap[it.getString(ARG_ITEM_ID)]
//                }

                //  She suggested this should go in onCreateView().  Not sure the benefits / otherwise
                val contactId = it.getInt(ARG_ITEM_ID)
                viewModelFractory = ContactViewModelFactory(contactId)

                contactViewModel = ViewModelProviders.of(this, viewModelFractory).get(Contact::class.java)
                //activity?.toolbar_layout?.title = contact?.firstName!!.value
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.item_detail, container, false)

        // Show the dummy content as text in a TextView.

        contactViewModel?.let {
            rootView.item_detail.text = it.firstName.value
        }

        return rootView
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
    }
}
