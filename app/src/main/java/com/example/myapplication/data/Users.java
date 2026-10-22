package com.example.myapplication.data;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "users", indices = {@Index(value = "username", unique = true)})
public class Users {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String username;

    public String password;

    // Adds starter users once.
    public static void seedIfEmpty(UsersDao dao) {
        if (dao.count() > 0) {
            return;
        }
        add(dao, "admin", "admin");
        add(dao, "alice", "alice123");
        add(dao, "bob", "bob123");
        add(dao, "charlie", "charlie123");
        add(dao, "dana", "dana123");
        add(dao, "eve", "eve123");
    }

    // Creates and saves one user.
    private static void add(UsersDao dao, String username, String password) {
        Users user = new Users();
        user.username = username;
        user.password = password;
        dao.insert(user);
    }
}
