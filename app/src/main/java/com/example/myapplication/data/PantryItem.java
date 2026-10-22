package com.example.myapplication.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/** One ingredient the user has in their pantry (one row in the "pantry" table). */
@Entity(tableName = "pantry")
public class PantryItem {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public double quantity;
    public String unit;

    /** Optional expiry date stored as text, e.g. "2026-11-05". Null if not set. */
    public String expiryDate;

    // ---------------------------------------------------------------------------------
    // Seed helper
    // ---------------------------------------------------------------------------------

    /**
     * Seed helper: builds a pantry item in one call (useful for sample/test data).
     * The pantry itself starts empty on first run, so nothing is seeded automatically.
     */
    public static PantryItem seed(String name, double quantity, String unit, String expiryDate) {
        PantryItem item = new PantryItem();
        item.name = name;
        item.quantity = quantity;
        item.unit = unit;
        item.expiryDate = expiryDate;
        return item;
    }

}
