package io.notecalendar.Pojo.MySQLTable;

import java.time.LocalDateTime;

public class Event {
    private Integer id;
    private Integer userId;
    private String eventName;
    private LocalDateTime dateStart;
    private LocalDateTime dateEnd;
    private Integer done; // use Integer
    private Integer goalId;

    public Event() {
    }

    public Event(Integer id, Integer userId, String eventName, LocalDateTime dateStart, LocalDateTime dateEnd, Integer done, Integer goalId) {
        this.id = id;
        this.userId = userId;
        this.eventName = eventName;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.done = done;
        this.goalId = goalId;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public LocalDateTime getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDateTime dateStart) {
        this.dateStart = dateStart;
    }

    public LocalDateTime getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDateTime dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Integer getDone() {
        return done;
    }

    public void setDone(Integer done) {
        this.done = done;
    }

    public Integer getGoalId() {
        return goalId;
    }

    public void setGoalId(Integer goalId) {
        this.goalId = goalId;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", userId=" + userId +
                ", eventName='" + eventName + '\'' +
                ", dateStart=" + dateStart +
                ", dateEnd=" + dateEnd +
                ", done=" + done +
                ", goalId=" + goalId +
                '}';
    }
}
