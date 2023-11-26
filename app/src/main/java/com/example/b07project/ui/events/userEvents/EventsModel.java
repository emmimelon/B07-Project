package com.example.b07project.ui.events.userEvents;

public class EventsModel {
    String eventName, eventLocation, eventDate, eventDescription;

    public EventsModel(String eventName, String eventLocation, String eventDate,
                       String eventDescription) {
        this.eventName = eventName;
        this.eventLocation = eventLocation;
        this.eventDate = eventDate;
        this.eventDescription = eventDescription;
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
    public String getEventDescription() {return eventDescription;}


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
