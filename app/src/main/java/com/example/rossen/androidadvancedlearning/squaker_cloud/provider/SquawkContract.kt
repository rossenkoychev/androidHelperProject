package com.example.rossen.androidadvancedlearning.squaker_cloud.provider

import android.content.SharedPreferences
import net.simonvt.schematic.annotation.*

/*
* Copyright (C) 2017 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*  	http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
/**
 * Uses the Schematic (https://github.com/SimonVT/schematic) library to define the columns in a
 * content provider baked by a database
 */

object SquawkContract {

    @DataType(DataType.Type.INTEGER)
    @PrimaryKey(onConflict = ConflictResolutionType.REPLACE)
    @AutoIncrement
    const val COLUMN_ID = "_id"

    @DataType(DataType.Type.TEXT)
    @NotNull
    const val COLUMN_AUTHOR = "author"

    @DataType(DataType.Type.TEXT)
    @NotNull
    const val COLUMN_AUTHOR_KEY = "authorKey"

    @DataType(DataType.Type.TEXT)
    @NotNull
    const val COLUMN_MESSAGE = "message"

    @DataType(DataType.Type.INTEGER)
    @NotNull
    const val COLUMN_DATE = "date"


    // Topic keys as matching what is found in the database
    val ASSER_KEY = "key_asser"
    val CEZANNE_KEY = "key_cezanne"
    val JLIN_KEY = "key_jlin"
    val LYLA_KEY = "key_lyla"
    val NIKITA_KEY = "key_nikita"
    val TEST_ACCOUNT_KEY = "key_test"


    val INSTRUCTOR_KEYS = arrayOf(ASSER_KEY, CEZANNE_KEY, JLIN_KEY, LYLA_KEY, NIKITA_KEY)

    /**
     * Creates a SQLite SELECTION parameter that filters just the rows for the authors you are
     * currently following.
     */
    fun createSelectionForCurrentFollowers(preferences: SharedPreferences): String {

        val stringBuilder = StringBuilder()
        //Automatically add the test account
        stringBuilder.append(COLUMN_AUTHOR_KEY).append(" IN  ('").append(TEST_ACCOUNT_KEY).append("'")

        for (key in INSTRUCTOR_KEYS) {
            if (preferences.getBoolean(key, false)) {
                stringBuilder.append(",")
                stringBuilder.append("'").append(key).append("'")
            }
        }
        stringBuilder.append(")")
        return stringBuilder.toString()
    }
}