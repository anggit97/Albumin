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
package com.anggit97.core.util

import androidx.recyclerview.widget.DiffUtil

class IdBasedDiffCallback<T>(
    private val getIdOf: (T) -> String
) : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return getIdOf(oldItem) == getIdOf(newItem)
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return java.util.Objects.equals(oldItem, newItem)
    }
}
