<template>
  <div class="note-top">
    <div class="date">{{ month + ' ' + year }}</div>
    <div class="right-item">
      <div class="date-picker">
        <VDatePicker v-model="datePicked" mode="date">
          <template v-slot:default="{ togglePopover }">
            <vs-button
              icon
              :color="buttonColor"
              style="border-radius: 20px; padding: 0.1rem"
              @click="togglePopover"
            >
              <font-awesome-icon :icon="['far', 'calendar']" />
              <span style="font-size: 0.9rem; margin-left: 0.5rem"
                >calendar</span
              >
            </vs-button>
          </template>
        </VDatePicker>
      </div>
      <div class="user-icon">
        <vs-button
          shape="circle"
          :color="buttonColor"
          type="gradient"
          @click="toggle"
        >
          <font-awesome-icon
            :icon="['fas', 'user']"
            style="margin-right: 0.3rem"
          />
          {{ userCounterStore.getUserName }}
        </vs-button>
        <Menu ref="menu" id="overlay_menu" :model="items" :popup="true" />
      </div>
    </div>
  </div>
</template>

<script setup>
import router from '@/router'
import { useCalendarStore } from '@/stores/EventToDo'
import { useUserCounterStore } from '@/stores/userInfo'
import { PrimeIcons } from '@primevue/core/api'
import { ref, watch } from 'vue'

const userCounterStore = useUserCounterStore()
const calendar = useCalendarStore()

const buttonColor = ref('#6f91b5')

//date
const year = ref()

const monthsAbbr = [
  'JAN',
  'FEB',
  'MAR',
  'APR',
  'MAY',
  'JUN',
  'JUL',
  'AUG',
  'SEP',
  'OCT',
  'NOV',
  'DEC',
]
const month = ref()
watch(
  () => calendar.selectedDate,
  (newDate, oldDate) => {
    if (isNaN(oldDate)) {
      month.value = monthsAbbr[newDate.getMonth()]
      year.value = newDate.getFullYear()
    } else {
      if (newDate.getMonth() != oldDate.getMonth()) {
        month.value = monthsAbbr[newDate.getMonth()]
      }
      if (newDate.getFullYear() != oldDate.getFullYear()) {
        year.value = newDate.getFullYear()
      }
    }
  },
  {
    immediate: true,
  },
)

//calendar
const datePicked = ref(new Date())
const menu = ref(null)

const items = [
  {
    label: 'logout',
    command: () => {
      // TODO clear remember me
      localStorage.removeItem('NoteToken')
      calendar.reset()
      userCounterStore.reset()
      router.push('/')
    },
    icon: PrimeIcons.SIGN_OUT,
  },
]

const toggle = (event) => {
  if (menu.value) {
    menu.value.toggle(event)
  }
}
watch(
  () => datePicked.value,
  (newdate) => {
    calendar.setSelectedDate(newdate)
  },
)
</script>

<style lang="scss" scoped>
.note-top {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.date {
  font-size: 3rem;
  font-weight: 700;
}
.right-item {
  display: flex;
  gap: 1.5rem;
  align-items: center;
}
.date-picker {
  position: relative;
}
</style>
