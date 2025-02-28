import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import { getUserNameRequest } from '@/api/user'

export const useUserCounterStore = defineStore('user', () => {
  const user = ref({
    username: '',
    password: '',
    email: '',
  })

  const testPassword = computed(() => {
    const password = user.value.password
    let reminder = ''
    let progress = 100

    // at least one number
    if (!/\d/.test(password)) {
      progress -= 35
      reminder = 'should contain at least one number'
    }
    // at least one capital letter
    if (!/(.*[A-Z].*)/.test(password)) {
      progress -= 35
      reminder = 'should contain at least one capital letter'
    }

    // more than 5 digits
    if (password.length < 6) {
      progress -= 30
      reminder = 'password length should be more then 5'
    }

    return {
      progress: progress,
      reminder: reminder,
    }
  })

  const testEmail = computed(() => {
    const email = user.value.email
    if (/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(email)) {
      return true
    }
    return false
  })

  const clearUserPassword = () => {
    user.value.password = ''
  }

  const getUserName = computed(() => {
    return user.value.username
  })

  const setUserName = async () => {
    const data = await getUserNameRequest()
    user.value.username = data.data
  }

  const reset = () => {
    user.value = {
      username: '',
      password: '',
      email: '',
    }
  }

  return {
    user,
    testPassword,
    testEmail,
    clearUserPassword,
    getUserName,
    setUserName,
    reset,
  }
})
