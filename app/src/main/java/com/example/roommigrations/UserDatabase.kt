package com.example.roommigrations

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RenameColumn
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec

@Database(
    entities = [User::class],
    version = 3,
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
        AutoMigration(from = 2, to = 3, spec = UserDatabase.MigrationFrom2To3::class)
    ]
)
abstract class UserDatabase : RoomDatabase() {
    abstract val dao: UserDao

    @RenameColumn(tableName = "User", fromColumnName = "created", toColumnName = "createdAt")
    class MigrationFrom2To3 : AutoMigrationSpec
}