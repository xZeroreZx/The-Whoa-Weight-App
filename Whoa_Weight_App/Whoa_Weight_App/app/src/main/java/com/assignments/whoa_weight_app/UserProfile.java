package com.assignments.whoa_weight_app;

public class UserProfile {
    public UserProfile(String todayDate, String todayWeight, String myGoal) {
        this.todayDate = todayDate;
        this.todayWeight = todayWeight;
        this.myGoal = myGoal;
    }

    public String todayDate;
    public String todayWeight;
    public String myGoal;

    public UserProfile(){

    }
//This activity is a placeholder to gather user profile information.
    public String getTodayDate() {
        return todayDate;
    }

    public String getTodayWeight() {
        return todayWeight;
    }

    public String getMyGoal() {
        return myGoal;
    }

    public void setTodayDate(String todayDate) {
        this.todayDate = todayDate;
    }

    public void setTodayWeight(String todayWeight) {
        this.todayWeight = todayWeight;
    }

    public void setMyGoal(String myGoal) {
        this.myGoal = myGoal;
    }
}
