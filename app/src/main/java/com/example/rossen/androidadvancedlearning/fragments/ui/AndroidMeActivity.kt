package com.example.rossen.androidadvancedlearning.fragments.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.rossen.androidadvancedlearning.R
import com.example.rossen.androidadvancedlearning.fragments.data.AndroidImageAssets

class AndroidMeActivity : AppCompatActivity() {
    companion object {
        const val HEAD_INDEX = "head index"
        const val BODY_INDEX = "body index"
        const val LEG_INDEX = "leg index"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_android_me)

        lateinit var headFragment: BodyPartFragment
        lateinit var bodyFragment: BodyPartFragment
        lateinit var legsFragment: BodyPartFragment
        val bundle = intent.extras

        val headIndex = bundle.getInt(HEAD_INDEX)
        val bodyIndex = bundle.getInt(BODY_INDEX)
        val legIndex = bundle.getInt(LEG_INDEX)

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
                    .add(R.id.head_container, headFragment, HEAD_INDEX)
                    .add(R.id.body_container, bodyFragment, BODY_INDEX)
                    .add(R.id.legs_container, legsFragment, LEG_INDEX)
                    .commit()
        }
    }
}
