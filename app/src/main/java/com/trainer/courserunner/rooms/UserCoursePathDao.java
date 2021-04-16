package com.trainer.courserunner.rooms;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

@Dao
public interface UserCoursePathDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public long insertUserCoursePath(UserCoursePath userCoursePath);
}
