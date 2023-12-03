<template>
  <div class="container mx-auto p-4">
    <h1 class="text-3xl font-bold mb-4">Categories</h1>
    <table class="table-auto w-full">
      <thead>
      <tr>
        <th class="px-4 py-2">#</th>
        <th class="px-4 py-2">Title</th>
        <th class="px-4 py-2">Slug</th>
        <th class="px-4 py-2">Actions</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="category in categories" :key="category.id">
        <td class="border px-4 py-2">{{ category.id }}</td>
        <td class="border px-4 py-2">{{ category.title }}</td>
        <td class="border px-4 py-2">{{ category.slug }}</td>
        <td class="border px-4 py-2">
          <button class="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded"
                  @click="deleteCategory(category.id)">Delete</button>
        </td>
      </tr>
      </tbody>
    </table>
    <h2 class="text-xl font-bold mt-8">Add a Category</h2>
    <form @submit.prevent="crearteCategory" class="mt-4">
      <div class="flex items-center mb-4">
        <label class="w-1/3 font-bold" for="title">Title:</label>
        <input class="border rounded p-2 w-2/3" v-model="newCategory.title" type="text" id="title">
      </div>
      <div class="flex items-center mb-4">
        <label class="w-1/3 font-bold" for="slug">Slug:</label>
        <input class="border rounded p-2 w-2/3" v-model="newCategory.slug" type="text" id="slug">
      </div>
      <button class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
              type="submit">Add Category</button>
    </form>
  </div>
</template>

// And then in your component's script:
<script>
import { mapActions, mapGetters } from 'vuex'
import axios from "axios";
import store from "@/store";

export default {
  data() {
    return {
      categories:[],
      newCategory: {
        title: '',
        slug: null,
      },
    }
  },
  methods: {
    deleteCategory(id){
      const token = store.state.auth.token.tokenValue;
      axios.delete(`http://server_cw:8080/api/category/${id}`,{
        headers: {
          Authorization:`Bearer ${token}`
        }
      }).then(value => this.fetchCats());
    },
    crearteCategory(){
      const token = store.state.auth.token.tokenValue;
      axios.post('http://server_cw:8080/api/category/new',{
        "title":this.newCategory.title,
        "slug":this.newCategory.slug
      },
          {
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${token}`
            },
          }).then(value =>  this.fetchCats());
    },
    fetchCats(){
      axios.get('http://server_cw:8080/api/category/all').then(value => this.categories = value.data);
    }
  },
  created() {
   this.fetchCats();
  },
}
</script>