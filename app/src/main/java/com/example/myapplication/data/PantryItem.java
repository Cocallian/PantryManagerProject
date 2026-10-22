package com.example.myapplication.data;

import androidx.room.ForeignKey;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.List;

/**
 * One ingredient a user has in their pantry (one row in the "pantry" table).
 * Each item belongs to a user through the userId foreign key; deleting a user
 * also deletes their pantry items (CASCADE).
 */
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

    /** Id of the owning user (references users.id). */
    public int userId;

    public String name;
    public double quantity;
    public String unit;

    /** Optional expiry date stored as text, e.g. "2026-11-05". Null if not set. */
    public String expiryDate;

    // ---------------------------------------------------------------------------------
    // Seed helper
    // ---------------------------------------------------------------------------------

    /** Starter pantry stock as "name:baseQuantity:unit". Each user gets a scaled copy. */
    private static final String[] STOCK = {
            "egg:6:pcs", "butter:100:g", "salt:50:g", "cheese:150:g", "pasta:400:g",
            "tomato:6:pcs", "olive oil:100:ml", "garlic:6:cloves", "bread:8:slices",
            "milk:500:ml", "flour:500:g", "sugar:200:g", "rice:500:g", "onion:4:pcs",
            "soy sauce:100:ml", "water:1000:ml", "potato:800:g", "carrot:5:pcs",
            "apple:4:pcs", "lemon:2:pcs", "yogurt:250:g", "ham:100:g", "pepper:20:g",
            "honey:150:g"
    };

    /**
     * Seed helper: gives every existing user a pantry of 24 ingredients, but only if the
     * pantry table is empty, so it is safe to call on every launch. Run it after
     * Users.seedIfEmpty (the userId foreign key needs the users to exist) and on a
     * background thread.
     *
     * Amounts differ per user: each ingredient's base quantity is multiplied by a factor
     * between 0.5 and 2.5 that changes with the ingredient and the user, so some users
     * have enough for a recipe and others are a little short.
     */
    public static void seedIfEmpty(PantryDao pantryDao, UsersDao usersDao) {
        if (pantryDao.count() > 0) {
            return;
        }
        List<Users> users = usersDao.getAll();
        for (int u = 0; u < users.size(); u++) {
            for (int i = 0; i < STOCK.length; i++) {
                String[] parts = STOCK[i].split(":");
                double factor = 0.5 + ((i + u * 2) % 5) * 0.5;
                double quantity = Math.max(1, Math.round(Double.parseDouble(parts[1]) * factor));

                // Every third item gets an expiry date, spread across the next few weeks
                String expiry = (i % 3 == 0)
                        ? String.format("2026-11-%02d", 1 + (i * 3 + u * 5) % 28)
                        : null;

                pantryDao.insert(seed(users.get(u).id, parts[0], quantity, parts[2], expiry));
            }
        }
    }

    /**
     * Seed helper: builds a pantry item in one call (useful for sample/test data).
     * The pantry itself starts empty on first run, so nothing is seeded automatically.
     */
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
