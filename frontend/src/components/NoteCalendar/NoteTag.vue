<template>
  <div>
    <div class="tag">
      <slot></slot>
    </div>
  </div>
</template>

<script setup></script>

<style lang="scss" scoped>
.tag {
  display: inline-block;
  padding-left: 0.5rem;
  padding-right: 0.5rem;
  border-radius: 0.7rem;

  font-size: 0.7rem;
  background-color: rgb(203, 224, 231);
  color: rgb(10, 124, 176);
}
</style>
