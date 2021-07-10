package com.unicocoder.batmanmovies.db;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.unicocoder.batmanmovies.db.model.Batman;

import java.util.List;

@androidx.room.Dao
public interface Dao {

    @Query("SELECT * FROM batman")
    List<Batman> getAll();

    @Query("SELECT * FROM batman where title LIKE :title")
    Batman findByName(String title);

    @Query("SELECT COUNT(*) from batman")
    int countMovies();

    @Insert
    void insertAll(Batman... batmen);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Batman batman);

    @Delete
    void delete(Batman user);
}
