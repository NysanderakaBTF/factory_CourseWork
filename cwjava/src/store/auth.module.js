import AuthService from '@/service/auth.service';
import UserService from "@/service/user.service";

const user = JSON.parse(localStorage.getItem('user'));
const initialState = user
    ? { status: { loggedIn: true }, user }
    : { status: { loggedIn: false }, user: null };

export const auth = {
    namespaced: true,
    namespace:'auth',
    state: {
        ...initialState,
        userInfo:null,
        token:''
    },
    actions: {
        login({ commit }, user) {
            return AuthService.login(user).then(
                user => {
                    commit('loginSuccess', user);
                    commit('setToken', user.token)
                    commit('fetchUserInf');
                    return Promise.resolve(user);
                },
                error => {
                    commit('loginFailure');
                    return Promise.reject(error);
                }
            );
        },
        logout({ commit }) {

            AuthService.logout();
            commit('logout');
        },
        register({ commit }, user) {
            return AuthService.register(user).then(
                response => {
                    commit('registerSuccess');
                    return Promise.resolve(response.data);
                },
                error => {
                    commit('registerFailure');
                    return Promise.reject(error);
                }
            );
        },

    },
    mutations: {
        async fetchUserInf(state) {
            state.userInfo = await UserService.getPersonalInfo(state.user.username);
        },
        setToken(state, token){
            state.token = token;
        },

        setUserInfo(state, user){
            state.user = user;
        },
        loginSuccess(state, user) {
            state.status.loggedIn = true;
            state.user = user;
        },
        loginFailure(state) {
            state.status.loggedIn = false;
            state.user = null;
        },
        logout(state) {
            state.status.loggedIn = false;
            state.user = null;
        },
        registerSuccess(state) {
            state.status.loggedIn = false;
        },
        registerFailure(state) {
            state.status.loggedIn = false;
        }
    },
    getters:{
        getAuth(state){
            return state.status.loggedIn;
        },
        getUserInf(state) {
            return state.userInfo;
        },
        getUsername(state){
            return state.user.username;
        }
    }
};