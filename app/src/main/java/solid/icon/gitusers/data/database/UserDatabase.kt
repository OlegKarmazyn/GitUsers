package solid.icon.gitusers.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import solid.icon.gitusers.data.database.entities.RepositoryItem
import solid.icon.gitusers.data.database.entities.UserItem

@Database(
    entities = [UserItem::class, RepositoryItem::class],
    version = 1,
    exportSchema = true
)
abstract class UserDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDao
    abstract fun getRepositoryDao(): RepositoryDao

    companion object {
        @Volatile
        private var instance: UserDatabase? = null
        private const val DATABASE_NAME = "user_base"

        operator fun invoke(context: Context) = buildDatabase(context)
            .also { instance = it }

        private fun buildDatabase(context: Context): UserDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                UserDatabase::class.java,
                DATABASE_NAME
            )
                .build()
        }
    }
}