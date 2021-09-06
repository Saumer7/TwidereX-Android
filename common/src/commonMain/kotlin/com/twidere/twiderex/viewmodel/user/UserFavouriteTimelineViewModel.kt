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
package com.twidere.twiderex.viewmodel.user

import androidx.paging.cachedIn
import com.twidere.services.microblog.TimelineService
import com.twidere.twiderex.extensions.asStateIn
import com.twidere.twiderex.model.MicroBlogKey
import com.twidere.twiderex.repository.AccountRepository
import com.twidere.twiderex.repository.TimelineRepository
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class UserFavouriteTimelineViewModel(
    private val repository: TimelineRepository,
    private val accountRepository: AccountRepository,
    private val userKey: MicroBlogKey,
) : ViewModel() {
    private val account by lazy {
        accountRepository.activeAccount.asStateIn(viewModelScope, null)
    }

    val source by lazy {
        account.flatMapLatest {
            if (it != null) {
                repository.favouriteTimeline(
                    userKey = userKey,
                    accountKey = it.accountKey,
                    platformType = it.type,
                    service = it.service as TimelineService
                )
            } else {
                emptyFlow()
            }
        }.cachedIn(viewModelScope)
    }
}