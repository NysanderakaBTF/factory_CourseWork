<template>
    <nav class="bg-gray-800">
      <div class="max-w-7xl mx-auto px-2 sm:px-6 lg:px-8">
        <div class="relative flex items-center justify-between h-16">
          <div class="absolute inset-y-0 left-0 flex items-center sm:hidden">
            <button
                @click="toggleMenu"
                type="button"
                class="inline-flex items-center justify-center p-2 rounded-md text-gray-400 hover:text-white hover:bg-gray-700 focus:outline-none focus:ring-2 focus:ring-inset focus:ring-white"
                aria-controls="mobile-menu"
                aria-expanded="false"
            >
              <span class="sr-only">Open main menu</span>
              <svg
                  class="block h-6 w-6"
                  xmlns="http://www.w3.org/2000/svg"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke="currentColor"
                  aria-hidden="true"
              >
                <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="M4 6h16M4 12h16M4 18h16"
                />
              </svg>
              <svg
                  class="hidden h-6 w-6"
                  xmlns="http://www.w3.org/2000/svg"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke="currentColor"
                  aria-hidden="true"
              >
                <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="M6 18L18 6M6 6l12 12"
                />
              </svg>
            </button>
          </div>
          <div class="flex-1 flex items-center justify-center sm:items-stretch sm:justify-start">
            <div class="flex-short">
              <router-link to="/" class="text-white font-bold text-lg">My App</router-link>
            </div>
            <div class="hidden sm:block sm:ml-6">
              <div class="flex space-x-4">
                <router-link to="/" class="text-gray-300 hover:bg-gray-700 hover:text-white px-3 py-2 rounded-md text-sm font-medium">Home</router-link>
                <router-link to="/store" class="text-gray-300 hover:bg-gray-700 hover:text-white px-3 py-2 rounded-md text-sm font-medium">Store</router-link>
                <router-link v-if="isLogged" to="/cart" class="text-gray-300 hover:bg-gray-700 hover:text-white px-3 py-2 rounded-md text-sm font-medium">Cart</router-link>
                <router-link v-if="isLogged" to="/ticket" class="text-gray-300 hover:bg-gray-700 hover:text-white block px-3 py-2 rounded-md text-base font-medium">Visit us</router-link>
                <router-link v-if="isLogged" :to="upath" class="text-gray-300 hover:bg-gray-700 hover:text-white px-3 py-2 rounded-md text-sm font-medium">User</router-link>
                <router-link v-if="!isLogged" class="text-gray-300 hover:bg-gray-700 hover:text-white px-3 py-2 rounded-md text-sm font-medium" to="/login">Login</router-link>
                <button v-else class="text-gray-300 hover:bg-gray-700 hover:text-white px-3 py-2 rounded-md text-sm font-medium" @click="logOut">Logout</button>
              </div>
            </div>
          </div>
        </div>
        <div :class="{'block': isMenuOpen, 'hidden': !isMenuOpen}" class="sm:hidden" id="mobile-menu">
          <div class="px-2 pt-2 pb-3 space-y-1">
            <router-link to="/" class="text-gray-300 hover:bg-gray-700 hover:text-white block px-3 py-2 rounded-md text-base font-medium">Home</router-link>
            <router-link to="/store" class="text-gray-300 hover:bg-gray-700 hover:text-white block px-3 py-2 rounded-md text-base font-medium">Store</router-link>
            <router-link v-if="isLogged" to="/cart" class="text-gray-300 hover:bg-gray-700 hover:text-white block px-3 py-2 rounded-md text-base font-medium">Cart</router-link>
            <router-link v-if="isLogged" to="/ticket" class="text-gray-300 hover:bg-gray-700 hover:text-white block px-3 py-2 rounded-md text-base font-medium">Visit us</router-link>
            <router-link v-if="isLogged" :to="upath" class="text-gray-300 hover:bg-gray-700 hover:text-white block px-3 py-2 rounded-md text-base font-medium">User</router-link>
            <router-link v-if="!isLogged" class="text-gray-300 hover:bg-gray-700 hover:text-white px-3 py-2 rounded-md text-sm font-medium" to="/login">Login</router-link>
            <button v-else class="text-gray-300 hover:bg-gray-700 hover:text-white px-3 py-2 rounded-md text-sm font-medium" @click="logOut">Logout</button>
          </div>
        </div>
      </div>
    </nav>


  </template>

<script>
import store from "@/store";

export default {
  name: "Navbar",
  data() {
    return {
      loggedIn:false,
      isMenuOpen: false,
    }
  },
  computed: {
    isLogged(){
      return store.state.auth.status.loggedIn
    },
    currentUser() {
      return this.$store.state.auth.user;
    },
    upath: function (){
      if (store.state.auth.status.loggedIn)
        return "/user";
      else return "#";
    }
  },
  methods: {

    logOut() {
      this.$store.dispatch('auth/logout');
      this.$router.push('/login');
    },
    toggleMenu(){
      this.isMenuOpen = !this.isMenuOpen
    },
  }


}
</script>

<style>


</style>