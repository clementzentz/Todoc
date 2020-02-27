package com.cleanup.todoc;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.room.TaskDao;
import com.cleanup.todoc.room.TodocDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class TodocDatabaseTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private TaskDao mTaskDao;
    private TodocDatabase mTodocDatabase;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        mTodocDatabase = Room.inMemoryDatabaseBuilder(context, TodocDatabase.class)
                // Allowing main thread queries, just for testing.
                .allowMainThreadQueries()
                .build();
        mTaskDao = mTodocDatabase.getTaskDao();
    }



    @After
    public void closeDb() {
        mTodocDatabase.close();
    }

    @Test
    public void insertAndGetWord() throws Exception {
        Word word = new Word("word");
        mWordDao.insert(word);
        List<Word> allWords = LiveDataTestUtil.getValue(mWordDao.getAlphabetizedWords());
        assertEquals(allWords.get(0).getWord(), word.getWord());
    }

    @Test
    public void getAllWords() throws Exception {
        Word word = new Word("aaa");
        mWordDao.insert(word);
        Word word2 = new Word("bbb");
        mWordDao.insert(word2);
        List<Word> allWords = LiveDataTestUtil.getValue(mWordDao.getAlphabetizedWords());
        assertEquals(allWords.get(0).getWord(), word.getWord());
        assertEquals(allWords.get(1).getWord(), word2.getWord());
    }

    @Test
    public void deleteAll() throws Exception {
        Task word = new Task(11, "aaa", );
        mWordDao.insert(word);
        Word word2 = new Word("word2");
        mWordDao.insert(word2);
        mWordDao.deleteAll();
        List<Word> allWords = LiveDataTestUtil.getValue(mWordDao.getAlphabetizedWords());
        assertTrue(allWords.isEmpty());
    }
}
