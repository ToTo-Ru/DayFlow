<template>
  <div class="note-items">
    <ul>
      <li v-for="(item, index) in calendarStore.getCalendarItems" :key="index">
        <vs-scrollbar height="45vh">
          <div class="container">
            <NoteEventItem
              v-for="(event, eventIndex) in item.dayEvent"
              :key="eventIndex"
              @click="handleOpenEventForm(event)"
            >
              <template #time
                >{{ event.dateStart.slice(11, 16) }} -
                {{ event.dateEnd.slice(11, 16) }}</template
              >
              <template #title>{{ event.eventName }}</template>
              <template #tag v-if="event.goalId">
                <NoteTag>{{ getGoalName(event.goalId) }}</NoteTag>
              </template>
            </NoteEventItem>
            <div class="toDoList">
              <div
                class="to-do-item"
                v-for="(todo, todoIndex) in item.dayTodo"
                :key="todoIndex"
              >
                <div class="setting-logo" @click="handleClickTodoSetting(todo)">
                  <font-awesome-icon :icon="['fas', 'gear']" />
                </div>
                <vs-checkbox
                  v-model="todo.done"
                  @change="handleTodoState(todo)"
                >
                  {{ todo.todoName }}
                </vs-checkbox>
                <div class="tag" v-show="todo.goalId">
                  <NoteTag>{{ getGoalName(todo.goalId) }}</NoteTag>
                </div>
              </div>
            </div>
          </div>
        </vs-scrollbar>
      </li>
      <!-- Example -->
      <!-- <li>
        <vs-scrollbar height="45vh">
          <div class="container">
            <NoteEventItem>
              <template #time>11:00-12:00</template>
              <template #title>Java 100-110</template>
              <template #tag>Info</template>
            </NoteEventItem>

            <div class="toDoList">
              <div class="to-do-item">
                <vs-checkbox v-model="option" :line-through="option">
                  Take a bath
                </vs-checkbox>
                <div class="tag">
                  <Tag severity="info" value="Info" rounded></Tag>
                </div>
              </div>
            </div>
          </div>
        </vs-scrollbar>
      </li>-->
    </ul>
  </div>
  <!-- Dialog for todo -->
  <div class="edit-todo-form">
    <vs-dialog v-model="editTodoFormShown" prevent-close>
      <template #header>
        <h4>Edit your todo</h4>
      </template>

      <div class="item-form">
        <vs-input
          v-model="todoInForm.todoName"
          style="width: 100%"
          label="title"
        />
        <vs-input v-model="todoInForm.date" type="date" label="Date" />
        <!-- Note: VsSelect v-model warning is a known issue, does not affect functionality -->
        <vs-select
          v-model="todoInForm.goalId"
          placeholder="Select"
          label="Goal"
        >
          <vs-option
            v-for="goal in calendarStore.goals"
            :key="goal.id"
            :label="goal.goalName"
            :value="goal.id"
            v-show="goal.done != 1"
          >
            {{ goal.goalName }}
          </vs-option>
        </vs-select>
      </div>

      <template #footer>
        <div class="con-footer">
          <vs-button
            type="transparent"
            color="danger"
            @click="handleDeleteTodo()"
          >
            Delete
          </vs-button>
          <vs-button type="transparent" @click="handleUpdateTodo()">
            Confirm
          </vs-button>
        </div>
      </template>
    </vs-dialog>
  </div>

  <div class="edit-event-form">
    <vs-dialog v-model="editEventFormShown" prevent-close>
      <template #header>
        <h4>Edit your event</h4>
      </template>

      <vs-input
        style="width: 100%"
        v-model="eventInForm.title"
        label="Title"
        placeholder="A new meeting"
      />
      <div class="time">
        <vs-input v-model="eventInForm.date" type="date" label="Date" />

        <!-- only will be shown when user select event -->
        <vs-input
          v-model="eventInForm.startTime"
          type="time"
          label="starting time"
        />
        <vs-input
          v-model="eventInForm.endTime"
          type="time"
          label="ending time"
        />
      </div>

      <vs-select
        style="width: 4rem"
        v-model="eventInForm.goal"
        placeholder="Select"
        label="Goal"
      >
        <vs-option
          v-for="goal in calendarStore.goals"
          :key="goal.id"
          :label="goal.goalName"
          :value="goal.id"
          v-show="goal.done != 1"
        >
          {{ goal.goalName }}
        </vs-option>
      </vs-select>

      <template #footer>
        <div class="con-footer">
          <vs-button
            type="transparent"
            color="danger"
            @click="handleDeleteEvent()"
          >
            Delete
          </vs-button>
          <vs-button type="transparent" @click="submitEventEdit()">
            Confirm
          </vs-button>
        </div>
      </template>
    </vs-dialog>
  </div>
</template>

<script setup>
import {
  deleteEventById,
  deleteTodo,
  updateEventInfo,
  updateTodoInfo,
} from '@/api/calendar'
import { useCalendarStore } from '@/stores/EventToDo'
import { isInRange } from '@/utils/testResponseCode'
import { ref, watch } from 'vue'
// // import { storeToRefs } from 'pinia'
// import { ref } from 'vue'
const calendarStore = useCalendarStore()

const editTodoFormShown = ref(false)
const editEventFormShown = ref(false)

const todoInForm = ref(null)
const eventInForm = ref(null)

const handleTodoState = async (todo) => {
  calendarStore.updateTodoState(todo.id, todo.done)
  await calendarStore.setUndo()
}
const handleClickTodoSetting = (todo) => {
  editTodoFormShown.value = true
  const newTodo = { ...todo }
  todoInForm.value = newTodo
  console.log(todoInForm.value)
}
const getGoalName = (goalId) => {
  if (!goalId) {
    return ''
  }
  const goal = calendarStore.goals.find((goal) => goalId == goal.id)
  return goal.goalName
}

const handleDeleteTodo = async () => {
  const data = await deleteTodo(todoInForm.value.id)
  if (!isInRange(data.code)) {
    console.log(data.message)
  }
  await calendarStore.setTodos()
  editTodoFormShown.value = false
}

const handleUpdateTodo = async () => {
  todoInForm.value.done = todoInForm.value.done == true ? 1 : 0
  const data = await updateTodoInfo(todoInForm.value)
  if (!isInRange(data)) {
    console.log(data.message)
  }
  await calendarStore.setTodos()
  editTodoFormShown.value = false
}

// For Event Form
const handleOpenEventForm = (event) => {
  const newEvent = {
    ...event,
    title: event.eventName,
    date: event.dateStart.slice(0, 10),
    startTime: event.dateStart.slice(11, 16),
    endTime: event.dateEnd.slice(11, 16),
    goal: event.goalId,
  }
  eventInForm.value = newEvent
  editEventFormShown.value = true
}

const submitEventEdit = async () => {
  const data = await updateEventInfo(eventInForm.value)
  if (!isInRange(data.code)) {
    console.log('Event update failed')
  } else {
    editEventFormShown.value = false
  }
  await calendarStore.setEvents()
}

const handleDeleteEvent = async () => {
  const data = await deleteEventById(eventInForm.value.id)
  if (!isInRange(data.code)) {
    console.log('Event delete failed')
  } else {
    editEventFormShown.value = false
  }
  await calendarStore.setEvents()
}

watch(
  () => calendarStore.undo,
  () => {
    calendarStore.setTodos()
  },
  { deep: true },
)
</script>

<style lang="scss" scoped>
.note-items ul {
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  gap: 1rem;
}
li {
  .container {
    .toDoList {
      margin-top: 2rem;
    }
    .to-do-item {
      display: flex;
      flex-wrap: wrap;
      margin-bottom: 1rem;
    }
    .tag {
      margin-left: 1.7rem;
      margin-top: 0.35rem;
    }
  }
}

.to-do-item {
  position: relative;
}
.setting-logo {
  color: lightgray;
  opacity: 0;
  transition: opacity 0.3s ease;
  position: absolute;
  right: 0.6rem;
  top: 50%;
  transform: translateY(-50%);
}

.to-do-item:hover .setting-logo {
  opacity: 1;
}
.setting-logo:hover {
  cursor: pointer;
}
.vs-checkbox {
  max-width: calc(100% - 1.5rem);
}
.time {
  display: flex;
  gap: 0.7rem;
}
.con-footer {
  display: flex;
  justify-content: end;
}
</style>
