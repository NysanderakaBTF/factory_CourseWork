<template>
  <div class="h-screen bg-gray-100 pt-20">
    <h1 class="mb-10 text-center text-2xl font-bold">Cart Items</h1>
    <div class="mx-auto max-w-5xl justify-center px-6 md:flex md:space-x-6 xl:px-0">
      <div class="rounded-lg md:w-2/3 flex-col">
        <OrderItem  v-for="item in this.items" :product="item"/>
      </div>
      <!-- Sub total -->
      <div class="mt-6 h-full rounded-lg border bg-white p-6 shadow-md md:mt-0 md:w-1/3">
        <div class="flex justify-between">
          <p class="text-lg font-bold">Total</p>
          <div class="">
            <p class="mb-1 text-lg font-bold">{{store.getters["cart/getTotal"]}} BYR</p>
            <p class="text-sm text-gray-700">including VAT</p>
          </div>
        </div>
        <router-link to="/checkout" v-if="store.state.auth.user!==null">
        <button class="mt-6 w-full rounded-md bg-blue-500 py-1.5 font-medium text-blue-50 hover:bg-blue-600" @click="postOrder">Check out</button>
        </router-link>
        <router-link v-else to="/login">
          <button class="mt-6 w-full rounded-md bg-blue-500 py-1.5 font-medium text-blue-50 hover:bg-blue-600">Login</button>
        </router-link>
      </div>
    </div>
  </div>

</template>

<script>
import store from "@/store";
import OrderItem from "@/components/OrderItem.vue";
import axios from "axios";

export default {
  name: "CartPage",
  components: {OrderItem},
  watch:{
    'store.state.cart.products'(newProducts){
      this.items = newProducts;
    }
  },
  computed: {
    store() {
      return store
    }
  },
  data() {
    return {
      items: []
    }
  },
  mounted() {
    this.items = store.getters["cart/getAllProducts"];
  },
  methods:{
    postOrder(){
      const token = store.state.auth.token.tokenValue;
      let date = new Date(Date.now())
      const st = date.toISOString().substring(0,10);
      axios.post('http://server_cw:8080/api/orders/new',{
        "creationDate":st,
        "status":"CREATED",
        "totalPrice":store.getters["cart/getTotal"]
      },{
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`
        },
      }).then(value => {
        console.log(store.getters["cart/getAllProducts"])
        axios.post(`http://server_cw:8080/api/orders/${value.data.id}/items/addbulk`,store.getters["cart/getAllProducts"],{
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`
          }
        })
      })
    }
  }
}
</script>

<style scoped>

</style>