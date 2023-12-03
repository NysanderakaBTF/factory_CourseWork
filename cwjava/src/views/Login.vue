<template>
  <div class="h-screen flex">
    <div class="hidden lg:flex w-full lg:w-1/2 login_img_section
          justify-around items-center">
      <div
          class="
            bg-black
            opacity-20
            inset-0
            z-0"
      >
      </div>
      <div class="w-full mx-auto px-20 flex-col items-center space-y-6">
        <h1 class="text-white font-bold text-4xl font-sans">Customer Profile</h1>
        <p class="text-white mt-1">A bette3r way to shop</p>
        <div class="flex justify-center lg:justify-start mt-6">
          <router-link to='/register' class="hover:bg-indigo-700 hover:text-white hover:-translate-y-1 transition-all duration-500 bg-white text-indigo-800 mt-4 px-4 py-2 rounded-2xl font-bold mb-2">Get Started</router-link>
        </div>
      </div>
    </div>
      <div class="container">



        <Form name="form" @submit="handleLogin" class="bg-white rounded-md shadow-2xl p-5">
          <h1 class="text-gray-800 font-bold text-2xl mb-1">Hello Again!</h1>
          <p class="text-sm font-normal text-gray-600 mb-8">Welcome Back</p>
          <div class="flex items-center border-2 mb-8 py-2 px-3 rounded-2xl">
            <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 text-gray-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 12a4 4 0 10-8 0 4 4 0 008 0zm0 0v1.5a2.5 2.5 0 005 0V12a9 9 0 10-9 9m4.5-1.206a8.959 8.959 0 01-4.5 1.207" />
            </svg>
            <Field
                v-model="user.username"
                class="pl-2 w-full outline-none border-none"
                type="text"
                name="username"
                placeholder="username"
                :rules="validateUsername"
            />
            <ErrorMessage name="username"/>
          </div>
          <div class="flex items-center border-2 mb-12 py-2 px-3 rounded-2xl ">
            <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 text-gray-400" viewBox="0 0 20 20" fill="currentColor">
              <path fill-rule="evenodd" d="M5 9V7a5 5 0 0110 0v2a2 2 0 012 2v5a2 2 0 01-2 2H5a2 2 0 01-2-2v-5a2 2 0 012-2zm8-2v2H7V7a3 3 0 016 0z" clipRule="evenodd" />
            </svg>
            <Field
                class="pl-2 w-full outline-none border-none"
                placeholder="Password"
                v-model="user.password"
                type="password"
                name="password"
                :rules="validatepassword"
            />
            <ErrorMessage name="password"/>
          </div>
          <button class="block w-full bg-indigo-600 mt-5 py-2 rounded-2xl hover:bg-indigo-700 hover:-translate-y-1 transition-all duration-500 text-white font-semibold mb-2">Login</button>
          <div class="form-group">
            <div v-if="message" class="block w-fit bg-gray-400 mt-5" role="alert">{{message}}</div>
          </div>

          <div class="flex justify-between mt-4">

            <router-link to="/register" class="text-sm ml-2 hover:text-blue-500 cursor-pointer hover:-translate-y-1 duration-500 transition-all">Don't have an account yet?</router-link>
          </div>

        </Form>
      </div>
    </div>
</template>

<script>
import User from '@/models/user';
import { Form, Field ,ErrorMessage } from 'vee-validate';
import store from  "@/store";

export default {
  name: 'Login',
  components: {
    Form,
    Field,
    ErrorMessage
  },
  data() {
    return {
      user: new User('', ''),
      loading: false,
      message: ''
    };
  },
  computed: {
    loggedIn() {
      return store.state.auth.status.loggedIn;
    }
  },
  created() {
    if (this.loggedIn) {
      this.$router.push('/user');
    }
  },
  methods: {
    validateUsername(value) {
      // if the field is empty
      if (!value) {
        return 'This field is required';
      }
      // All is good
      return true;
    },
    validatepassword(value) {
      // if the field is empty
      if (!value) {
        return 'This field is required';
      }
      // All is good
      return true;
    },
    handleLogin() {
      this.loading = true;

      if (this.user.username && this.user.password) {
        store.dispatch('auth/login', this.user).then(
            () => {
              this.$router.push('/home');
            },
            error => {
              this.loading = false;
              this.message =
                  (error.response && error.response.data) ||
                  error.message ||
                  error.toString();
            }
        );
        store.commit('auth/fetchUserInf');
      }
    }
  }
};
</script>

<style>
.login_img_section {
  background: linear-gradient(rgba(2, 2, 2, .7), rgba(0, 0, 0, .7)), url("@/assets/photo-1.webp") center center;

}
</style>