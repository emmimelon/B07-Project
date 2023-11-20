package com.example.b07project.ui.events;

public class EventsModel {
    String eventName;
    String eventLocation;
    String eventDate;

    public EventsModel(String eventName, String eventLocation, String eventDate) {
        this.eventName = eventName;
        this.eventLocation = eventLocation;
        this.eventDate = eventDate;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public String getEventDate() {
        return eventDate;
    }
}
