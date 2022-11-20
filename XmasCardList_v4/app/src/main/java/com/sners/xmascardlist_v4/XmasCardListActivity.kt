package com.sners.xmascardlist_v4

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.sners.xmascardlist_v4.controller.ContactController
import com.sners.xmascardlist_v4.data.ContactVM
import com.sners.xmascardlist_v4.data.ContactVMFactory
import com.sners.xmascardlist_v4.data.XmasCardListVM
import com.sners.xmascardlist_v4.data.XmasCardListVMFactory
import com.sners.xmascardlist_v4.data.database.ContactDatabase

import kotlinx.android.synthetic.main.activity_item_list.*
import kotlinx.android.synthetic.main.item_list_content.view.*
import kotlinx.android.synthetic.main.item_list.*

/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [ContactDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class XmasCardListActivity : AppCompatActivity() {

   // var contactController = ContactController()

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        setSupportActionBar(toolbar)
        toolbar.title = title

        fab.setOnClickListener { view ->
            Snackbar.make(view, getString(R.string.add_new_contact), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        if (contact_detail_container != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }

        setupRecyclerView(item_list)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {

        //  Set up the link to the database
        val application = requireNotNull(this).application
        val dataSource = ContactDatabase.getInstance(application).contactDao
        val viewModelFactory = XmasCardListVMFactory(dataSource, application)
        val listViewModel =
            ViewModelProvider(this).get(XmasCardListVM::class.java)

        recyclerView.adapter = XmasCardItemRecyclerViewAdapter(this, listViewModel.contacts, twoPane)
    }

    class XmasCardItemRecyclerViewAdapter(
        private val parentActivity: XmasCardListActivity,
        private val values: List<ContactVM>,
        private val twoPane: Boolean
    ) :
        RecyclerView.Adapter<XmasCardItemRecyclerViewAdapter.ViewHolder>() {

        private val onClickListener: View.OnClickListener

        init {
            onClickListener = View.OnClickListener { v ->
                val item = v.tag as ContactVM
                if (twoPane) {
                    val fragment = ContactDetailFragment().apply {
                        arguments = Bundle().apply {
                            putInt(ContactDetailFragment.ARG_ITEM_ID, item.contactId)
                        }
                    }
                   // fragment.contactController = parentActivity.contactController
                    parentActivity.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.contact_detail_container, fragment)
                        .commit()
                } else {
                    val intent = Intent(v.context, ContactDetailActivity::class.java).apply {
                        putExtra(ContactDetailFragment.ARG_ITEM_ID, item.contactId)
                    }
                    v.context.startActivity(intent)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_content, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = values[position]
            holder.idView.text = item.contactId.toString()
            holder.contentView.text = item.firstName.value

            with(holder.itemView) {
                tag = item
                setOnClickListener(onClickListener)
            }
        }

        override fun getItemCount() = values.size

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val idView: TextView = view.id_text
            val contentView: TextView = view.content
        }
    }
}
