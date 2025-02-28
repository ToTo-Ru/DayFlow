// adding Event/todo/goal from user
import request from '@/utils/request'

export const addingEvent = (eventPojo) => {
  return request.post('/calendar', eventPojo)
}

export const getAllEvents = (startDate, endDate) => {
  return request.get('/calendar/eventsThisWeek', {
    params: {
      startDate: startDate,
      endDate: endDate,
    },
  })
}

export const getAllTodos = (startDate, endDate) => {
  return request.get('/calendar/todoThisWeek', {
    params: {
      startDate: startDate,
      endDate: endDate,
    },
  })
}
export const updateTodoStateRequest = (id, done) => {
  return request.patch(`/calendar/todo/${id}?done=${done}`)
}

export const getAllGoals = () => {
  return request.get('calendar/goal')
}

export const getUndoList = () => {
  return request.get('calendar/undo')
}

export const deleteTodo = (id) => {
  return request.delete(`calendar/todo/${id}`)
}

export const updateTodoInfo = (todo) => {
  return request.put('/calendar/todo', todo)
}

export const getGoalEventTodo = () => {
  return request.get('/calendar/goal/eventTodo')
}

export const deleteGoal = (goalId) => {
  return request.delete(`/calendar/goal/${goalId}`)
}

export const editGoal = (goal) => {
  return request.patch('/calendar/goal', {
    id: goal.id,
    goalName: goal.goalTitle,
    done: goal.goalCompleted ? 1 : 0,
  })
}

export const updateEventInfo = (event) => {
  return request.put('/calendar/event', event)
}

export const deleteEventById = (eventId) => {
  return request.delete(`calendar/event/${eventId}`)
}
