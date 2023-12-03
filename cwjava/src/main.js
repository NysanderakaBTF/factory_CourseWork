import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import 'vue-rate/dist/vue-rate.css'
import rate from 'vue-rate'

import './assets/main.css'
import store from "@/store";

import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { library } from '@fortawesome/fontawesome-svg-core'


import {
    faHome,
    faUser,
    faUserPlus,
    faSignInAlt,
    faSignOutAlt,
    faStar
} from '@fortawesome/free-solid-svg-icons';
library.add(faHome, faUser, faUserPlus, faSignInAlt, faSignOutAlt, faStar);

const app = createApp(App)
app.component('font-awesome-icon', FontAwesomeIcon)
app.use(router).use(store).use(rate)

app.mount('#app')
