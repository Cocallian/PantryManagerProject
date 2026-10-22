package com.example.myapplication.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UsersDao {

    // Adds a new user.
    @Insert
    long insert(Users user);

    // Finds a user by username and password.
    @Query("SELECT * FROM users WHERE username = :username AND password = :password LIMIT 1")
    Users findUser(String username, String password);

    // Finds a user by username only.
    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    Users findByUsername(String username);

    // Counts all users.
    @Query("SELECT COUNT(*) FROM users")
    int count();

    // Gets all users.
    @Query("SELECT * FROM users ORDER BY id")
    List<Users> getAll();
}
