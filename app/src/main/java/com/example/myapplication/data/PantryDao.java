package com.example.myapplication.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PantryDao {

    // Adds a new pantry item.
    @Insert
    void insert(PantryItem item);

    // Saves changes to a pantry item.
    @Update
    void update(PantryItem item);

    // Removes a pantry item.
    @Delete
    void delete(PantryItem item);

    // Gets all pantry items for one user.
    @Query("SELECT * FROM pantry WHERE userId = :userId ORDER BY name")
    List<PantryItem> getAllForUser(int userId);

    // Gets one pantry item by id.
    @Query("SELECT * FROM pantry WHERE id = :id")
    PantryItem getById(int id);

    // Counts all pantry items.
    @Query("SELECT COUNT(*) FROM pantry")
    int count();
}
