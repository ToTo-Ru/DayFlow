package io.notecalendar.Pojo.MySQLTable;

import java.time.LocalDate;

public class Todo {
    private Integer id;
    private Integer userId;
    private String todoName;
    private LocalDate date;
    private Integer done;
    private Integer goalId;

    public Todo() {
    }

    public Todo(Integer id, Integer userId, String todoName, LocalDate date, Integer done, Integer goalId) {
        this.id = id;
        this.userId = userId;
        this.todoName = todoName;
        this.date = date;
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

    public String getTodoName() {
        return todoName;
    }

    public void setTodoName(String todoName) {
        this.todoName = todoName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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
        return "Todo{" +
                "id=" + id +
                ", userId=" + userId +
                ", todoName='" + todoName + '\'' +
                ", date=" + date +
                ", done=" + done +
                ", goalId=" + goalId +
                '}';
    }
}
