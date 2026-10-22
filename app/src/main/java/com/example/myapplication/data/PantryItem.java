package com.example.myapplication.data;

import androidx.room.ForeignKey;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.List;

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

    private static final String[] STOCK = {
            "egg:6:pcs", "butter:100:g", "salt:50:g", "cheese:150:g", "pasta:400:g",
            "tomato:6:pcs", "olive oil:100:ml", "garlic:6:cloves", "bread:8:slices",
            "milk:500:ml", "flour:500:g", "sugar:200:g", "rice:500:g", "onion:4:pcs",
            "soy sauce:100:ml", "water:1000:ml", "potato:800:g", "carrot:5:pcs",
            "apple:4:pcs", "lemon:2:pcs", "yogurt:250:g", "ham:100:g", "pepper:20:g",
            "honey:150:g"
    };

    // Fills pantry with starter items once.
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

                String expiry = (i % 3 == 0)
                        ? String.format("2026-11-%02d", 1 + (i * 3 + u * 5) % 28)
                        : null;

                pantryDao.insert(seed(users.get(u).id, parts[0], quantity, parts[2], expiry));
            }
        }
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
