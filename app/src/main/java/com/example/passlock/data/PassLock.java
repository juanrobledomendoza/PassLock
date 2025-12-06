package com.example.passlock.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;
import androidx.room.ForeignKey;

@Entity(tableName = "passlocks",
        foreignKeys = @ForeignKey(entity = User.class,
                                  parentColumns = "user_id",
                                  childColumns = "user_id",
                                  onDelete = ForeignKey.CASCADE))
public class PassLock {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "passlock_id")
    private int id;

    @ColumnInfo(name = "user_id", index = true)
    public int userId;

    @ColumnInfo(name = "service_name")
    private String serviceName;

    @ColumnInfo(name = "username")
    private String username;

    @ColumnInfo(name = "password")
    private String password;

    // New fields for score and feedback
    @ColumnInfo(name = "score")
    private int score;

    @ColumnInfo(name = "feedback")
    private String feedback;


    public PassLock(int userId, String serviceName, String username, String password) {
        this.userId = userId;
        this.serviceName = serviceName;
        this.username = username;
        this.password = password;
        this.score = -1; // Default score indicates it hasn't been tested
        this.feedback = "";
    }

    // --- Getters and Setters ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getUserId() {
        return userId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
