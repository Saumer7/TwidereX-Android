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
package com.twidere.twiderex.mock.db

import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.twidere.twiderex.db.CacheDatabase
import com.twidere.twiderex.db.dao.ListsDao
import com.twidere.twiderex.db.dao.MediaDao
import com.twidere.twiderex.db.dao.PagingTimelineDao
import com.twidere.twiderex.db.dao.ReactionDao
import com.twidere.twiderex.db.dao.StatusDao
import com.twidere.twiderex.db.dao.StatusReferenceDao
import com.twidere.twiderex.db.dao.UrlEntityDao
import com.twidere.twiderex.db.dao.UserDao

class MockCacheDatabase : CacheDatabase() {
    override fun statusDao(): StatusDao {
        TODO("Not yet implemented")
    }

    override fun mediaDao(): MediaDao {
        TODO("Not yet implemented")
    }

    override fun userDao(): UserDao {
        TODO("Not yet implemented")
    }

    override fun reactionDao(): ReactionDao {
        TODO("Not yet implemented")
    }

    override fun pagingTimelineDao(): PagingTimelineDao {
        TODO("Not yet implemented")
    }

    override fun urlEntityDao(): UrlEntityDao {
        TODO("Not yet implemented")
    }

    override fun statusReferenceDao(): StatusReferenceDao {
        TODO("Not yet implemented")
    }

    private val listsDao = MockListsDao()
    override fun listsDao(): ListsDao {
        return listsDao
    }

    override fun createOpenHelper(config: DatabaseConfiguration?): SupportSQLiteOpenHelper {
        TODO("Not yet implemented")
    }

    override fun createInvalidationTracker(): InvalidationTracker {
        return InvalidationTracker(this, "mock")
    }

    override fun clearAllTables() {
        TODO("Not yet implemented")
    }
}
