/*
 *  Twidere X
 *
 *  Copyright (C) 2020-2021 Tlaster <tlaster@outlook.com>
 * 
 *  This file is part of Twidere X.
 * 
 *  Twidere X is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  Twidere X is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with Twidere X. If not, see <http://www.gnu.org/licenses/>.
 */
package com.twidere.services.twitter.model.exceptions

import com.twidere.services.http.MicroBlogException
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TwitterApiExceptionV2(
    @SerialName("account_id")
    val accountID: Long? = null,

    @SerialName("product_name")
    val productName: String? = null,

    val title: String? = null,
    val period: String? = null,
    val scope: String? = null,
    val detail: String? = null,
    val type: String? = null,
) : MicroBlogException() {
    override val microBlogErrorMessage: String?
        get() = detail
}