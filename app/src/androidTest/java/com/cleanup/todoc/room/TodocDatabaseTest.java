package com.cleanup.todoc.room;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;

public class TodocDatabaseTest {

    private TodocDatabase mTodocDatabase;

    public TaskDao getTaskDao(){
        return mTodocDatabase.getTaskDao();
    }

    public ProjectDao getProjectDao(){
        return mTodocDatabase.getProjectDao();
    }

    @Before
    public void init(){
        mTodocDatabase = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                TodocDatabase.class
        ).build();
    }

    @After
    public void finish(){
        mTodocDatabase.close();
    }
}
