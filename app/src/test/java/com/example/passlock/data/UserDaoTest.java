package com.example.passlock.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserDaoTest {

    private AppDatabase db;
    private UserDao userDao;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class)
                .allowMainThreadQueries()
                .build();
        userDao = db.userDao();
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void validLoginReturnsUser_invalidLoginReturnsNull() {
        // Arrange
        User admin = new User("Admin1", "admin123", true);
        long id = userDao.insertUser(admin);

        // Act
        User found = userDao.getUserByUsernameAndPassword("Admin1", "admin123");
        User wrongPassword = userDao.getUserByUsernameAndPassword("Admin1", "wrong");
        User unknownUser = userDao.getUserByUsernameAndPassword("nope", "admin123");

        // Assert
        assertNotNull(found);
        assertEquals("Admin1", found.getUsername());
        assertEquals(true, found.isAdmin());

        assertNull(wrongPassword);
        assertNull(unknownUser);
    }

    @Test
    public void insertUser_returnsValidId() {
        // Arrange
        User testUser = new User("Test1", "test123", false);

        // Act
        long id = userDao.insertUser(testUser);

        // Assert
        assertNotNull(id);
        assertNotNull(userDao.getUserByUsernameAndPassword("Test1", "test123"));
    }

    @Test
    public void insertUser_replaceExistingUser() {
        // Arrange
        User user1 = new User("TestUser", "pass1", false);
        userDao.insertUser(user1);

        // Act - insert with same username but different password
        User user2 = new User("TestUser", "pass2", true);
        userDao.insertUser(user2);

        // Assert - should have new password
        User found = userDao.getUserByUsernameAndPassword("TestUser", "pass2");
        assertNotNull(found);
        assertEquals("pass2", found.getPassword());
        assertEquals(true, found.isAdmin());
    }
}

