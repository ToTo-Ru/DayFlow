package io.notecalendar.Mapper;

import io.notecalendar.Pojo.MySQLTable.Event;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper
public interface EventMapper {
    @Insert("insert into event(user_id, event_name, date_start, date_end, done, goal_id) values (#{userId}, #{eventName}, #{dateStart}, #{dateEnd}, #{done}, #{goalId})")
    int addEvent(Event event);

    @Select("select * from event where user_id = #{userId}")
    ArrayList<Event> findEventsByUserId(int userId);

    @Select("SELECT * FROM event WHERE date_start >= #{startDate} AND date_start < DATE_ADD(#{endDate}, INTERVAL 1 DAY) AND user_id = #{userId}")
    ArrayList<Event> findEventsByDateRange(int userId, String startDate, String endDate);

    @Update("UPDATE event SET goal_id = NULL WHERE goal_id = #{goalId} AND user_id = #{userId}")
    void unlinkRelatedEvents(Integer goalId, Integer userId);

    @Update("update event set event_name = #{event.eventName}, date_start = #{event.dateStart}, date_end = #{event.dateEnd}, done = #{event.done}, goal_id = #{event.goalId} where user_id = #{userId} and id = #{event.id}")
    int updateEvent(Event event, int userId);

    @Delete("delete from event where id = #{eventId} AND user_id = #{userId}")
    int deleteEvent(Integer eventId, Integer userId);
}
