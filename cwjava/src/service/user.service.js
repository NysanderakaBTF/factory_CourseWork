import axios from 'axios';
import authHeader from './auth-header';
import store from "@/store";

const API_URL = 'http://server_cw:8080/api/user/';

class UserService {
    async getPersonalInfo(id) {
        try {
            const a = await  axios.get(API_URL + id, {headers: authHeader()}).then(
                value =>{
                    store.commit('auth/setUserInfo', value.data);
                    console.log(value.data);
                    return value.data;
                }
            );
            return a;
        }catch (e){
            alert(e);
        }

    }

    updateInfo(id, userinfo) {
        return axios.put(API_URL + `${id}`, userinfo, {headers: authHeader()});
    }

    deleteAccount(id){
        return axios.delete(API_URL + `${id}`)
    }

}

export default new UserService();