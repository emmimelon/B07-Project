package com.example.b07project.ui.events;

public class EventsModel {
    String eventName, eventLocation, eventDate;

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

    @Override
    public boolean equals(Object obj){
        if(obj instanceof EventsModel){
            EventsModel m = (EventsModel) obj;
            if(m.getEventDate().equals(this.getEventDate()) &&
                    m.getEventLocation().equals(this.getEventLocation()) &&
                    m.getEventName().equals(this.eventName)){
                return true;
            }
        }
        return false;
    }
}
