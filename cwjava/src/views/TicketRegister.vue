<template>
  <div class="max-w-lg mx-auto">
    <h1 class="text-2xl font-bold mb-4">Submit Attendance Ticket</h1>
    <form @submit.prevent="submitTicket">
      <div class="mb-4">
        <label class="block font-bold mb-2" for="attendance-date">Attendance Date:</label>
        <input class="form-input block w-full" type="date" id="attendance-date" v-model="attendanceDate" required>
      </div>
      <div class="mb-4">
        <label class="block font-bold mb-2" for="price">Price:</label>
        <input class="form-input block w-full" type="number" id="price" v-model="price" required disabled>
      </div>
      <div class="mb-4">
        <label class="block font-bold mb-2" for="user-name">User Name:</label>
        <input class="form-input block w-full" type="text" id="user-name" v-model="user.name" required>
      </div>
      <div class="mb-4">
        <label class="block font-bold mb-2" for="user-email">User Email:</label>
        <input class="form-input block w-full" type="email" id="user-email" v-model="user.email" required>
      </div>
      <button class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded" type="submit">Submit</button>
    </form>
  </div>
</template>
<script>
import axios from 'axios'
import store from "@/store";
export default {
  data() {
    return {
      attendanceDate: '',
      price: 10,
      user: {
        name: '',
        email: ''
      }
    }
  },
  methods: {
    submitTicket() {
      let date = new Date(Date.now())
      const st = date.toISOString().substring(0,10);
      const ticket = {
        attandenceDate: this.attendanceDate,
        creationDate:st,
        price: this.price,
        user: {
          "id":store.state.auth.userInfo.id,
          "firstName":store.state.auth.userInfo.firstName,
          "lastName":store.state.auth.userInfo.lastName,
          "email":store.state.auth.userInfo.email,
        }
      }
      const token = store.state.auth.token.tokenValue;
      axios.post('http://server_cw:8080/api/ticket/new', ticket, {
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`
        },
      })
          .then(response => {
            alert('Ticket successfully submitted')
            this.attendanceDate = ''
            this.price = ''
            this.user = {
              name: '',
              email: ''
            }
          })
          .catch(error => {
            alert('Error submitting ticket')
            console.log(error)
          })
    }
  }
}
</script>
<style>
/* Basic form styles */
input[type="text"], input[type="email"], input[type="number"], input[type="date"] {
  border: 2px solid #ddd;
  padding: 10px;
  border-radius: 4px;
}
input[type="text"]:focus, input[type="email"]:focus, input[type="number"]:focus, input[type="date"]:focus {
  outline: none;
  border-color: #4a90e2;
}
button[type="submit"] {
  background-color: #4a90e2;
  color: #fff;
  border-radius: 4px;
  padding: 10px;
  font-size: 16px;
  font-weight: bold;
  border: none;
  cursor: pointer;
}
button[type="submit"]:hover {
  background-color: #0062cc;
}
label {
  font-weight: bold;
}
/* Tailwind styles */
.form-input {
  @apply block w-full border-gray-400 focus:border-blue-500 rounded-md;
  transition: border-color .15s ease-in-out,box-shadow .15s ease-in-out;
}
@media (prefers-reduced-motion: reduce) {
  .form-input {
    transition: none;
  }
}
</style>