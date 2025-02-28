<template>
  <div class="note-desc">
    <Inplace>
      <template #display>
        <span class="pink">
          <font-awesome-icon :icon="['fas', 'bullseye']" />
        </span>
        {{ text || 'Write down your wishes for this month' }}
      </template>
      <template #content="{ closeCallback }">
        <span class="input-area">
          <span class="pink">
            <font-awesome-icon :icon="['fas', 'bullseye']" />
          </span>

          <InputText
            v-model="text"
            autofocus
            @vue:mounted="$event.el?.focus()"
            v-on:keydown.enter="closeCallback"
          />

          <vs-button icon color="success" type="flat" @click="closeCallback">
            <font-awesome-icon :icon="['fas', 'check']" />
          </vs-button>
        </span>
      </template>
    </Inplace>
  </div>
</template>

<script setup></script>

<style lang="scss" scoped>
.note-desc {
  // margin-top: 0.5rem;
}
.input-area {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}
.pink {
  color: #f65e5d;
  // margin-right: 1rem;
}
</style>
