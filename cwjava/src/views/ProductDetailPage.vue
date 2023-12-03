<template>
  <div class="container flex flex-wrap sm:flex-col lg:flex-row ">
    <div class="sm:max-w-full lg:max-w-md sm:align-middle">
      <div class="active_img overflow-hidden">
        <img id='current' :src="product.productImageSet[0].data" alt="..." v-if="product.productImageSet.length !== 0"
             class="w-full rounded-lg items-stretch">
        <img v-else src="https://placehold.co/600x400" alt="product-image" class="w-full rounded-lg">
      </div>
      <div v-if="product.productImageSet.length > 1" class="flex flex-row">
        <img @click="change_img(index)" class="w-25 mt-1.5 mb-1.5 ml-1.5 rounded border-slate-500 border-3"
             :src="image.data" v-for="(image, index) in this.product.productImageSet" :key="index" :id="index"
             alt="...">
      </div>
    </div>
    <div class="lg:ml-10 flex w-fit flex-col">
      <h2>{{ product.name }}</h2>
      <p class="mt-2.5">
        {{ product.description }}
      </p>

      <div class="ratings">
        <div v-if="product.rating !== null && product.rating !== 0">
          <span v class="product-rating">{{ product.rating }}</span><span>/5</span>
          <div class="stars">
            <font-awesome-icon :icon="['fas', 'star']"
                               v-for="i in Array.from({ length: Math.round(product.rating)}, (_, i) => i)"/>
          </div>
        </div>
        <div v-else>
          No ratings yet
        </div>
      </div>

      <h4>
        Price: {{ product.price }} BYR
      </h4>
      <div class="flex justify-content-around align-content-stretch rounded bg-slate-700 text-gray-100 text-center max-w-sm">
        <span><button v-if="cart_prod.quantity > 0" class="inline-block w-25 " @click="incQuantity">+</button></span>
        <span>
          <button class="add_cart" @click="addToCart">
            <span v-if="cart_prod.quantity > 0">{{ cart_prod.quantity }} </span>
            <span v-else class="text-lg">Add to Cart</span>
          </button>
        </span>
        <span><button v-if="cart_prod.quantity > 0" class="inline-block w-25 " @click="decQuantity">-</button></span>
      </div>
      <comment-list
        :comments="product.comments"
        :product_id="product.id"
      />
    </div>
  </div>
</template>

<script>
import store from "@/store";
import axios from "axios";
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import {inRange} from "eslint-plugin-vue/lib/utils";
import CommentList from "@/components/CommentList.vue";

export default {
  name: "ProductPage",
  components: {CommentList, FontAwesomeIcon},
  props: {
    id: null
  },
  data() {
    return {
      product: null,
      cart_prod: {
        quantity: 0,
        product: this.product,
        total: 0
      }
    }
  },
  created() {
    axios.get(`http://server_cw:8080/api/products/${this.id}`)
        .then(value => {
          this.product = value.data
          if (this.product.productImageSet == null) {
            this.product.productImageSet = [];
          }
          console.log(this.product)
        })
        .catch(function (error) {
          console.log(error);
        });
  },
  mounted() {
    this.cart_prod = {
      quantity: 0,
      product: this.product,
      total: 0
    }
    let q = store.state.cart.products.find(value => value.product.id === this.product.id)
    if (q) {
      this.cart_prod = q;
    }
  },
  methods: {
    inRange,
    change_img(id) {
      let f = document.getElementById('current');
      f.src = this.product.productImageSet[id].data;
    },
    updateTotal() {
      this.cart_prod.total = this.cart_prod.quantity * this.cart_prod.product["price"];
    },
    addToCart() {
      this.msg = this.quantity;
      this.updateTotal();
      store.commit('cart/addProduct', this.cart_prod);
    },
    incQuantity() {
      this.updateTotal();
      store.commit("cart/updateQuantityById", {payload: this.cart_prod, increase: true})
      this.msg = this.cart_prod.quantity;
    },
    decQuantity() {
      this.updateTotal();
      if (this.cart_prod.quantity === 1) {
        this.cart_prod.quantity = 0;
        store.commit("cart/removeProductById", this.product["id"]);
      } else {
        store.commit("cart/updateQuantityById", {payload: this.cart_prod, increase: false})
      }
    }
  }
}
</script>

<style scoped>
body {
  margin: 0;
  padding: 0;
  font-family: 'Open Sans', serif;
  background: #eee;
}

.content {
  width: 420px;
  margin-top: 100px;
}


.product-rating {

  font-size: 50px;
}

.stars i {

  font-size: 18px;
  color: #28a745;
}

.rating-text {
  margin-top: 10px;
}

</style>