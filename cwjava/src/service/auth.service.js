import axios from 'axios';
import store from "@/store";

const API_URL = 'http://server_cw:8080/api/';

class AuthService {
    login(user) {
        console.log(user)
        return axios
            .post(API_URL + 'user/authenticate', {
                username: user.username,
                password: user.password
            })
            .then(response => {
                if (response.data.accessToken) {
                    store.commit('auth/loginSuccess', JSON.stringify(response.data));
                }
                console.log(response)
                return {token: response.data, username:user.username};
            });
    }

    logout() {
        store.state.auth.status.loggedIn = false;
        store.commit('user', null);
    }

    register(user) {
        return axios.post(API_URL + 'user/register', {
            username: user.username,
            email: user.email,
            password: user.password,
            firstName: user.firstName,
            address: user.address,
            role:'USER',
            status:'ACTIVE',
            lastName:user.lastName
        }).then(value => console.log(value.data));
    }
}

export default new AuthService();