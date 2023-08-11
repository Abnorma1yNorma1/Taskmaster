package com.example.taskmaster.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.taskmaster.model.Tag

@Dao
interface TagDao {

    @Insert(onConflict = OnConflictStrategy.NONE)
    fun insert(tag: Tag)

    @Query("UPDATE tagTable SET tagName = :tagName WHERE id = :id")
    fun updateName(id: Int, tagName: String)

    @Query("SELECT * FROM tagTable WHERE id = :id")
    fun getTag(id: Int): Tag

    @Query("SELECT * FROM tagTable WHERE tagName = :tagName")
    fun getTagIdByName(tagName: String): Int

    @Query("SELECT * FROM tagTable")
    fun getAll(): List<Tag>

    @Query("DELETE FROM tagTable WHERE id = :id")
    fun delete(id: Int)

    @Query("DELETE FROM tagTable")
    fun deleteAll()

}