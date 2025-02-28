import { computed, ref } from 'vue'
import { defineStore } from 'pinia'
import {
  getAllEvents,
  getAllGoals,
  getAllTodos,
  getGoalEventTodo,
  getUndoList,
  updateTodoStateRequest,
} from '@/api/calendar'
import { weekInYMD, weekOnlyDate } from '@/utils/getTime'
import { isInRange } from '@/utils/testResponseCode'

// Event example
// 1. dateEnd: "2025-02-17T21:20:00"
// 2. dateStart: "2025-02-17T20:43:00"
// 3. done: 0
// 4. eventName: "a new meeting"
// 5. goalId: null
// 6. id: 2
// 7. userId: 11

export const useCalendarStore = defineStore('useCalendarStore', () => {
  // This event is for 7 days shown on the user table
  const selectedDate = ref(null)
  const isLoaded = ref(false)

  const events = ref([])
  const todo = ref([])
  const undo = ref([])
  const goals = ref([])

  const thisWeekDD = computed(() => weekOnlyDate(selectedDate.value))

  const setEvents = async () => {
    const week = weekInYMD(selectedDate.value)
    const data = await getAllEvents(week[0], week[6])
    events.value = data.data
  }

  const setGoal = async () => {
    const data = await getAllGoals()
    goals.value = data.data
  }

  const setTodos = async () => {
    const week = weekInYMD(selectedDate.value)
    const data = await getAllTodos(week[0], week[6])

    //as noteItem todo using boolean to check, need to change 1/0 to true/false first
    const todos = data.data.map((todo) => {
      return {
        ...todo,
        done: todo.done == 1,
      }
    })
    todo.value = todos
  }

  const setSelectedDate = async (newSelectedDate) => {
    if (newSelectedDate && !isNaN(newSelectedDate.getTime())) {
      selectedDate.value = newSelectedDate
      await setEvents()
      await setTodos()
    } else {
      console.error('Invalid date during setting in store:', newSelectedDate)
    }
  }

  const setIsLoaded = () => {
    isLoaded.value = true
  }

  const updateTodoState = async (id, done) => {
    let newDone = 0

    newDone = done == true ? 1 : 0

    const data = await updateTodoStateRequest(id, newDone)
    await setTodos()
    if (!isInRange(data.code)) {
      console.log('update failed')
    }
  }

  const getCalendarItems = computed(() => {
    if (isLoaded.value == false) {
      return []
    }

    const weekTime = weekInYMD(selectedDate.value)
    const orderedItems = weekTime.map((date) => {
      const dayEvent = []
      const dayTodo = []

      for (let index = 0; index < events.value.length; index++) {
        if (events.value[index].dateStart.slice(0, 10) == date) {
          dayEvent.push(events.value[index])
        }
      }

      for (let index = 0; index < todo.value.length; index++) {
        if (todo.value[index].date.slice(0, 10) == date) {
          dayTodo.push(todo.value[index])
        }
      }
      return {
        date,
        dayEvent,
        dayTodo,
      }
    })
    return orderedItems
  })

  const getGoalLinkedList = async () => {
    // get data
    const data = await getGoalEventTodo()

    // change goal name
    const result = data.data.map((goal) => {
      //handle event / todo
      const items = [...goal.events, ...goal.todos]

      const newItems = items.map((item) => ({
        label: item.eventName || item.todoName,
        key: item.eventName ? `event-${item.id}` : `todo-${item.id}`,
        isTopLevel: false, // used for html logo
      }))

      return {
        isTopLevel: true, // used for html logo
        label: goal.goalName,
        key: goal.goalId,
        items: newItems,
      }
    })
    return result
    //   {
    //     label: 'Item 1',
    //     key: 'item1',
    //     items: [
    //       { label: 'Subitem 1.1', key: 'subitem1.1' },
    //       { label: 'Subitem 1.2', key: 'subitem1.2' },
    //     ],
    //   },
    //   {
    //     label: 'Item 2',
    //     key: 'item2',
    //     items: [{ label: 'Subitem 2.1', key: 'subitem2.1' }],
    //   },
    // ]
  }

  const setUndo = async () => {
    const data = await getUndoList()
    undo.value = data.data
  }
  const getUndo = computed(() => {
    return undo.value
  })
  const getTodo = computed(() => {
    return todo.value
  })
  const getEvents = computed(() => {
    return events.value
  })
  const getGoal = computed(() => {
    return goals.value
  })
  const fetchInitialData = async () => {
    await setSelectedDate(new Date())
    await setGoal()
    await setUndo()
    await getGoalLinkedList()
    setIsLoaded()
  }

  const reset = () => {
    selectedDate.value = null

    events.value = []
    todo.value = []
    undo.value = []
    goals.value = []

    isLoaded.value = false
  }
  return {
    events,
    todo,
    isLoaded,
    selectedDate,
    thisWeekDD,
    setSelectedDate,
    goals,
    getCalendarItems,
    setIsLoaded,
    updateTodoState,
    setGoal,
    getGoalLinkedList,
    setUndo,
    getUndo,
    setTodos,
    getTodo,
    getEvents,
    getGoal,
    reset,
    setEvents,
    fetchInitialData,
  }
})
