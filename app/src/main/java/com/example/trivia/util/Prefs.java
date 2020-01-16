package com.example.trivia.util;

import android.app.Activity;
import android.content.SharedPreferences;

public class Prefs {
    private SharedPreferences preferences;

    public Prefs(Activity activity){
        this.preferences = activity.getPreferences(activity.MODE_PRIVATE);
    }

    public void saveHighestScore(int score){
        int currentScore = score;
        int lastScore = preferences.getInt("high_score", 0);

        if (currentScore > lastScore){
            preferences.edit().putInt("high_score", currentScore).apply();
        }
    }

    public int getHighScore(){
        return preferences.getInt("high_score", 0);
    }

    public void saveState(int questionIndex){
        preferences.edit().putInt("index_state", questionIndex).apply();
    }

    public int getSavedState(){
        return preferences.getInt("index_state", 0);
    }
}
