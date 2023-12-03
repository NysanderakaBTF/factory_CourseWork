
<template>
  <div class="justify-between mb-6 rounded-lg bg-white p-6 shadow-md sm:flex sm:justify-start">
    <img v-if="this.imagesURLS.length > 0" :src="this.imagesURLS[0]" alt="product-image" class="w-full rounded-lg sm:w-40" />
    <img v-else src="https://placehold.co/600x400" alt="product-image" class="w-full rounded-lg sm:w-40" />
    <div class="sm:ml-4 sm:flex sm:w-full sm:justify-between">
      <div class="mt-5 sm:mt-0">
        <h2 class="text-lg font-bold text-gray-900">{{this.product.product.name}}</h2>
      </div>
      <div class="mt-4 flex justify-between im sm:space-y-6 sm:mt-0 sm:block sm:space-x-6">
        <div class="flex items-center border-gray-100">
          <span class="cursor-pointer rounded-l bg-gray-100 py-1 px-3.5 duration-100 hover:bg-blue-500 hover:text-blue-50" @click="decQuantity"> - </span>
          <input class="h-8 w-8 border bg-white text-center text-xs outline-none" type="number" v-model="this.product.quantity" min="1" />
          <span class="cursor-pointer rounded-r bg-gray-100 py-1 px-3 duration-100 hover:bg-blue-500 hover:text-blue-50" @click="incQuantity"> + </span>
<!--          <button-->
<!--              @click="removeItem"-->
<!--              type="button"-->
<!--              class="inline-block rounded bg-danger px-6 pt-2.5 pb-2 text-xs font-medium uppercase leading-normal text-white shadow-[0_4px_9px_-4px_#dc4c64] transition duration-150 ease-in-out hover:bg-danger-600 hover:shadow-[0_8px_9px_-4px_rgba(220,76,100,0.3),0_4px_18px_0_rgba(220,76,100,0.2)] focus:bg-danger-600 focus:shadow-[0_8px_9px_-4px_rgba(220,76,100,0.3),0_4px_18px_0_rgba(220,76,100,0.2)] focus:outline-none focus:ring-0 active:bg-danger-700 active:shadow-[0_8px_9px_-4px_rgba(220,76,100,0.3),0_4px_18px_0_rgba(220,76,100,0.2)]">-->
<!--            Delete-->
<!--          </button>-->
        </div>
        <div class="flex items-center space-x-4">
          <p class="text-sm">{{this.product.total}} BYR</p>
          <svg
              @click="removeItem"
              xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="h-5 w-5 cursor-pointer duration-150 hover:text-red-500">
            <path stroke-linecap="round" stroke-linejoin="round" d="M6 18L18 6M6 6l12 12" />
          </svg>
        </div>
      </div>
    </div>
  </div>
</template>


<script>
import store from "@/store";
import {ref} from "@vue/reactivity";

export default {
  name: "OrderItem",
  props: {
    product:{
      required:true,
    }
  },
  data() {
    return {
      imagesURLS:[]
    };
  },
  mounted(){
    for (let imag of this.product.product.productImageSet) {
      console.log(imag)
      this.imagesURLS.push(imag.data)
    }
    console.log(this.product);
  },
  methods: {
    removeItem(){
      store.commit('cart/removeProductById',this.product.product.id)
    },
    updateTotal(){
      this.product.total = this.product.quantity * this.product.product["price"];
    },
    addToCart(){
      this.msg = this.quantity;
      this.updateTotal();
      store.commit('cart/addProduct', this.product);
    },
    incQuantity(){
      this.updateTotal();
      store.commit("cart/updateQuantityById",{payload:this.product,increase:true})
      this.msg = this.product.quantity;
    },
    decQuantity(){
      this.updateTotal();
      if (this.product.quantity === 1 ){
        this.product.quantity = 0;
        store.commit("cart/removeProductById", this.product["id"]);
      }else {
        store.commit("cart/updateQuantityById", {payload: this.product, increase: false})
      }
    },
    isActive(image) {
      return image === this.images[this.activeIndex];
    },
    slide(direction) {
      this.activeIndex += direction;
      if (this.activeIndex < 0) {
        this.activeIndex = this.images.length - 1;
      } else if (this.activeIndex >= this.images.length) {
        this.activeIndex = 0;
      }
    },
  },
};
</script>

<style scoped>


.btntoggle{
  display: none;

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
.card{
  display: flex;
  flex-direction: column;
  width: 25%;
}
.add_cart{
  background-color: #2c3e50;
  color: antiquewhite;
  font-weight: bold;
  border-radius: 5px;
}
.card-text {
  display: flex;
  justify-content: space-between;
  padding-top: 10px;
  align-items: center;
}


.product-images {
  margin: 10px;
  display: flex;
  position: relative;
  flex-direction: column;
  background: white;
  border-radius: 15px;
  max-width: 300px;
  min-width: 300px;
  justify-content: center;
  place-content: center;
  place-items: center;
  overflow: hidden;
  padding: 10px;
  transition: 0.5s;
}

</style>