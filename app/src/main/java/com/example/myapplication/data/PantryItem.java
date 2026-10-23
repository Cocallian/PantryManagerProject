package com.example.myapplication.data;

import androidx.room.ForeignKey;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

@Entity(
        tableName = "pantry",
        foreignKeys = @ForeignKey(
                entity = Users.class,
                parentColumns = "id",
                childColumns = "userId",
                onDelete = ForeignKey.CASCADE),
        indices = {@Index("userId")})
public class PantryItem {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public int userId;

    public String name;
    public double quantity;
    public String unit;

    public String expiryDate;

    // Fills pantry with starter items once.
    public static void seedIfEmpty(PantryDao pantryDao, UsersDao usersDao) {
        if (pantryDao.count() > 0) {
            return;
        }
        List<Users> users = usersDao.getAll();
        seedAdmin(pantryDao, users.get(0).id);
        seedAlice(pantryDao, users.get(1).id);
        seedBob(pantryDao, users.get(2).id);
        seedCharlie(pantryDao, users.get(3).id);
        seedDana(pantryDao, users.get(4).id);
        seedEve(pantryDao, users.get(5).id);
    }

    // Adds admin's starter pantry.
    private static void seedAdmin(PantryDao dao, int userId) {
        add(dao, userId, "egg", 3, "pcs", dateInDays(1));
        add(dao, userId, "butter", 100, "g", null);
        add(dao, userId, "salt", 75, "g", null);
        add(dao, userId, "cheese", 300, "g", dateInDays(10));
        add(dao, userId, "pasta", 1000, "g", null);
        add(dao, userId, "tomato", 3, "pcs", null);
        add(dao, userId, "olive oil", 100, "ml", dateInDays(5));
        add(dao, userId, "garlic", 9, "cloves", null);
        add(dao, userId, "bread", 16, "slices", null);
        add(dao, userId, "milk", 1250, "ml", dateInDays(14));
        add(dao, userId, "flour", 250, "g", null);
        add(dao, userId, "sugar", 200, "g", null);
        add(dao, userId, "rice", 750, "g", dateInDays(9));
        add(dao, userId, "onion", 8, "pcs", null);
        add(dao, userId, "soy sauce", 250, "ml", null);
        add(dao, userId, "water", 500, "ml", dateInDays(4));
        add(dao, userId, "potato", 800, "g", null);
        add(dao, userId, "carrot", 8, "pcs", null);
        add(dao, userId, "apple", 8, "pcs", dateInDays(13));
        add(dao, userId, "lemon", 5, "pcs", null);
        add(dao, userId, "yogurt", 125, "g", null);
        add(dao, userId, "ham", 100, "g", dateInDays(8));
        add(dao, userId, "pepper", 30, "g", null);
        add(dao, userId, "honey", 300, "g", null);
        add(dao, userId, "chicken", 1250, "g", dateInDays(3));
        add(dao, userId, "spinach", 100, "g", null);
        add(dao, userId, "mushroom", 250, "g", null);
        add(dao, userId, "banana", 6, "pcs", dateInDays(12));
        add(dao, userId, "oats", 600, "g", null);
        add(dao, userId, "tuna", 400, "g", null);
        add(dao, userId, "cucumber", 1, "pcs", dateInDays(7));
        add(dao, userId, "beans", 400, "g", null);
        add(dao, userId, "cream", 300, "ml", null);
        add(dao, userId, "parmesan", 160, "g", dateInDays(2));
        add(dao, userId, "basil", 25, "g", null);
        add(dao, userId, "cinnamon", 8, "g", null);
    }

    // Adds alice's starter pantry.
    private static void seedAlice(PantryDao dao, int userId) {
        add(dao, userId, "egg", 9, "pcs", dateInDays(6));
        add(dao, userId, "butter", 200, "g", null);
        add(dao, userId, "salt", 125, "g", null);
        add(dao, userId, "cheese", 75, "g", dateInDays(1));
        add(dao, userId, "pasta", 400, "g", null);
        add(dao, userId, "tomato", 9, "pcs", null);
        add(dao, userId, "olive oil", 200, "ml", dateInDays(10));
        add(dao, userId, "garlic", 15, "cloves", null);
        add(dao, userId, "bread", 4, "slices", null);
        add(dao, userId, "milk", 500, "ml", dateInDays(5));
        add(dao, userId, "flour", 750, "g", null);
        add(dao, userId, "sugar", 400, "g", null);
        add(dao, userId, "rice", 1250, "g", dateInDays(14));
        add(dao, userId, "onion", 2, "pcs", null);
        add(dao, userId, "soy sauce", 100, "ml", null);
        add(dao, userId, "water", 1500, "ml", dateInDays(9));
        add(dao, userId, "potato", 1600, "g", null);
        add(dao, userId, "carrot", 13, "pcs", null);
        add(dao, userId, "apple", 2, "pcs", dateInDays(4));
        add(dao, userId, "lemon", 2, "pcs", null);
        add(dao, userId, "yogurt", 375, "g", null);
        add(dao, userId, "ham", 200, "g", dateInDays(13));
        add(dao, userId, "pepper", 50, "g", null);
        add(dao, userId, "honey", 75, "g", null);
        add(dao, userId, "chicken", 500, "g", dateInDays(8));
        add(dao, userId, "spinach", 300, "g", null);
        add(dao, userId, "mushroom", 500, "g", null);
        add(dao, userId, "banana", 10, "pcs", dateInDays(3));
        add(dao, userId, "oats", 150, "g", null);
        add(dao, userId, "tuna", 160, "g", null);
        add(dao, userId, "cucumber", 3, "pcs", dateInDays(12));
        add(dao, userId, "beans", 800, "g", null);
        add(dao, userId, "cream", 500, "ml", null);
        add(dao, userId, "parmesan", 40, "g", dateInDays(7));
        add(dao, userId, "basil", 10, "g", null);
        add(dao, userId, "cinnamon", 23, "g", null);
    }

    // Adds bob's starter pantry.
    private static void seedBob(PantryDao dao, int userId) {
        add(dao, userId, "egg", 15, "pcs", dateInDays(11));
        add(dao, userId, "butter", 50, "g", null);
        add(dao, userId, "salt", 50, "g", null);
        add(dao, userId, "cheese", 225, "g", dateInDays(6));
        add(dao, userId, "pasta", 800, "g", null);
        add(dao, userId, "tomato", 15, "pcs", null);
        add(dao, userId, "olive oil", 50, "ml", dateInDays(1));
        add(dao, userId, "garlic", 6, "cloves", null);
        add(dao, userId, "bread", 12, "slices", null);
        add(dao, userId, "milk", 1000, "ml", dateInDays(10));
        add(dao, userId, "flour", 1250, "g", null);
        add(dao, userId, "sugar", 100, "g", null);
        add(dao, userId, "rice", 500, "g", dateInDays(5));
        add(dao, userId, "onion", 6, "pcs", null);
        add(dao, userId, "soy sauce", 200, "ml", null);
        add(dao, userId, "water", 2500, "ml", dateInDays(14));
        add(dao, userId, "potato", 400, "g", null);
        add(dao, userId, "carrot", 5, "pcs", null);
        add(dao, userId, "apple", 6, "pcs", dateInDays(9));
        add(dao, userId, "lemon", 4, "pcs", null);
        add(dao, userId, "yogurt", 625, "g", null);
        add(dao, userId, "ham", 50, "g", dateInDays(4));
        add(dao, userId, "pepper", 20, "g", null);
        add(dao, userId, "honey", 225, "g", null);
        add(dao, userId, "chicken", 1000, "g", dateInDays(13));
        add(dao, userId, "spinach", 500, "g", null);
        add(dao, userId, "mushroom", 125, "g", null);
        add(dao, userId, "banana", 4, "pcs", dateInDays(8));
        add(dao, userId, "oats", 450, "g", null);
        add(dao, userId, "tuna", 320, "g", null);
        add(dao, userId, "cucumber", 5, "pcs", dateInDays(3));
        add(dao, userId, "beans", 200, "g", null);
        add(dao, userId, "cream", 200, "ml", null);
        add(dao, userId, "parmesan", 120, "g", dateInDays(12));
        add(dao, userId, "basil", 20, "g", null);
        add(dao, userId, "cinnamon", 38, "g", null);
    }

    // Adds charlie's starter pantry.
    private static void seedCharlie(PantryDao dao, int userId) {
        add(dao, userId, "egg", 6, "pcs", dateInDays(2));
        add(dao, userId, "butter", 150, "g", null);
        add(dao, userId, "salt", 100, "g", null);
        add(dao, userId, "cheese", 375, "g", dateInDays(11));
        add(dao, userId, "pasta", 200, "g", null);
        add(dao, userId, "tomato", 6, "pcs", null);
        add(dao, userId, "olive oil", 150, "ml", dateInDays(6));
        add(dao, userId, "garlic", 12, "cloves", null);
        add(dao, userId, "bread", 20, "slices", null);
        add(dao, userId, "milk", 250, "ml", dateInDays(1));
        add(dao, userId, "flour", 500, "g", null);
        add(dao, userId, "sugar", 300, "g", null);
        add(dao, userId, "rice", 1000, "g", dateInDays(10));
        add(dao, userId, "onion", 10, "pcs", null);
        add(dao, userId, "soy sauce", 50, "ml", null);
        add(dao, userId, "water", 1000, "ml", dateInDays(5));
        add(dao, userId, "potato", 1200, "g", null);
        add(dao, userId, "carrot", 10, "pcs", null);
        add(dao, userId, "apple", 10, "pcs", dateInDays(14));
        add(dao, userId, "lemon", 1, "pcs", null);
        add(dao, userId, "yogurt", 250, "g", null);
        add(dao, userId, "ham", 150, "g", dateInDays(9));
        add(dao, userId, "pepper", 40, "g", null);
        add(dao, userId, "honey", 375, "g", null);
        add(dao, userId, "chicken", 250, "g", dateInDays(4));
        add(dao, userId, "spinach", 200, "g", null);
        add(dao, userId, "mushroom", 375, "g", null);
        add(dao, userId, "banana", 8, "pcs", dateInDays(13));
        add(dao, userId, "oats", 750, "g", null);
        add(dao, userId, "tuna", 80, "g", null);
        add(dao, userId, "cucumber", 2, "pcs", dateInDays(8));
        add(dao, userId, "beans", 600, "g", null);
        add(dao, userId, "cream", 400, "ml", null);
        add(dao, userId, "parmesan", 200, "g", dateInDays(3));
        add(dao, userId, "basil", 5, "g", null);
        add(dao, userId, "cinnamon", 15, "g", null);
    }

    // Adds dana's starter pantry.
    private static void seedDana(PantryDao dao, int userId) {
        add(dao, userId, "egg", 12, "pcs", dateInDays(7));
        add(dao, userId, "butter", 250, "g", null);
        add(dao, userId, "salt", 25, "g", null);
        add(dao, userId, "cheese", 150, "g", dateInDays(2));
        add(dao, userId, "pasta", 600, "g", null);
        add(dao, userId, "tomato", 12, "pcs", null);
        add(dao, userId, "olive oil", 250, "ml", dateInDays(11));
        add(dao, userId, "garlic", 3, "cloves", null);
        add(dao, userId, "bread", 8, "slices", null);
        add(dao, userId, "milk", 750, "ml", dateInDays(6));
        add(dao, userId, "flour", 1000, "g", null);
        add(dao, userId, "sugar", 500, "g", null);
        add(dao, userId, "rice", 250, "g", dateInDays(1));
        add(dao, userId, "onion", 4, "pcs", null);
        add(dao, userId, "soy sauce", 150, "ml", null);
        add(dao, userId, "water", 2000, "ml", dateInDays(10));
        add(dao, userId, "potato", 2000, "g", null);
        add(dao, userId, "carrot", 3, "pcs", null);
        add(dao, userId, "apple", 4, "pcs", dateInDays(5));
        add(dao, userId, "lemon", 3, "pcs", null);
        add(dao, userId, "yogurt", 500, "g", null);
        add(dao, userId, "ham", 250, "g", dateInDays(14));
        add(dao, userId, "pepper", 10, "g", null);
        add(dao, userId, "honey", 150, "g", null);
        add(dao, userId, "chicken", 750, "g", dateInDays(9));
        add(dao, userId, "spinach", 400, "g", null);
        add(dao, userId, "mushroom", 625, "g", null);
        add(dao, userId, "banana", 2, "pcs", dateInDays(4));
        add(dao, userId, "oats", 300, "g", null);
        add(dao, userId, "tuna", 240, "g", null);
        add(dao, userId, "cucumber", 4, "pcs", dateInDays(13));
        add(dao, userId, "beans", 1000, "g", null);
        add(dao, userId, "cream", 100, "ml", null);
        add(dao, userId, "parmesan", 80, "g", dateInDays(8));
        add(dao, userId, "basil", 15, "g", null);
        add(dao, userId, "cinnamon", 30, "g", null);
    }

    // Adds eve's starter pantry.
    private static void seedEve(PantryDao dao, int userId) {
        add(dao, userId, "egg", 3, "pcs", dateInDays(12));
        add(dao, userId, "butter", 100, "g", null);
        add(dao, userId, "salt", 75, "g", null);
        add(dao, userId, "cheese", 300, "g", dateInDays(7));
        add(dao, userId, "pasta", 1000, "g", null);
        add(dao, userId, "tomato", 3, "pcs", null);
        add(dao, userId, "olive oil", 100, "ml", dateInDays(2));
        add(dao, userId, "garlic", 9, "cloves", null);
        add(dao, userId, "bread", 16, "slices", null);
        add(dao, userId, "milk", 1250, "ml", dateInDays(11));
        add(dao, userId, "flour", 250, "g", null);
        add(dao, userId, "sugar", 200, "g", null);
        add(dao, userId, "rice", 750, "g", dateInDays(6));
        add(dao, userId, "onion", 8, "pcs", null);
        add(dao, userId, "soy sauce", 250, "ml", null);
        add(dao, userId, "water", 500, "ml", dateInDays(1));
        add(dao, userId, "potato", 800, "g", null);
        add(dao, userId, "carrot", 8, "pcs", null);
        add(dao, userId, "apple", 8, "pcs", dateInDays(10));
        add(dao, userId, "lemon", 5, "pcs", null);
        add(dao, userId, "yogurt", 125, "g", null);
        add(dao, userId, "ham", 100, "g", dateInDays(5));
        add(dao, userId, "pepper", 30, "g", null);
        add(dao, userId, "honey", 300, "g", null);
        add(dao, userId, "chicken", 1250, "g", dateInDays(14));
        add(dao, userId, "spinach", 100, "g", null);
        add(dao, userId, "mushroom", 250, "g", null);
        add(dao, userId, "banana", 6, "pcs", dateInDays(9));
        add(dao, userId, "oats", 600, "g", null);
        add(dao, userId, "tuna", 400, "g", null);
        add(dao, userId, "cucumber", 1, "pcs", dateInDays(4));
        add(dao, userId, "beans", 400, "g", null);
        add(dao, userId, "cream", 300, "ml", null);
        add(dao, userId, "parmesan", 160, "g", dateInDays(13));
        add(dao, userId, "basil", 25, "g", null);
        add(dao, userId, "cinnamon", 8, "g", null);
    }

    // Saves one starter pantry item.
    private static void add(PantryDao dao, int userId, String name, double quantity, String unit, String expiryDate) {
        dao.insert(seed(userId, name, quantity, unit, expiryDate));
    }

    // Gives the date this many days from today as yyyy-MM-dd.
    private static String dateInDays(int days) {
        Calendar date = Calendar.getInstance();
        date.add(Calendar.DAY_OF_YEAR, days);
        return new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(date.getTime());
    }

    // Builds one pantry item.
    public static PantryItem seed(int userId, String name, double quantity, String unit, String expiryDate) {
        PantryItem item = new PantryItem();
        item.userId = userId;
        item.name = name;
        item.quantity = quantity;
        item.unit = unit;
        item.expiryDate = expiryDate;
        return item;
    }

}
