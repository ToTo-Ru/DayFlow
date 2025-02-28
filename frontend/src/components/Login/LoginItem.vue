<!-- * Using this component should add position: relative -->
<template>
  <div class="login-item fixed-center">
    <h1>Login</h1>

    <vs-alert v-if="loginFailed" :color="'warn'">
      Login Failed: {{ loginFailedMessage }}
    </vs-alert>
    <vs-alert v-if="registerSuccess" :color="'success'">
      Register Success
    </vs-alert>

    <form>
      <div class="input-group">
        <vs-input
          style="width: 20rem; font-size: 0.8rem"
          v-model="user.email"
          color="#7d33ff"
          input-style="shadow"
          placeholder="email"
          type="text"
        >
          <template #icon>
            <font-awesome-icon :icon="['fas', 'at']" />
          </template>
          <template v-if="!user.email" #message-danger> Required </template>
        </vs-input>

        <vs-input
          style="width: 20rem"
          v-model="user.password"
          color="#7d33ff"
          input-style="shadow"
          placeholder="Password"
          type="password"
        >
          <template #icon>
            <font-awesome-icon :icon="['fas', 'lock']" />
          </template>
          <template v-if="!user.password" #message-danger> Required </template>
        </vs-input>

        <!-- TODO (maynot add) add remember me function
        <vs-checkbox v-model="option"> Remember Me </vs-checkbox> -->
        <vs-button type="gradient" @click.prevent="userLogin">
          Login
        </vs-button>
      </div>
    </form>
    <div class="desc">
      <p>Does not have an account yet?</p>

      <span @click="toggleLogin">Register</span>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { login } from '@/api/login'
import { useUserCounterStore } from '@/stores/userInfo'
import { storeToRefs } from 'pinia'
import { isInRange } from '@/utils/testResponseCode'
import router from '@/router'
import { useCalendarStore } from '@/stores/EventToDo'
// import { useCalendarStore } from '@/stores/EventToDo'

const userStore = useUserCounterStore()
const calendar = useCalendarStore()
const { user } = storeToRefs(userStore)
// const calendarStore = useCalendarStore()

const emit = defineEmits(['toggleLogin'])

// eslint-disable-next-line no-unused-vars
const props = defineProps({
  registerSuccess: {
    type: Boolean,
  },
})

const loginFailed = ref(false)
const loginFailedMessage = ref('')

// Login / Register
const toggleLogin = () => {
  emit('toggleLogin')
}

// @click Login button
const userLogin = async () => {
  const data = await login(user.value.email, user.value.password)
  if (isInRange(data.code)) {
    //remember me
    localStorage.setItem('NoteToken', data.data)
    calendar.reset()
    // // get data
    // await calendarStore.fetchInitialData()

    // TODO add local storage: set token
    router.push('/noteCalendar')
  } else {
    userStore.clearUserPassword()
    // loginFailedMessage.value = 'Login Failed'
    loginFailed.value = true
  }
}
// onMounted(async () => {
//   // test token
//   // TODO if NoteToken exist, need gain all data and jump to calendar
//   if (localStorage.getItem('NoteToken')) {
//     const data = await getAllGoals()
//     if (isInRange(data.code)) {
//       await calendarStore.fetchInitialData()
//       router.push('/noteCalendar')
//     }
//   }
//   // 直接獲取用戶的calendar資料
// })
</script>

<style lang="scss" scoped>
h1 {
  margin-bottom: 2rem;
  font-size: 2rem;
}
.input-group {
  margin-top: 1rem;
  margin-bottom: 1rem;
  display: flex;
  flex-direction: column;
  gap: 1rem;
}
.desc {
  display: flex;
  gap: 1rem;
  align-items: center;
}
p {
  font-size: 0.7rem;
}
span {
  display: block;
  color: lightseagreen;
  cursor: pointer;
  :hover {
    background-color: rgb(143, 206, 202);
  }
}
</style>
