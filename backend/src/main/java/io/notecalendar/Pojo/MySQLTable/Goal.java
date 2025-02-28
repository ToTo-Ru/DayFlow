package io.notecalendar.Pojo.MySQLTable;

public class Goal {
    private Integer id;
    private Integer userId;
    private String goalName;
    private Integer done;

    public Goal(Integer id, Integer userId, String goalName, Integer done) {
        this.id = id;
        this.userId = userId;
        this.goalName = goalName;
        this.done = done;
    }

    public Goal() {
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

    public String getGoalName() {
        return goalName;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    public Integer getDone() {
        return done;
    }

    public void setDone(Integer done) {
        this.done = done;
    }

    @Override
    public String toString() {
        return "Goal{" +
                "id=" + id +
                ", userId=" + userId +
                ", goalName='" + goalName + '\'' +
                ", done=" + done +
                '}';
    }
}
