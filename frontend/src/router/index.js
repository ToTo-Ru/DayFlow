import { createRouter, createWebHistory } from 'vue-router'
import LoginPage from '@/views/LoginPage.vue'
import NoteCalendar from '@/views/NoteCalendar.vue'
import { useCalendarStore } from '@/stores/EventToDo'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      component: LoginPage,
    },
    {
      path: '/noteCalendar',
      component: NoteCalendar,
    },
  ],
})

router.beforeEach(async (to, from, next) => {
  const calendar = useCalendarStore()
  const token = localStorage.getItem('NoteToken')

  if (token) {
    try {
      if (!calendar.isLoaded) {
        await calendar.setSelectedDate(new Date())
        await calendar.fetchInitialData()
      }

      if (to.path === '/') {
        next('/noteCalendar')
      } else {
        next()
      }
    } catch (error) {
      console.log('Token failed or could not fetch data: ' + error)
      if (to.path !== '/') {
        next('/')
      } else {
        next()
      }
    }
  } else {
    if (to.path !== '/') {
      next('/')
    } else {
      next()
    }
  }
})

export default router
