import { createApp } from 'vue'
import { createPinia } from 'pinia'
import {
  VsAlert,
  VsButton,
  VsCheckbox,
  VsInput,
  VsNotification,
  VsScrollbar,
  VsDialog,
  VsRadio,
  VsSelect,
  VsOption,
} from 'vuesax-alpha'
// import '@/style/base.scss'
import { fas } from '@fortawesome/free-solid-svg-icons'
import { far } from '@fortawesome/free-regular-svg-icons'

import App from './App.vue'
import router from './router'
import 'vuesax-alpha/theme-chalk/index.css'
import 'vuesax-alpha/theme-chalk/dark/css-vars.css'
import '@/style/common.scss'
import '@/style/primevue.css'

import { library } from '@fortawesome/fontawesome-svg-core'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'

import PrimeVue from 'primevue/config'
import Aura from '@primevue/themes/aura'
import { definePreset } from '@primevue/themes'
import VCalendar from 'v-calendar'
import 'v-calendar/style.css'

// Use plugin with optional defaults

const app = createApp(App)
app.use(VCalendar, {})

library.add(fas, far)

app.use(createPinia())
app.use(router)
app
  .use(VsButton)
  .use(VsAlert)
  .use(VsInput)
  .use(VsCheckbox)
  .use(VsScrollbar)
  .use(VsNotification)
  .use(VsDialog)
  .use(VsRadio)
  .use(VsSelect)
  .use(VsOption)

app.component('font-awesome-icon', FontAwesomeIcon)

const MyPreset = definePreset(Aura, {})
app.use(PrimeVue, {
  theme: {
    preset: MyPreset,
  },
})

app.mount('#app')
