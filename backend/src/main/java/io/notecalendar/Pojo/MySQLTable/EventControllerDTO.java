package io.notecalendar.Pojo.MySQLTable;

public class EventControllerDTO {
    private Integer type;
    private Integer id;
    private String title;
    private String date;
    private String startTime;
    private String endTime;
    private String goal;

    public EventControllerDTO() {
    }

    public EventControllerDTO(Integer type, Integer id, String title, String date, String startTime, String endTime, String goal) {
        this.type = type;
        this.id = id;
        this.title = title;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.goal = goal;
    }

    public EventControllerDTO(Integer type, String title, String date, String startTime, String endTime, String goal) {
        this.type = type;
        this.title = title;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.goal = goal;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    @Override
    public String toString() {
        return "EventControllerDTO{" +
                "type=" + type +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", goal='" + goal + '\'' +
                '}';
    }
}
