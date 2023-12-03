<template>
  <div class="container">
    <div class="products__article">
      <div class="products-catalog__filter">
        <div class="filter__category">
          <h4 style="margin-top: 0px;">Категории</h4>
          <form @submit.prevent="perfornSearch">
            <select v-model="selectedCats" multiple>
              <option v-for="cat in getCat" :value=cat.valueOf() :id=cat.id>
                {{ cat.title }}
              </option>
            </select>
            <div>
              <label for="title">Название</label>
              <input v-model="searchTitle" class="bg-opacity-50 bg-teal-200 ml-1" type="text" id="title" name="title"/>
            </div>
            <div>
              <button class="bg-gray-400" type="submit">Найти</button>
            </div>
          </form>
        </div>

        <div class="sort" style="border-top: 1px solid grey;">
          <h4>Сортировать по:</h4>
          <ul>
            <li>
              <input type="radio" id="sortPrice" name="contact" value="1" v-model="sortOption" v-on:change="sort">
              <label for="saleChoice1">Цене</label>
            </li>
            <li>
              <input type="radio" id="sortRate" name="contact" value="2" v-model="sortOption" v-on:change="sort">
              <label for="saleChoice2">Отзывам</label>
            </li>
          </ul>
        </div>
        <div class="cart" style="border-top: 1px solid grey;">
          <button class="cart_b">
            <router-link class="link" to="/cart">Перейти к заказу</router-link>
          </button>
        </div>
        <router-link to="/categories">
          <button v-if="store.state.auth.user != null && store.state.auth.userInfo.role === 'ADMIN'"
                  class="bg-gray-800 py-1 px-4 text-white rounded mt-3"
          >
            Add category

          </button>
        </router-link>
        <br>
        <router-link to="/product/add"
                     v-if="store.state.auth.user != null && store.state.auth.userInfo.role === 'ADMIN'">
          <button
              class="bg-gray-800 py-1 px-4 text-white rounded mt-3"
          >Add product
          </button>
        </router-link>
      </div>
      <div class="priducrGridp">
        <productCard
            v-for="i in products"
            :product="i"
            :key="i.id"
            :name="i.name"
            :images="i.productImageSet"
            :price="i.price"
            :rating="i.rating"
            class="product__grid card"
        />
      </div>
    </div>
  </div>
</template>

<script>
import productCard from "@/components/ProductCard.vue";
import store from "@/store";
import axios from "axios";

export default {
  name: "Store",
  components: {productCard},
  data() {
    return {
      sortOption: 0,
      categories: [],
      products: [],
      selectedCats: [],
      searchTitle: ''
    }
  },
  computed: {
    store() {
      return store
    },

    getCat() {
      return store.getters["category/getCats"];
      // return this.categories;
    }
  },
  methods: {
    sort() {
      if (this.sortOption == 1) {
        this.products = this.products.sort((a, b) => a.price - b.price);
      }
      if (this.sortOption == 2) {
        this.products = this.products.sort((a, b) => a.rating - b.rating);
      }
    },
    perfornSearch() {
      let q = this.selectedCats;
      let n = this.searchTitle;
      let newProducts = axios.post("http://server_cw:8080/api/products/filter/both", q, {
            params: {
              title: n
            }
          }
      ).then(value => {
        this.products = value.data;
        console.log(value.data);
      })
    }
  },
  mounted() {
    this.categories = store.dispatch("category/getCategores");


    axios.get("http://server_cw:8080/api/products/all").then(
        value => {
          console.log(value.data)
          this.products = value.data;
          console.log(this.products)
        }
    )


  }

}
</script>

<style lang="scss" scoped>
.cart {
  display: flex;
  justify-content: center
}

.cart_b, .link {
  background-color: #143c4f;;
  color: #ffffff;
  font-weight: bold;
  border-radius: 5px;
  width: 88%;
  text-decoration: none;
}

.container {
  max-width: 1500px;
  margin-bottom: 5%;
}

.catsel {
  a {
    text-align: left;
  }

  &:hover {
    color: #603e31;
  }
}

li {
  justify-content: left;
  margin-left: 0;
}

.left,
.right {
  width: 90%;
  font-size: 15px;
  text-indent: 5px;
  height: 35px;
  margin-left: 0;
}

.price-label {
  margin: 0px 0px 2px 0px;
  font-size: 15px;
  font-weight: 200;
}

.from {
  width: 50%;
}

.price-range {
  display: flex;
  flex-wrap: wrap;
}

h4 {
  font-size: 20px;
  margin: 10px 0px 10px 0px;
  width: 100%;
}

.price-range {
  margin: 10px 0px;
}


.products-catalog__filter {
  margin-top: 8px;
  padding: 15px;
  border-radius: 15px;
  position: sticky;
  top: 10px;
}

.product__grid {
  width: 25% !important;
  max-width: none;
  min-width: 250px;
  margin: 1% 1% 0px 0px;
}

.products__article {
  display: flex;
  justify-content: space-between;
  align-items: start;
}

.products-catalog__filter {
  min-width: 280px;
  background: #ffffff;

  ul > li > a {
    text-decoration: none;
    color: #999999;
  }
}

.priducrGridp {
  display: flex;
  flex-wrap: wrap;
  margin-left: 10px;
  justify-content: space-between;
}


.products-catalog__title {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

ul {
  margin: 0px;
  list-style-type: none;
  padding: 0px;
}


ul > li {
  font-size: 18px;
  display: flex;
  align-items: center;
}

input[type=radio] {
  width: 15px;
  height: 15px;
}

input {
  border: #2c3e50 2px;
}


@media screen and (max-width: 1087px) {
  .product__grid {
    width: 49% !important;
    max-width: none;
    min-width: 200px;
  }
}

@media screen and (max-width: 800px) {
  .product__grid {
    width: 48% !important;
    max-width: 400px;
    min-width: 200px;
    margin: 1% auto;
  }
  .products-catalog__filter {
    position: inherit;
    margin-bottom: 10px;
  }
  .products__article {
    flex-wrap: wrap;
    flex-direction: column;
  }
}

@media screen and (max-width: 600px) {
  .priducrGridp {
    margin-left: 0px;
  }
  .products-catalog__filter {
    position: inherit;
    margin-bottom: 10px;
  }

  .products__article {
    flex-wrap: wrap;
  }

  .product__grid {
    width: 100%;
    max-width: none;
    min-width: 150px;
    margin: 1% auto;

  }

  .products-catalog__filter {
    width: 100%;
  }
}

@media screen and (max-width: 450px) {
  .product__grid {
    width: 98% !important;
    max-width: 400px;
    min-width: 200px;
    margin: 1% auto;
  }
}
</style>