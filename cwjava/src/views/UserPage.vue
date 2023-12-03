<template>
  <div class="hidden">{{getUserr()}}</div>
  <div class="container">
    <div class="flex flex-col items-center">
      <h1 class="text-3xl font-bold mb-4">User Information</h1>
      <div class="bg-white shadow overflow-hidden sm:rounded-lg w-2/3">
        <div class="px-4 py-5 sm:px-6">
          <h3 class="text-lg leading-6 font-bold text-gray-900">User Details</h3>
          <div class="mt-5 grid grid-cols-3 gap-5">
            <div class="col-span-1">
              <p class="text-md font-bold text-gray-600">Username:</p>
              <p class="text-lg text-gray-900">{{ this.userr.username }}</p>
            </div>
            <div class="col-span-1">
              <p class="text-md font-bold text-gray-600">First Name:</p>
              <p class="text-lg text-gray-900">{{ this.userr.firstName }}</p>
            </div>
            <div class="col-span-1">
              <p class="text-md font-bold text-gray-600">Last Name:</p>
              <p class="text-lg text-gray-900">{{ this.userr.lastName }}</p>
            </div>
            <div class="col-span-1">
              <p class="text-md font-bold text-gray-600">Email:</p>
              <a href="mailto:{{ this.userr.email }}" class="text-lg text-gray-900 hover:underline">{{ this.userr.email }}</a>
            </div>
            <div class="col-span-1">
              <p class="text-md font-bold text-gray-600">Address:</p>
              <p class="text-lg text-gray-900">{{ this.userr.address }}</p>
            </div>
            <div class="col-span-1">
              <p class="text-md font-bold text-gray-600">Role:</p>
              <p class="text-lg text-gray-900">{{ this.userr.role }}</p>
            </div>
          </div>
          <router-link to="/upd">Update User</router-link>
        </div>
        <div class="border-t border-gray-200 px-4 py-5 sm:px-6">
          <h3 class="text-lg leading-6 font-bold text-gray-900">Orders</h3>
          <div class="mt-5 flex flex-col items-center">
            <table class="min-w-full divide-y divide-gray-200">
              <thead class="bg-gray-50">
              <tr>
                <th scope="col"
                    class="px-6 py-3 text-left text-md font-bold text-gray-600 uppercase tracking-wider">
                  ID
                </th>
                <th scope="col"
                    class="px-6 py-3 text-left text-md font-bold text-gray-600 uppercase tracking-wider">
                  Creation Date
                </th>
                <th scope="col"
                    class="px-6 py-3 text-left text-md font-bold text-gray-600 uppercase tracking-wider">
                  Status
                </th>
                <th scope="col"
                    class="px-6 py-3 text-left text-md font-bold text-gray-600 uppercase tracking-wider">
                  Total Price
                </th>
                <th scope="col"
                    class="px-6 py-3 text-left text-md font-bold text-gray-600 uppercase tracking-wider">
                  Discount
                </th>
              </tr>
              </thead>
              <tbody class="bg-white divide-y divide-gray-200">
              <tr v-for="order in this.userr.orders" :key="order.id">
                <td class="px-6 py-4 whitespace-nowrap">
                  <div class="text-md font-medium text-gray-900">{{ order.id }}</div>
                </td>
                <td class="px-6 py-4 whitespace-nowrap">
                  <div class="text-md font-medium text-gray-900">{{ order.creationDate }}</div>
                </td>
                <td class="px-6 py-4 whitespace-nowrap">
                  <div class="text-md font-medium text-gray-900">{{ order.status }}</div>
                </td>
                <td class="px-6 py-4 whitespace-nowrap">
                  <div class="text-md font-medium text-gray-900">{{ order.totalPrice }}</div>
                </td>
                <td class="px-6 py-4 whitespace-nowrap">
                  <div class="text-md font-medium text-gray-900">{{ order.discount }}</div>
                </td>
              </tr>
              </tbody>
            </table>
            <TicketTable
            :tickets="tickets"
            />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import store from "@/store";
import UserService from "@/service/user.service";
import AuthService from "@/service/auth.service";
import axios from "axios";
import TicketTable from "@/components/TicketTable.vue";
import {resolveTransitionHooks} from "vue";

export default {
  name: 'Profile',
  components: {TicketTable},
  computed: {
    store() {
      return store
    }
  },
  data() {
    return{
      userr:null,
      tickets:null
    }
  },
  created() {

      axios.get(`http://server_cw:8080/api/user/${store.state.auth.user.username}`).then(
          value => this.userr = value.data
      )
      axios.get(`http://server_cw:8080/api/ticket/u/${store.state.auth.userInfo.id}`).then(
          value => this.tickets = value.data
      )
  },
  mounted() {
    this.getUserr();
    this.currentUser();
  },
  methods:{
    getUserr() {
      if (!this.userr) {
        this.currentUser().then(value => this.userr = value);
      }
      return this.userr;
    },
    async currentUser() {
      await store.commit('auth/fetchUserInf')
      this.userr = await store.getters["auth/getUserInf"];
      return this.userr;
    }
  }
};
</script>