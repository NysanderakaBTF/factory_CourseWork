import axios from "axios";

export const cart = {
    namespaced: true,
    namespace:'cart',
    state: {
        products:[],
        total:0
    },
    actions: {
    },
    mutations: {
        addProduct(state, product){
            console.log(state.products)
            product.quantity = 1;
            product.total = product.quantity*product.product.price;
            state.products.push(product);
            let q = 0;
            for (const item of state.products) {
                q += item.total;
            }
            state.total = q;
            console.log(state.products);
        },
        removeProductById(state, prodId) {
            console.log(state.products)
            state.products = state.products.filter(value =>
                value["product"]["id"] !== prodId
            )
            console.log(state.products)
            let q = 0;
            for (const item of state.products) {
                q += item.total;
            }
            state.total = q;
        },
        updateQuantityById(state, {payload, increase}) {
            let isAdded = false;
            for (const item of state.products) {
                console.log(item ,payload)
                if (item.product.id === payload.product.id) {
                    if (increase) {
                        item.quantity += 1;
                        item.total = item.quantity*item.product.price;
                    } else {
                        if (item.quantity === 1) {
                            state.products = state.products.filter(item => item.product.id !== payload.product.id);
                        } else {
                            item.quantity -= 1
                            item.total = item.quantity*item.product.price;
                        }
                    }
                    isAdded = true;
                    break;
                }
            }
            if (!isAdded && increase) {
                payload.quantity = 1;
                payload.total = payload.quantity*payload.product.price;
                state.products.push(payload);
            }
            let q = 0;
            for (const item of state.products) {
                q += item.total;
            }
            state.total = q;
        }

    },
    getters:{
        getAllProducts(state){
            return state.products;
        },
        getTotal(state){
            return state.total;
        },
        getProductById(state, id){
            for (const product of state.products) {
                if(product.product.id === id){
                    return product;
                }
            }
        }
    }
};