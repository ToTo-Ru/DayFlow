<template>
  <div class="note-week">
    <ul>
      <li>MON</li>
      <li>TUE</li>
      <li>WED</li>
      <li>THU</li>
      <li>FRI</li>
      <li>SAT</li>
      <li>SUN</li>
    </ul>
  </div>
  <div class="date">
    <ul>
      <li
        v-for="(date, index) in weekFullDate"
        :key="index"
        :class="{ today: date.toDateString() == new Date().toDateString() }"
      >
        {{ date.getDate() }}
      </li>
    </ul>
  </div>
</template>
<script setup>
import { useCalendarStore } from '@/stores/EventToDo'
import { weekInDateFormate } from '@/utils/getTime'
import { ref, watch } from 'vue'
const calendarStore = useCalendarStore()
const weekFullDate = ref([])
// calculate the date of this week

// If selectedDate updated, update weekFullDate
watch(
  () => calendarStore.selectedDate,
  (newDate) => {
    weekFullDate.value = weekInDateFormate(newDate)
    console.log(weekFullDate.value[0] + new Date())
  },
  { immediate: true }, // Run immediately on mount to ensure first time setup
)
</script>

<style lang="scss" scoped>
.note-week {
  margin-top: 1rem;
}
ul {
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  gap: 1rem;
}
.note-week li {
  padding: 0.5rem;
  border-bottom: 2px solid #000;
  transition: transform 0.3s;
  &:hover {
    transform: translateY(-4px);
  }
}
.date li {
  margin: 1rem;
  text-align: center;
  font-size: 2rem;
  font-weight: 800;
  cursor: default;
}
.today {
  background-color: #b798b7;
  border-radius: 2rem;
  color: whitesmoke;
}
</style>
