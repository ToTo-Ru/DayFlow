<template>
  <div class="note-calendar">
    <div class="left">
      <NoteSideBar></NoteSideBar>
    </div>
    <div class="right">
      <NoteTop></NoteTop>
      <NoteWeeks></NoteWeeks>
      <NoteItems></NoteItems>
    </div>

    <div class="addButton">
      <vs-button
        icon
        color="#6f91b5"
        type="gradient"
        @click="itemDialog = !itemDialog"
        size="xl"
      >
        <font-awesome-icon :icon="['fas', 'plus']" />
      </vs-button>
    </div>
    <!-- Dialog for adding new event/todo -->

    <div class="addingForm">
      <vs-dialog v-model="itemDialog">
        <template #header>
          <h4 style="font-weight: 700">Add a new schedule</h4>
        </template>

        <div class="item-form">
          <vs-alert color="warn" v-show="reminderShown">
            {{ reminder }}
          </vs-alert>
          <div class="type">
            <h4>Type</h4>
            <vs-radio v-model="eventPojo.type" value="1"> Event </vs-radio>
            <vs-radio v-model="eventPojo.type" value="2"> Todo </vs-radio>
            <vs-radio v-model="eventPojo.type" value="3"> Goal </vs-radio>
          </div>
          <vs-input
            style="width: 100%"
            v-model="eventPojo.title"
            label="Title"
            placeholder="A new meeting"
          />
          <div class="time">
            <vs-input
              v-show="eventPojo.type == 1 || eventPojo.type == 2"
              v-model="eventPojo.date"
              type="date"
              label="Date"
            />

            <!-- only will be shown when user select event -->
            <vs-input
              v-show="eventPojo.type == 1"
              v-model="eventPojo.startTime"
              type="time"
              label="starting time"
            />
            <vs-input
              v-show="eventPojo.type == 1"
              v-model="eventPojo.endTime"
              type="time"
              label="ending time"
            />
          </div>

          <div v-show="eventPojo.type == 1 || eventPojo.type == 2">
            <vs-select
              v-show="eventPojo.type != 3"
              style="width: 4rem"
              v-model="eventPojo.goal"
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
        </div>

        <template #footer>
          <div class="con-footer">
            <vs-button type="transparent" @click="submitEvent">
              Confirm
            </vs-button>
          </div>
        </template>
      </vs-dialog>
    </div>
  </div>
</template>

<script setup>
import NoteSideBar from '@/components/NoteCalendar/NoteSideBar.vue'
import NoteTop from '@/components/NoteCalendar/NoteTop.vue'

import NoteWeeks from '@/components/NoteCalendar/NoteWeeks.vue'
import NoteItems from '@/components/NoteCalendar/NoteItems.vue'
import { reactive, onMounted, ref } from 'vue'
import { VsNotification } from 'vuesax-alpha'
import { VsDialog, VsRadio, VsSelect, VsOption } from 'vuesax-alpha'
import { addingEvent } from '@/api/calendar'
import { isInRange } from '@/utils/testResponseCode'
import { useCalendarStore } from '@/stores/EventToDo'
import { getTodayDate, getTodayTime } from '@/utils/getTime'
import { useUserCounterStore } from '@/stores/userInfo'
const calendarStore = useCalendarStore()
const userStore = useUserCounterStore()

// TODO add content as your next todo
// Notification
const openNotification = (position, border, duration, title) => {
  VsNotification({
    duration,
    border,
    position,
    title,
  })
}

// onMounted
onMounted(async () => {
  await userStore.setUserName()
  openNotification(
    'top-center',
    '#527590',
    2500,
    userStore.getUserName + ', Welcome back to your journal! ',
  )
})

// Dialog form for adding items
const itemDialog = ref(false)
//show or not
const reminderShown = ref(false)
const reminder = ref('')

//this pojo could not use ref, as it is an object

// initialState of eventPojo
const initialState = {
  type: '1',
  title: '',
  date: getTodayDate(),
  startTime: getTodayTime(),
  endTime: '',
  goal: null,
}
const eventPojo = reactive({ ...initialState })

async function submitEvent() {
  const result = checkingDialogForm() //check if all the information is correct
  if (result) {
    eventPojo.type = +eventPojo.type //change to integer when transfer
    const data = await addingEvent(eventPojo)
    // if code 200
    if (isInRange(data.code)) {
      // reset the table and remind user success
      reminderShown.value = false
      itemDialog.value = false
      openNotification('top-center', '#98BF64', 2500, 'adding successfully!')

      // if adding a new goal, get the goalList again
      if (eventPojo.type == 3) {
        await calendarStore.setGoal()
      }

      // refresh the page
      calendarStore.setSelectedDate(calendarStore.selectedDate)

      Object.assign(eventPojo, initialState)
    } else {
      reminder.value = 'adding failed'
      reminderShown.value = true
    }
  }
  // TODO 如有時間，將dialog變成component
}
// function: show the reminder and return false for checking form
function showDialogReminder(word) {
  reminder.value = word
  reminderShown.value = true
  return false
}

function checkingDialogForm() {
  if (!eventPojo.title) {
    return showDialogReminder('Please enter a title')
  }
  switch (eventPojo.type) {
    // this is Event
    case '1':
      // check if null
      if (!eventPojo.startTime || !eventPojo.endTime) {
        return showDialogReminder('Please enter the time')
      }
      // check if endtime earlier than start time
      if (eventPojo.startTime > eventPojo.endTime) {
        return showDialogReminder(
          'Ending time should not be earlier than the starting time',
        )
      }
      return true

    // this is todo
    case '2':
      if (!eventPojo.date) {
        return showDialogReminder('Please enter the date')
      }
      return true
  }
  return true // for case 3: Goal, title had been checked
}
</script>

<style lang="scss" scoped>
.note-calendar {
  position: relative;
  background-color: #f7f9fb;
  display: grid;
  grid-template-columns: 1fr 4fr;
  height: 100vh;
  overflow: hidden;
}
.right {
  padding: 3rem 3rem 0 3rem;
  min-height: 100%;
  max-height: 100vh;
  color: #3d4249;
}
.addButton {
  position: absolute;
  right: 5%;
  bottom: 10%;
}
.item-form {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  h4 {
    font-weight: 600;
    // font-size: 1.3rem;
  }
  .type {
    display: flex;
    gap: 0.8rem;
  }
  .time {
    display: flex;
    gap: 0.7rem;
  }
}
.con-footer {
  display: flex;
  justify-content: end;
}
</style>
