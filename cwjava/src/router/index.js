import {createRouter, createWebHistory} from 'vue-router'
import Store from "@/views/Store.vue";
import UserPage from "@/views/UserPage.vue";
import HomePage from "@/views/HomePage.vue";
import ProductDetailPage from "@/views/ProductDetailPage.vue";
import Register from "@/views/Register.vue";
import Login from "@/views/Login.vue";
import store from "@/store";
import CartPage from "@/views/CartPage.vue";
import UpdateUserInfo from "@/views/UpdateUserInfo.vue";
import ProductForm from "@/views/ProductForm.vue";
import CategoryEditor from "@/views/CategoryEditor.vue";
import Checkout from "@/views/Checkout.vue";
import TicketRegister from "@/views/TicketRegister.vue";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/home',
            name: 'home',
            component: HomePage,
            alias: '/'
        },
        {
            path: '/user',
            component: UserPage,
            name: "user"
        },
        {
            path: '/register',
            component: Register,
            name: 'register'
        },
        {
            path: '/login',
            component: Login,
            name: 'login'
        },
        {
            path: '/cart',
            component: CartPage
        },
        {
            path: '/upd',
            component: UpdateUserInfo
        },
        {
            path: '/store',
            component: Store,
            name: 'store',
        },
        {
            path: '/user',
            name: 'user',
            // lazy-loaded
            component: () => import('@/views/UserPage.vue')
        },
        {
            path: '/product/add',
            component: ProductForm
        },
        {
            path: '/product/:id',
            component: ProductDetailPage,
            name: 'product',
            props: true
        },
        {
            path:'/categories',
            component:CategoryEditor
        },
        {
            path:'/checkout',
            component:Checkout
        },
        {
            path:'/ticket',
            component:TicketRegister
        }
    ]
})
router.beforeEach((to, from, next) => {
    const publicPages = ['/login', '/register', '/home', '/store', "/"];
    let authRequired = !publicPages.includes(to.path);
    // const loggedIn = localStorage.getItem('user');
    const loggedIn = store.state.auth.status.loggedIn

    // const loggedIn = th;
    // trying to access a restricted page + not logged in
    // redirect to login page
    if (authRequired && !loggedIn) {
        if (to.path.match(/\/product\/*/)) {
            next()
        } else {
            next('/login');
        }
    } else {
        next();
    }
});
export default router
