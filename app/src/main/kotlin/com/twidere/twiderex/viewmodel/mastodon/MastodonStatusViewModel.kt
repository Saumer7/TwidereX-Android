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
package com.twidere.twiderex.viewmodel.mastodon

import com.twidere.services.mastodon.MastodonService
import com.twidere.twiderex.db.CacheDatabase
import com.twidere.twiderex.model.AccountDetails
import com.twidere.twiderex.model.MicroBlogKey
import com.twidere.twiderex.paging.mediator.paging.PagingMediator
import com.twidere.twiderex.paging.mediator.status.MastodonStatusContextMediator
import com.twidere.twiderex.repository.StatusRepository
import com.twidere.twiderex.viewmodel.StatusPagingViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class MastodonStatusViewModel @AssistedInject constructor(
    statusRepository: StatusRepository,
    database: CacheDatabase,
    @Assisted private val account: AccountDetails,
    @Assisted private val statusKey: MicroBlogKey,
) : StatusPagingViewModel(statusRepository, account, statusKey) {

    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(account: AccountDetails, statusKey: MicroBlogKey): MastodonStatusViewModel
    }

    override val pagingMediator: PagingMediator = MastodonStatusContextMediator(
        service = account.service as MastodonService,
        statusKey = statusKey,
        accountKey = account.accountKey,
        database = database,
    )
}
