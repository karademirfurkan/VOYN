package com.furkankarademir.voyn.ParentClassesForActivity;

import com.furkankarademir.voyn.Classes.User;

public abstract class Invitation {
    private String ownerID;
    private String attendeeID;
    private String activityID;

    private User owner;

    public Invitation(String ownerID, String attendeeID, String activityID)
    {
        this.ownerID = ownerID;
        this.attendeeID = attendeeID;
        this.activityID = activityID;
    }

    public String getOwnerID()
    {
        return ownerID;
    }

    public String getAttendeeID()
    {
        return attendeeID;
    }

    public String getActivityID()
    {
        return activityID;
    }
}
