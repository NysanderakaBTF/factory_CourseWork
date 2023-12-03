html
<template>
  <div class="container mx-auto px-4">
    <form @submit.prevent="createProduct">
      <div class="mb-4">
      <label class="block text-gray-700 font-bold mb-2" for="name">
        Name
      </label>
      <input
          v-model="name"
          class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
          id="name"
          type="text"
          placeholder="Product name"
      />
    </div>
      <div class="mb-4">
        <label class="block text-gray-700 font-bold mb-2" for="description">
          Description
        </label>
        <textarea
            v-model="description"
            class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            id="description"
            rows="4"
            placeholder="Product description"
        ></textarea>
      </div>
      <div class="mb-4">
        <label class="block text-gray-700 font-bold mb-2" for="price">
          Price
        </label>
        <input
            v-model="price"
            class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            id="price"
            type="number"
            placeholder="Product price"
        />
      </div>
      <div class="mb-4">
        <label class="block text-gray-700 font-bold mb-2" for="categories">
          Categories
        </label>
        <select v-model="selectedCategories" multiple class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" id="categories">
          <option v-for="category in categories" :value="category.id">{{ category.title }}</option>
        </select>
      </div>
      <div class="mb-4">
        <label class="block text-gray-700 font-bold mb-2" for="images">
          Images
        </label>
        <input
            ref="imagesInput"
            class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            id="images"
            type="file"
            multiple
            accept="image/*"
            @change="handleImagesInputChange"
        />
      </div>
      <div class="flex items-center justify-between">
        <button class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline" type="submit">
          Create product
        </button>
      </div>
    </form>
  </div>
</template>
<script>
// Import axios
import axios from "axios";
import store from "@/store";
export default {
  data() {
    return {
      name: "",
      description: "",
      price: null,
      selectedCategories: [],
      categories: [],
      images: [],
    };
  },
  mounted() {
    this.fetchCategories();
  },
  methods: {
    async fetchCategories() {
      this.categories = store.getters["category/getCats"];
    },
    handleImagesInputChange() {
      this.images = this.$refs.imagesInput.files;
    },
    async createProduct() {
      const formData = new FormData();
      for (const element of this.images) {
        formData.append("image", element);
      }
      let chosen_cats = [];
      for(let i of this.selectedCategories){
        chosen_cats.push({
          "id":i.id,
          "title":i.title,
          "slug":i.slug
        });
      }
      const token = store.state.auth.token.tokenValue;
      try {
        const res = await axios.post("http://server_cw:8080/api/products/new", {
          "name":this.name,
          "description": this.description,
          "price":this.price,
          "categories":chosen_cats
        }, {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`
          },
        }).then(async value => {
          console.log(value)
          await axios.post(`http://server_cw:8080/api/images/${value.data.id}`,formData,{
            headers: {
              "Content-Type": "multipart/form-data",
              Authorization: `Bearer ${token}`
            },
          })
        });
        console.log(res.data)
        this.$router.push('/store');
      } catch (error) {
        console.error(error);
      }
    },
  },
};
</script>