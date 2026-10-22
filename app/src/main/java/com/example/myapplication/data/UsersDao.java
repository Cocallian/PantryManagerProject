package com.example.myapplication.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/** Database operations for user accounts (registration and login). */
@Dao
public interface UsersDao {

    /** Adds a new user. Fails if the username is already taken (unique index). */
    @Insert
    long insert(Users user);

    /** Returns the user matching both username and password, or null (used for login). */
    @Query("SELECT * FROM users WHERE username = :username AND password = :password LIMIT 1")
    Users findUser(String username, String password);

    /** Returns the user with this username, or null if none exists. */
    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    Users findByUsername(String username);

    @Query("SELECT COUNT(*) FROM users")
    int count();

    @Query("SELECT * FROM users ORDER BY id")
    List<Users> getAll();
}
