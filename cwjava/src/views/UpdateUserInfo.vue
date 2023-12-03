<template>
  <div class="max-w-md mx-auto mt-8">
    <form @submit.prevent="updateUser" class="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4">
      <div class="mb-4">
        <label for="email" class="block text-gray-700 font-bold mb-2">Email:</label>
        <input type="email" id="email" v-model="user.email" required
               class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline">
      </div>
      <div class="mb-4">
        <label for="firstName" class="block text-gray-700 font-bold mb-2">First Name:</label>
        <input type="text" id="firstName" v-model="user.firstName" required
               class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline">
      </div>
      <div class="mb-4">
        <label for="lastName" class="block text-gray-700 font-bold mb-2">Last Name:</label>
        <input type="text" id="lastName" v-model="user.lastName" required
               class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline">
      </div>
      <div class="mb-4">
        <label for="address" class="block text-gray-700 font-bold mb-2">Address:</label>
        <textarea id="address" v-model="user.address"
                  class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"></textarea>
      </div>
      <button type="submit"
              class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline">
        Update User
      </button>
    </form>
  </div>
</template>
<script>
import UserService from '@/service/user.service';
import store from "@/store";
export default {
  data(){
    return{
      user:null
    }
  },
  created() {
    this.user = store.getters["auth/getUserInf"];
  },
  methods: {
    updateUser() {
      UserService.updateInfo(this.user.id, this.user)
          .then(() => {
            // User successfully updated
            alert('User successfully updated:'+this.user);
          })
          .catch(error => {
            // Error occurred while updating user
            alert('Error updating user:'+ error);
          });
    }
  }
};
</script>