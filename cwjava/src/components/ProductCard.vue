<template>
  <div class="card">
    <div class="card-body">
      <h5 class="card-title">
        <router-link class="text-slate-900 decoration-from-font hover:text-teal-800"
                     :to="{name: 'product', params: {id: product.id}}"> {{ name }}
        </router-link>
      </h5>
      <div class="product-images">
        <div class="image-slider overflow-hidden">
          <div class="image-slider overflow-hidden">
            <div v-if="images.length > 0">
              <div v-for="(image, index) in images">
                <div v-if="isActive(index)">
                  <img :key="index" :src="image.data" class="ml-0 product-image overflow-hidden"/>
                </div>
                <div v-else>
                  <img :key="index" :src="image.data" class="ml-0 product-image overflow-hidden" style="display:none;"/>
                </div>
              </div>
            </div>
            <div v-else>
              <img src="https://placehold.co/600x400" alt="ml-0 product-image" class="w-full rounded-lg sm:w-40">
            </div>
          </div>
        </div>
        <div class="slider-controls">
<!--          <button @click="slide(-1)">❮</button>-->
<!--          <button @click="slide(1)">❯</button>-->
            <button @click="prevSlide">❮</button>
            <button @click="nextSlide">❯</button>
        </div>
      </div>
      <p class="card-text">Price: {{ price }}</p>
      <p class="card-text">Rating: {{ rating }}</p>
    </div>
    <div class="flex buttons w-full add_cart justify-content-evenly">
      <button v-if="cart_prod.quantity > 0" class="inline-block " @click="incQuantity"><h4>+</h4></button>
      <button v-if="cart_prod.quantity == 0" class="add_cart" @click="addToCart" >
        <span>Add to Cart</span>
      </button>
      <span class="add_cart inline-block" v-else> {{ cart_prod.quantity }} </span>
      <button v-if="cart_prod.quantity > 0" class="inline-block" @click="decQuantity"><h4>-</h4></button>
    </div>
  </div>
</template>


<script>
import store from "@/store";
import {ref} from "@vue/reactivity";

export default {
  name: "ProductCard",
  watch:{
    currentSlide(newVal) {
      const buttons = document.querySelectorAll('.slider-controls button');
      buttons.forEach((button, index) => {
        if (index === newVal) {
          button.disabled = true;
        } else {
          button.disabled = false;
        }
      });
    },
  },
  props: {
    product: {
      required: true,
    },
    name: {
      type: String,
      required: true,
    },
    images: {
      type: Array,
      required: true,
    },
    price: {
      type: Number,
      required: true,
    },
    rating: {
      type: Number,
      required: true,
    },
  },
  data() {
    return {
      currentSlide: 0,
      activeIndex: 0,
      quantity: 0,
      msg: "Add to cart",
      cart_prod: {
        quantity: 0,
        product: this.product,
        total: 0
      }
    };
  },
  created() {
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
    },
    isActive(index) {
      return index === this.currentSlide;
    },
    nextSlide() {
      this.currentSlide = (this.currentSlide + 1) % this.images.length;
    },
    prevSlide() {
      this.currentSlide = (this.currentSlide - 1 + this.images.length) % this.images.length;
    },
  },
};
</script>

<style scoped>

.slider-controls {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  display: flex;
  justify-content: space-between;
  width: 100%;
}
.slider-controls button {
  font-size: 2rem;
  background: none;
  border: none;
  cursor: pointer;
}
.product-image {
  display: block;
  margin: 0 auto;
  max-height: 250px;
  width: auto;
  max-width: 100%;
}

.image-slider {
  position: relative;
}

.image-slider .active {
  display: block;
}

.slider-controls {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  width: 100%;
  text-align: center;
}

.slider-controls button {
  background-color: transparent;
  border: none;
  color: #aaa;
  font-size: 20px;
  cursor: pointer;
  padding: 0 10px;
}

.slider-controls button:hover {
  color: #333;
}

.card {
  display: flex;
  flex-direction: column;
  width: 25%;
}

.add_cart {
  background-color: #1c1e22;
  color: white;
  border: none;
  padding: 6px 12px;
  text-align: center;
  text-decoration: none;
  font-size: 14px;
  margin-bottom: 0;
  font-weight: 400;
  line-height: 1.5;
  border-radius: 4px;
  transition: color 0.15s ease-in-out,background-color 0.15s ease-in-out,border-color 0.15s ease-in-out,box-shadow 0.15s ease-in-out;
  cursor: pointer;
  margin-right: 5px;
  display: flex;
  justify-content: space-evenly;

}

.card-text {
  display: flex;
  justify-content: space-between;
  padding-top: 10px;
  align-items: center;
}


.product-images {
  display: flex;
  position: relative;
  flex-direction: column;
  background: white;
  border-radius: 15px;
  max-width: 300px;
  justify-content: center;
  place-content: center;
  place-items: center;
  overflow: hidden;
  padding: 10px;
  transition: 0.5s;
}

</style>