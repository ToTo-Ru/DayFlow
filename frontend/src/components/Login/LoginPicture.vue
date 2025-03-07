<script setup></script>

<template>
  <div class="login-picture">
    <div class="box">
      <div class="move-block no-select">
        <!-- TODO add the best calendar app description -->
        <h2 class="dancing-script">DayFlow</h2>
        <!-- <p class="text-xl">
          This calender app helps you check how far have you been and planning
          your next goal
        </p> -->
      </div>
      <!-- TODO: modify description of the website -->
    </div>
  </div>
</template>

<style lang="scss" scoped>
.login-picture {
  height: 100vh;
  width: 100vw;
  // TODO: switch picture if have time
  background-image: url('@/static/vladyslav-tobolenko-NsJVEx4r2XU-unsplash.jpg');
  background-size: cover;
  background-position: center;

  position: relative;
}
.box {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
h2,
p {
  color: rgba(255, 255, 255);
  text-shadow: 1px 1px 2px rgba(186, 112, 112, 0.7);
}
h2 {
  padding: 1rem;
  text-align: center;
  font-weight: bold;
  font-size: 30rem;
  width: 100vw;
  height: 100vh;
}
p {
  padding: 2rem 4rem 2rem 4rem;
  text-align: left;
  font-size: 1.5rem;
}
</style>
