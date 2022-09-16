package com.example.roommigrations

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RenameColumn
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [User::class, School::class],
    version = 4,
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
        AutoMigration(from = 2, to = 3, spec = UserDatabase.MigrationFrom2To3::class)
    ]
)
abstract class UserDatabase : RoomDatabase() {
    abstract val userDao: UserDao
    abstract val schoolDao: SchoolDao

    @RenameColumn(tableName = "User", fromColumnName = "created", toColumnName = "createdAt")
    class MigrationFrom2To3 : AutoMigrationSpec

    companion object {
        val migrationFrom3To4 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS School (name CHAR NOT NULL PRIMARY KEY)")
            }
        }
    }
}