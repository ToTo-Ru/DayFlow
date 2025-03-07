<template>
  <div class="login-page">
    <LoginPicture></LoginPicture>
    <div class="login-form">
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
.login-page {
  position: relative;
  width: 100vw;
  height: 100vh;
  overflow: hidden;
  .login-form::before {
    content: '';
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    height: 70%;
    width: 30%;
    min-width: 400px;
    min-height: 400px;

    background-color: rgba(255, 255, 255, 0.568);
    backdrop-filter: blur(10px);
    opacity: 0.8;
    border-radius: 30px;
  }
}
</style>
