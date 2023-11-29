package com.example.b07project.ui.events.admin_events;

public class AdminEventsModel {
    private String EventName;
    private String Date;
    private String Description;
    private String Location;
    private Long ParticipationLimit;


    // Default constructor required for calls to DataSnapshot.getValue(AdminEventsModel.class)
    public AdminEventsModel() {
    }

    // Constructor
    public AdminEventsModel(String event_name, String date, String description, String location, Long participationLimit) {
        this.EventName = event_name;
        this.Date = date;
        this.Description = description;
        this.Location = location;
        this.ParticipationLimit = participationLimit;
    }

    public String getEventName() {
        return EventName;
    }
    public String getDate() { return Date; }
    public String getDescription() {
        return Description;
    }
    public String getLocation() {
        return Location;
    }
    public Long getParticipationLimit() {
        return ParticipationLimit;
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof AdminEventsModel){
            AdminEventsModel m = (AdminEventsModel) obj;
            if(m.getParticipationLimit() == this.getParticipationLimit() &&
                    m.getDescription().equals(this.getDescription()) &&
                    m.getDate().equals(this.getDate()) &&
                    m.getLocation().equals(this.getLocation()) &&
                    m.getEventName().equals(this.getEventName())){
                return true;
            }
        }
        return false;
    }
}