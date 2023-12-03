import { createStore } from 'vuex'
import {auth} from "@/store/auth.module";
import {category} from "@/store/category.module";
import {cart} from "@/store/cart.module";

export default createStore({
    state: {
    },
    getters: {

    },
    mutations: {
    },
    actions: {
    },
    modules: {
        auth,
        category,
        cart
    }
})