<template>
  <div class="login-item fixed-center">
    <h1>Register</h1>
    <vs-alert v-if="registerFail" :color="'warn'"> Register Failed </vs-alert>

    <div class="input-group">
      <vs-input
        style="width: 20rem; font-size: 0.8rem"
        v-model="user.username"
        color="#7d33ff"
        input-style="shadow"
        placeholder="username"
        type="text"
      >
        <template #icon>
          <font-awesome-icon :icon="['far', 'user']" />
        </template>
        <template v-if="!user.username" #message-danger> Required </template>
        <template v-else-if="user.username.length < 4" #message-warn>
          Username should contain at least 4 charter
        </template>
        <template v-else #message-success> Success </template>
      </vs-input>

      <vs-input
        style="width: 20rem"
        v-model="user.password"
        color="#7d33ff"
        input-style="shadow"
        placeholder="Password"
        type="password"
        :progress="testPassword.progress"
      >
        <template #icon>
          <font-awesome-icon :icon="['fas', 'lock']" />
        </template>
        <template v-if="!user.password" #message-danger> Required </template>
        <template v-else-if="testPassword.progress < 100" #message-warn>
          {{ testPassword.reminder }}
        </template>
        <template v-else #message-success> Success </template>
      </vs-input>

      <vs-input
        style="width: 20rem; font-size: 0.8rem"
        v-model="user.email"
        color="#7d33ff"
        input-style="shadow"
        placeholder="email address"
        type="text"
      >
        <template #icon>
          <font-awesome-icon :icon="['fas', 'at']" />
        </template>
        <template v-if="!user.email" #message-danger> Required </template>
        <template v-else-if="!testEmail" #message-warn>
          Email formate is not correct
        </template>
        <template v-else #message-success> Success </template>
      </vs-input>

      <!-- TODO add @click Register function + check if formate correct-->
      <vs-button type="gradient" @click="registerAccount"> Register </vs-button>
    </div>
    <div class="desc">
      <p>Already have an account?</p>

      <span @click="toggleLogin">Login</span>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { storeToRefs } from 'pinia'
import { useUserCounterStore } from '@/stores/userInfo'

import { register } from '@/api/login'
import { isInRange } from '@/utils/testResponseCode'
const userStore = useUserCounterStore()
const { user, testPassword, testEmail } = storeToRefs(userStore)

const registerFail = ref(false)

const emit = defineEmits(['toggleLogin', 'registerSuccessNotice'])

const toggleLogin = () => {
  emit('toggleLogin')
}

const registerAccount = async () => {
  const data = await register(
    user.value.username,
    user.value.password,
    user.value.email,
  )
  if (isInRange(data.code)) {
    emit('registerSuccessNotice')
    emit('toggleLogin')
  } else {
    registerFail.value = true
  }
}
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
