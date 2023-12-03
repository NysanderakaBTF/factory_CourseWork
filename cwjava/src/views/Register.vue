<template>
  <div class="col-md-12">
    <div class="card card-container">
      <Form name="form" @submit="handleRegister" :validation-schema="schema">
        <div v-if="!successful">
          <div class="form-group">
            <label for="username">Username</label>
            <Field
                v-model="user.username"
                type="text"
                class="form-control"
                name="username"
            />
            <ErrorMessage name="username"/>
          </div>


          <div class="form-group">
            <label for="email">Email</label>
            <Field
                v-model="user.email"
                type="email"
                class="form-control"
                name="email"
            />
            <ErrorMessage name="email"/>
          </div>


          <div class="form-group">
            <label for="password">Password</label>
            <Field
                v-model="user.password"
                type="password"
                class="form-control"
                name="password"
            />
            <ErrorMessage name="password"/>
          </div>

          <div class="form-group">
            <label for="firstName">First name</label>
            <Field
                v-model="user.firstName"
                type="text"
                class="form-control"
                name="firstName"
            />
            <ErrorMessage name="firstName"/>
          </div>

          <div class="form-group">
            <label for="address">Address</label>
            <Field
                v-model="user.address"
                type="text"
                class="form-control"
                name="address"
            />
            <ErrorMessage name="address"/>
          </div>

          <div class="form-group">
            <label for="lastName">lastName</label>
            <Field
                v-model="user.lastName"
                type="text"
                class="form-control"
                name="lastName"
            />
            <ErrorMessage name="lastName"/>
          </div>


          <div class="form-group">
            <button class="btn btn-primary btn-block">Sign Up</button>
          </div>
        </div>
      </Form>

      <div
          v-if="message"
          class="alert"
          :class="successful ? 'alert-success' : 'alert-danger'"
      >{{ message }}
      </div>
    </div>
  </div>
</template>


<script setup>
const schema = yup.object({
  email: yup.string().required().email(),
  password: yup.string().required().min(8),
  username:yup.string().required()
});
</script>


<script>
import User from '../models/user';
import {Form, Field, ErrorMessage} from 'vee-validate';
import * as yup from 'yup';
import store from "@/store";

export default {
  name: 'Register',
  data() {
    return {
      user: new User('', '', ''),
      submitted: false,
      successful: false,
      message: ''
    };
  },
  computed: {
    loggedIn() {
      return this.$store.state.auth.status.loggedIn;
    }
  },
  mounted() {
    if (this.loggedIn) {
      this.$router.push('/profile');
    }
  },
  methods: {
    handleRegister(values) {
      console.log(values)
      this.message = '';
      this.submitted = true;
      store.dispatch('auth/register', this.user).then(
          data => {
            console.log(data)
            this.message = data.message;
            this.successful = true;
          },
          error => {
            this.message =
                (error.response && error.response.data) ||
                error.message ||
                error.toString();
            this.successful = false;
          }
      );
    }
  }
};
</script>

<style scoped>
label {
  display: block;
  margin-top: 10px;
}

.card-container.card {
  max-width: 350px !important;
  padding: 40px 40px;
}

.card {
  background-color: #f7f7f7;
  padding: 20px 25px 30px;
  margin: 0 auto 25px;
  margin-top: 50px;
  -moz-border-radius: 2px;
  -webkit-border-radius: 2px;
  border-radius: 2px;
  -moz-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
  -webkit-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
  box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
}

.profile-img-card {
  width: 96px;
  height: 96px;
  margin: 0 auto 10px;
  display: block;
  -moz-border-radius: 50%;
  -webkit-border-radius: 50%;
  border-radius: 50%;
}
</style>