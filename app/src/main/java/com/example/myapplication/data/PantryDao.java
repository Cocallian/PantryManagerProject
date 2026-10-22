package com.example.myapplication.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/** Database operations for pantry items (full CRUD). */
@Dao
public interface PantryDao {

    @Insert
    void insert(PantryItem item);

    @Update
    void update(PantryItem item);

    @Delete
    void delete(PantryItem item);

    @Query("SELECT * FROM pantry ORDER BY name")
    List<PantryItem> getAll();

    @Query("SELECT * FROM pantry WHERE id = :id")
    PantryItem getById(int id);
}
