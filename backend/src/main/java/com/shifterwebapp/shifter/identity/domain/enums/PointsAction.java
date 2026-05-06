// common/enums/PointsAction.java
package com.shifterwebapp.shifter.identity.domain.enums;

public enum PointsAction {
    BUY_COURSE(3, "Purchase a course"),
    REVIEW_COURSE(1, "Submit a course review"),
    COMPLETE_COURSE(5, "Complete a course"),
    DAILY_LOGIN(1, "Daily login streak"),
    REFER_FRIEND(10, "Refer a friend"),
    COMPLETE_PROFILE(2, "Complete your profile"),
    FIRST_QUIZ_PASSED(3, "Pass your first quiz");

    private final int points;
    private final String description;

    PointsAction(int points, String description) {
        this.points = points;
        this.description = description;
    }

    public int getPoints() {
        return points;
    }

    public String getDescription() {
        return description;
    }
}