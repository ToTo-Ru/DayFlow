package cathy.chen.utils;

import cathy.chen.Pojo.MySQLTable.Event;
import cathy.chen.Pojo.MySQLTable.EventControllerDTO;
import cathy.chen.Pojo.MySQLTable.Goal;
import cathy.chen.Pojo.MySQLTable.Todo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// * done: 0 as default
// * will add userId from LocalThread
public class CovertPojoUtil {

    public static Event convertToEvent(EventControllerDTO pojo) {

        String date = pojo.getDate();
        String startTime = pojo.getStartTime();
        String endTime = pojo.getEndTime();

        LocalDateTime startTimeLD = LocalDateTime.parse(date + " " + startTime,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDateTime endTimeLD = LocalDateTime.parse(date + " " + endTime,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        return new Event(pojo.getId(), ThreadLocalUtil.getUserId(), pojo.getTitle(), startTimeLD, endTimeLD, 0,
                pojo.getGoal() == null || pojo.getGoal().isEmpty() ? null : Integer.parseInt(pojo.getGoal()));

    }
    public static Todo convertToToDo(EventControllerDTO pojo){
        LocalDate localDate = LocalDate.parse(pojo.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return new Todo(null, ThreadLocalUtil.getUserId(), pojo.getTitle(), localDate, 0,
                (pojo.getGoal() == null || pojo.getGoal().isEmpty()) ? null : Integer.parseInt(pojo.getGoal()));
    }
    public static Goal convertToGoal(EventControllerDTO pojo){
        return new Goal(null, ThreadLocalUtil.getUserId(), pojo.getTitle(), 0);
    }


}
