// adding Event/todo/goal from user
import request from '@/utils/request'

export const addingEvent = (eventPojo) => {
  return request.post('/calendar', eventPojo)
}

export const getAllEvents = (startDate, endDate) => {
  return request.get('/event/this-week', {
    params: {
      startDate: startDate,
      endDate: endDate,
    },
  })
}

export const getAllTodos = (startDate, endDate) => {
  return request.get('/todo/this-week', {
    params: {
      startDate: startDate,
      endDate: endDate,
    },
  })
}
export const updateTodoStateRequest = (id, done) => {
  return request.patch(`/todo/${id}?done=${done}`)
}

export const getAllGoals = () => {
  return request.get('/goal')
}

export const getUndoList = () => {
  return request.get('/todo/undo')
}

export const deleteTodo = (id) => {
  return request.delete(`/todo/${id}`)
}

export const updateTodoInfo = (todo) => {
  return request.put('/todo', todo)
}

export const getGoalEventTodo = () => {
  return request.get('/goal/eventTodo')
}

export const deleteGoal = (goalId) => {
  return request.delete(`/goal/${goalId}`)
}

export const editGoal = (goal) => {
  return request.patch('/goal', {
    id: goal.id,
    goalName: goal.goalTitle,
    done: goal.goalCompleted ? 1 : 0,
  })
}

export const updateEventInfo = (event) => {
  return request.put('/event', event)
}

export const deleteEventById = (eventId) => {
  return request.delete(`/event/${eventId}`)
}
