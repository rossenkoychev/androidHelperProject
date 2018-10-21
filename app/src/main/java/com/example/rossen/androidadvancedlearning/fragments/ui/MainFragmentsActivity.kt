package com.example.rossen.androidadvancedlearning.fragments.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.GridView
import android.widget.Toast
import com.example.rossen.androidadvancedlearning.R
import com.example.rossen.androidadvancedlearning.fragments.data.AndroidImageAssets

class MainFragmentsActivity : AppCompatActivity(), MasterListFragment.OnImageClickListener {

    var headIndex: Int = 0
    var bodyIndex: Int = 0
    var legIndex: Int = 0
    lateinit var androidMeIntent: Intent
    lateinit var buttonNext: Button
    var twoPane: Boolean = false
    lateinit var headFragment: BodyPartFragment
    lateinit var bodyFragment: BodyPartFragment
    lateinit var legsFragment: BodyPartFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragments_main)
        buttonNext = findViewById(R.id.next_button)
        if (findViewById<View>(R.id.android_me_linear_layout) != null) {
            twoPane = true
            if (savedInstanceState == null) {

                headFragment = BodyPartFragment()
                headFragment.imageIds = AndroidImageAssets.heads
                headFragment.listIndex = headIndex
                bodyFragment = BodyPartFragment()
                bodyFragment.imageIds = AndroidImageAssets.bodies
                bodyFragment.listIndex = bodyIndex
                legsFragment = BodyPartFragment()
                legsFragment.imageIds = AndroidImageAssets.legs
                legsFragment.listIndex = legIndex
                supportFragmentManager.beginTransaction()
                        .add(R.id.head_container, headFragment, AndroidMeActivity.HEAD_INDEX)
                        .add(R.id.body_container, bodyFragment, AndroidMeActivity.BODY_INDEX)
                        .add(R.id.legs_container, legsFragment, AndroidMeActivity.LEG_INDEX)
                        .commit()
            }
            buttonNext.visibility = View.GONE
            val gridView = findViewById(R.id.master_grid_view) as GridView
            gridView.numColumns = 2

        }


        val bundle = Bundle()
        bundle.putInt(AndroidMeActivity.BODY_INDEX, 0)
        bundle.putInt(AndroidMeActivity.HEAD_INDEX, 0)
        bundle.putInt(AndroidMeActivity.LEG_INDEX, 0)
        androidMeIntent = Intent(this, AndroidMeActivity::class.java)
        androidMeIntent.putExtras(bundle)

    }

    override fun onImageSelected(position: Int) {
        Toast.makeText(this, "Position clicked ${position + 1}", Toast.LENGTH_LONG).show()
        val bodyPartNumber = position / 12
        val listIndex = position - 12 * bodyPartNumber

        when (bodyPartNumber) {
            0 -> headIndex = listIndex
            1 -> bodyIndex = listIndex
            2 -> legIndex = listIndex
        }


        if (twoPane) {
            when (bodyPartNumber) {
                0 -> {
                    headFragment.listIndex = listIndex
                    headFragment.updateView()
                }
                1 -> {
                    bodyFragment.listIndex = listIndex
                    bodyFragment.updateView()
                }
                2 -> {
                    legsFragment.listIndex = listIndex
                    legsFragment.updateView()
                }
            }
        } else {
            val bundle = Bundle()
            bundle.putInt(AndroidMeActivity.BODY_INDEX, bodyIndex)
            bundle.putInt(AndroidMeActivity.HEAD_INDEX, headIndex)
            bundle.putInt(AndroidMeActivity.LEG_INDEX, legIndex)

            androidMeIntent.putExtras(bundle)
            buttonNext.setOnClickListener { startActivity(androidMeIntent) }
        }

    }
}