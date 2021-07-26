/*
 * Copyright 2021 SOUP
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.anggit97.core.ext

import android.content.Context
import android.content.Intent
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.LENGTH_SHORT
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

/** Color */

fun Context.getColorCompat(@ColorRes colorResId: Int) =
    ContextCompat.getColor(this, colorResId)

fun Context.getColorStateListCompat(@ColorRes colorResId: Int) =
    ContextCompat.getColorStateList(this, colorResId)

/** Toast */

fun Context.showToast(msg: CharSequence) =
    Toast.makeText(this, msg, LENGTH_SHORT).show()

fun Context.showLongToast(msg: CharSequence) =
    Toast.makeText(this, msg, LENGTH_LONG).show()

fun Context.showToast(@StringRes msgId: Int) =
    Toast.makeText(this, msgId, LENGTH_SHORT).show()

fun Context.showLongToast(@StringRes msgId: Int) =
    Toast.makeText(this, msgId, LENGTH_LONG).show()

/** Activity */

fun Context.startActivitySafely(intent: Intent) {
    if (intent.isValid(this)) {
        startActivity(intent)
    } else {
        showToast("No app found to run.")
    }
}

private fun Intent.isValid(ctx: Context): Boolean {
    return resolveActivity(ctx.packageManager) != null
}
