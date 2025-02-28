<template>
  <div class="event">
    <div class="event-time"><slot name="time"></slot></div>
    <div class="event-title"><slot name="title"></slot></div>
    <slot name="tag"></slot>
  </div>
</template>

<script setup></script>

<style lang="scss" scoped>
.event {
  margin: 1rem 0.5rem;
  box-shadow: 0px 2px 5px #bac2c4;
  padding: 1rem;
  border-radius: 5px;
  cursor: pointer;
  .event-title {
    font-weight: 600;
    font-size: 0.8rem;
  }
  .event-time {
    font-size: 0.7rem;
    color: gray;
  }
}
</style>
