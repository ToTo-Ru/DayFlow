<template>
  <div class="note-side-bar">
    <div class="nav">
      <vs-button
        type="transparent"
        :color="buttonColor"
        :active="active == 0"
        @click="active = 0"
      >
        Goal
      </vs-button>
      <vs-button
        type="transparent"
        :color="buttonColor"
        :active="active == 1"
        @click="active = 1"
      >
        Todo List
      </vs-button>
      <!-- <vs-button
        type="transparent"
        :color="buttonColor"
        :active="active == 2"
        @click="active = 2"
      >
        Achieve
      </vs-button> -->
    </div>

    <div class="input-todo">
      <vs-input
        style="font-size: 0.8rem"
        v-model="todo"
        color="#6F8191"
        placeholder="Add a quick todo by enter"
        icon-after
        v-on:keyup.enter="handleAddTodoToday(todo)"
      >
        <template #icon>
          <font-awesome-icon :icon="['fas', 'pen']" />
        </template>
      </vs-input>
    </div>

    <!-- List for Goal -->
    <div class="todo-list" v-show="active == 0">
      <vs-scrollbar height="60vh">
        <PanelMenu v-model:expandedKeys="expandedKeys" :model="menuItems">
          <template #item="{ item }">
            <div class="goal-item">
              <!-- Label area: click to expand -->
              <span class="goal-label">{{ item.label }}</span>
              <!-- Logo Area -->
              <span class="actions" v-if="item.isTopLevel">
                <span @click.stop="handleEditGoal(item)"
                  ><font-awesome-icon :icon="['fas', 'pencil']"
                /></span>
                <span class="red" @click.stop="handleDeleteGoal(item)"
                  ><font-awesome-icon :icon="['fas', 'trash']"
                /></span>
              </span>
            </div>
          </template>
        </PanelMenu>
      </vs-scrollbar>
    </div>

    <!-- List for simple todo -->
    <div class="todo-list" v-show="active == 1">
      <vs-scrollbar height="60vh">
        <div class="todo-box" v-for="todo in undoShown" :key="todo.id">
          <vs-checkbox
            :v-model="todo.todoName"
            :line-through="todo.done == 1"
            @change="handleTodoChange(todo)"
          >
            {{ todo.todoName }}
          </vs-checkbox>
        </div>
      </vs-scrollbar>
    </div>

    <!-- Edit Goal Form -->
    <vs-dialog v-model="editGoalForm">
      <template #header>
        <h4 class="bold">Edit your goal</h4>
      </template>

      <div class="con-form">
        <vs-input
          v-model="selectedGoal.goalTitle"
          label="Title"
          style="width: 100%"
        />
        <div>
          <span class="bold">State</span>
          <vs-checkbox
            v-model="selectedGoal.goalCompleted"
            color="success"
            label-before
          >
            {{ goalCompleted ? 'Completed' : 'Uncompleted' }}
          </vs-checkbox>
        </div>
      </div>

      <template #footer>
        <div class="con-footer">
          <vs-button type="transparent" @click="handleSubmitGoalEdit">
            Confirm
          </vs-button>
        </div>
      </template>
    </vs-dialog>

    <!-- Delete Confirmation -->
    <vs-dialog v-model="deleteGoalForm">
      <template #header>
        <h4 class="bold">Warning</h4>
      </template>

      <div class="reminder">
        Once your goal <span class="bold blue">{{ deleteGoalName }}</span> is
        deleted, all related data (including linkages) will be
        <span class="red bold">permanently lost and cannot be recovered. </span
        ><br /><br />
        Please confirm before proceeding.
      </div>

      <template #footer>
        <div class="con-footer">
          <vs-button
            type="transparent"
            color="danger"
            @click="confirmDeleteGoal"
          >
            Delete
          </vs-button>
        </div>
      </template>
    </vs-dialog>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue'
import PanelMenu from 'primevue/panelmenu'
import { useCalendarStore } from '@/stores/EventToDo'
import { getTodayDate, getTodayTime } from '@/utils/getTime'
import { addingEvent, deleteGoal, editGoal } from '@/api/calendar'
import { isInRange } from '@/utils/testResponseCode'
const calendarStore = useCalendarStore()
const buttonColor = ref('#f7f9fb')
const active = ref(0)

// TODO button select v-show
// input button
const todo = ref(null)

// Panel Menu
const expandedKeys = ref(new Map())
const menuItems = ref([])

const undoShown = ref([])

// Edit Goal Form
const editGoalForm = ref(false)
const selectedGoal = ref('')

// Delete Goal Form
const deleteGoalForm = ref(false)
const deleteGoalName = ref('')
const deleteGoalId = ref(null)

// init
watch(
  () => calendarStore.isLoaded,
  async (loaded) => {
    if (loaded) {
      menuItems.value = await calendarStore.getGoalLinkedList()

      undoShown.value = calendarStore.getUndo
    }
  },
  { immediate: true },
)

//when todo have a update, syn undoList
watch(
  () => calendarStore.todo,
  async () => {
    await calendarStore.setUndo()
    undoShown.value = calendarStore.getUndo
  },
  { deep: true },
)

// when todo/events changes, update the linkage
watch(
  [
    calendarStore.getTodo,
    calendarStore.getEvents,
    undoShown,
    calendarStore.getGoal,
  ],
  async () => {
    menuItems.value = await calendarStore.getGoalLinkedList()
  },
  { deep: true },
)

const handleTodoChange = async (todo) => {
  todo.done = !todo.done
  calendarStore.updateTodoState(todo.id, todo.done)
  await calendarStore.setTodos()
}

const handleAddTodoToday = async (title) => {
  const eventPojo = {
    type: '2',
    title: title,
    date: getTodayDate(),
    startTime: getTodayTime(),
    endTime: '',
    goal: '',
  }
  const data = await addingEvent(eventPojo)
  if (!isInRange(data.code)) {
    console.log('adding Todo Failed')
  }
  await calendarStore.setTodos()
  await calendarStore.setUndo()
  todo.value = ''
}
// For Panel Menu
const handleDeleteGoal = (item) => {
  deleteGoalForm.value = true
  deleteGoalName.value = item.label
  deleteGoalId.value = item.key
}

const confirmDeleteGoal = async () => {
  const data = await deleteGoal(deleteGoalId.value)
  if (!isInRange(data.code)) {
    console.log('delete failed')
  }
  menuItems.value = await calendarStore.getGoalLinkedList()
  deleteGoalForm.value = false
}

const handleEditGoal = (item) => {
  console.log(item)
  // set value
  selectedGoal.value = {
    id: item.key,
    goalTitle: item.label,
    goalCompleted: false,
  }
  // Open dialog
  editGoalForm.value = true
}

const handleSubmitGoalEdit = async () => {
  const data = await editGoal(selectedGoal.value)
  if (!isInRange(data.code)) {
    console.log(data.message)
  } else {
    await calendarStore.setGoal()
  }
}
</script>

<style lang="scss" scoped>
.note-side-bar {
  margin: 1rem;
  height: 95vh;
  background-color: #aba9c0;
  border-radius: 20px;
  // rgb(240, 240, 240)
  overflow: hidden;
}
.nav {
  display: flex;
  justify-content: space-around;
  margin: 1rem;
}
.input-todo {
  margin: 2rem;
}
.todo-list {
  margin: 2rem;
}
.todo-box {
  color: aliceblue;
}
.goal-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  padding: 0.5rem;
}

.goal-label {
  flex-grow: 1;
  font-size: 0.8rem;
  cursor: pointer;
}

.actions {
  :hover {
    cursor: pointer;
  }
  display: flex;
  gap: 0.8rem;
  color: rgb(138, 122, 144);
  opacity: 0;
  transition: opacity 0.2s ease;
}
.goal-item:hover .actions {
  opacity: 1;
}

.con-form {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}
.con-footer {
  display: flex;
  justify-content: end;
}
.red {
  color: rgb(214, 77, 77);
}
.bold {
  font-weight: 600;
}
.blue {
  color: rgb(61, 105, 162);
}
</style>
