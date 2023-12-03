<script setup>
import { RouterLink, RouterView } from 'vue-router'

import Navbar from "@/components/UI/Navbar.vue";
import Footer from "@/components/UI/Footer.vue";
</script>

<template>
  <Navbar/>
    <router-view></router-view>
  <Footer/>
</template>

<style scoped>

</style>
