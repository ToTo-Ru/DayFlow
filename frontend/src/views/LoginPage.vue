<template>
  <div class="login-page">
    <div class="container">
      <div class="left"><LoginPicture></LoginPicture></div>
      <div class="right">
        <LoginItem
          @toggleLogin="toggleLogin"
          v-show="isLogin"
          :registerSuccess="registerSuccess"
        ></LoginItem>
        <RegisterItem
          @toggleLogin="toggleLogin"
          @registerSuccessNotice="registerSuccessNotice"
          v-show="!isLogin"
        ></RegisterItem>
      </div>
    </div>
  </div>
</template>

<script setup>
import LoginItem from '@/components/Login/LoginItem.vue'
import LoginPicture from '@/components/Login/LoginPicture.vue'
import RegisterItem from '@/components/Login/RegisterItem.vue'
import { ref } from 'vue'

// * Here is to control the toggle of Login and Register
const isLogin = ref(true)
const toggleLogin = () => {
  isLogin.value = !isLogin.value
}

const registerSuccess = ref(false)
const registerSuccessNotice = () => (registerSuccess.value = true)
</script>

<style lang="scss" scoped>
.container {
  display: grid; /* 使用 CSS Grid */
  grid-template-columns: 1fr 1fr; /* 分為兩列，每列 50% */
  width: 100%;
  height: 100vh;
}

.right {
  position: relative;
}
</style>
