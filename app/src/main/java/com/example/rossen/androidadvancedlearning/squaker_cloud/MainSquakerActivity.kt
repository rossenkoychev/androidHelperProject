package com.example.rossen.androidadvancedlearning.squaker_cloud

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.support.v4.app.LoaderManager
import android.support.v4.content.CursorLoader
import android.support.v4.content.Loader
import android.support.v7.app.AppCompatActivity
import android.support.v7.preference.PreferenceManager
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.rossen.androidadvancedlearning.R
import com.example.rossen.androidadvancedlearning.squaker_cloud.following.FollowingPreferenceActivity
import com.example.rossen.androidadvancedlearning.squaker_cloud.provider.SquawkContract
import com.example.rossen.androidadvancedlearning.squaker_cloud.provider.SquawkProvider
import com.google.firebase.iid.FirebaseInstanceId

import kotlinx.android.synthetic.main.squawker_activity_main.*


class MainSquakerActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor> {

    internal lateinit var mLayoutManager: LinearLayoutManager
    internal lateinit var mAdapter: SquawkAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.squawker_activity_main)

       // mRecyclerView = findViewById(R.id.squawks_recycler_view) as RecyclerView
squawks_recycler_view.setHasFixedSize(true)
        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        squawks_recycler_view.setHasFixedSize(true)

        // Use a linear layout manager
        mLayoutManager = LinearLayoutManager(this)
        squawks_recycler_view.layoutManager = mLayoutManager

        // Add dividers
        val dividerItemDecoration = DividerItemDecoration(
                squawks_recycler_view.context,
                mLayoutManager.orientation)
        squawks_recycler_view.addItemDecoration(dividerItemDecoration)

        // Specify an adapter
        mAdapter = SquawkAdapter()
        squawks_recycler_view.adapter = mAdapter

        // Start the loader
        supportLoaderManager.initLoader(LOADER_ID_MESSAGES, null, this)



        var extras:Bundle?=intent.extras
       Log.d(LOG_TAG, "Contains ${extras?.getString("test")}")

        // Get token from the ID Service you created and show it in a log
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener(this@MainSquakerActivity) { instanceIdResult ->
            val token = instanceIdResult.token
            val msg = getString(R.string.message_token_format, token)
            Log.d(LOG_TAG, msg)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_following_preferences) {
            // Opens the following activity when the menu icon is pressed
            val startFollowingActivity = Intent(this, FollowingPreferenceActivity::class.java)
            startActivity(startFollowingActivity)
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    /**
     * Loader callbacks
     */

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        // This method generates a selection off of only the current followers
        val selection = SquawkContract.createSelectionForCurrentFollowers(
                PreferenceManager.getDefaultSharedPreferences(this))
        Log.d(LOG_TAG, "Selection is $selection")
        return CursorLoader(this, SquawkProvider.SquawkMessages.CONTENT_URI,
                MESSAGES_PROJECTION, selection, null, SquawkContract.COLUMN_DATE + " DESC")
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
         mAdapter.swapCursor(data)
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        mAdapter.swapCursor(null)
    }

    companion object {

        private val LOG_TAG = MainSquakerActivity::class.java.simpleName
        private val LOADER_ID_MESSAGES = 0

        internal val MESSAGES_PROJECTION = arrayOf<String>(SquawkContract.COLUMN_AUTHOR, SquawkContract.COLUMN_MESSAGE, SquawkContract.COLUMN_DATE, SquawkContract.COLUMN_AUTHOR_KEY)

        internal val COL_NUM_AUTHOR = 0
        internal val COL_NUM_MESSAGE = 1
        internal val COL_NUM_DATE = 2
        internal val COL_NUM_AUTHOR_KEY = 3
    }
}