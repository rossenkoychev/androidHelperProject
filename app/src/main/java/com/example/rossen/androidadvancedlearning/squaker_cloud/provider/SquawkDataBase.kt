package com.example.rossen.androidadvancedlearning.squaker_cloud.provider

import net.simonvt.schematic.annotation.Database
import net.simonvt.schematic.annotation.Table

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
 * Uses the Schematic (https://github.com/SimonVT/schematic) library to create a database with one
 * table for messages
 */

@Database(version = SquawkDatabase.VERSION)
object SquawkDatabase {

    const val VERSION = 4

    @Table(SquawkContract::class)
    const val SQUAWK_MESSAGES = "squawk_messages"

}