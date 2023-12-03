<template>
  <div class="container mx-auto px-4">
    <h2 class="text-2xl font-bold mb-4">Checkout</h2>
    <!-- Payment info form -->
    <form class="w-full max-w-md">
      <div class="mb-4">
        <label class="block text-gray-700 font-bold mb-2" for="cardName">
          Cardholder Name
        </label>
        <input
            class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            id="cardName"
            type="text"
            placeholder="John Doe"
            v-model="cardName"
        >
      </div>
      <div class="mb-4">
        <label class="block text-gray-700 font-bold mb-2" for="cardNumber">
          Card Number
        </label>
        <input
            class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            id="cardNumber"
            type="text"
            placeholder="1111 2222 3333 4444"
            v-model="cardNumber"
        >
      </div>
      <div class="mb-4">
        <div class="block text-gray-700 font-bold mb-2">Expiration Date</div>
        <div class="flex">
          <select
              class="shadow appearance-none border rounded w-1/2 py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
              v-model="expMonth"
          >
            <option v-for="(month, index) in months" :key="index" :value="month.value">
              {{ month.label }}
            </option>
          </select>
          <select
              class="shadow appearance-none border rounded w-1/2 py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline ml-2"
              v-model="expYear"
          >
            <option v-for="(year, index) in years" :key="index" :value="year">
              {{ year }}
            </option>
          </select>
        </div>
      </div>
      <div class="mb-4">
        <label class="block text-gray-700 font-bold mb-2" for="cvc">
          CVC
        </label>
        <input
            class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            id="cvc"
            type="text"
            placeholder="123"
            v-model="cvc"
        >
      </div>
      <div class="mb-4">
        <label class="block text-gray-700 font-bold mb-2" for="email">
          Email
        </label>
        <input
            class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
            id="email"
            type="email"
            placeholder="john.doe@example.com"
            v-model="email"
        >
      </div>
      <button
          class="bg-blue-500 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline hover:bg-blue-700"
          type="button"
          @click="submitForm"
      >
        Submit Payment
      </button>
    </form>
    <!-- Total price label -->
    <div class="mt-8">
      <span class="text-gray-700 font-bold">Total Price: </span>
      <span class="text-xl font-bold text-blue-500">{{ totalPrice }}</span>
      <span class="text-gray-700 font-bold"> USD</span>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      cardName: '',
      cardNumber: '',
      expMonth: '',
      expYear: '',
      cvc: '',
      email: '',
      months: [
        { label: '01 - January', value: '01' },
        { label: '02 - February', value: '02' },
        { label: '03 - March', value: '03' },
        { label: '04 - April', value: '04' },
        { label: '05 - May', value: '05' },
        { label: '06 - June', value: '06' },
        { label: '07 - July', value: '07' },
        { label: '08 - August', value: '08' },
        { label: '09 - September', value: '09' },
        { label: '10 - October', value: '10' },
        { label: '11 - November', value: '11' },
        { label: '12 - December', value: '12' }
      ],
      years: [],
      totalPrice: 10.99
    }
  },
  mounted() {
    // Fill years array with current year and the next 10 years
    const currentYear = new Date().getFullYear();
    for (let i = currentYear; i <= currentYear + 10; i++) {
      this.years.push(i);
    }
  },
  methods: {
    submitForm() {
      // Handle form submission here
      console.log('Form submitted!');
    }
  },
  computed: {
    formattedTotalPrice() {
      // Format the total price with 2 decimal places
      return parseFloat(this.totalPrice).toFixed(2);
    }
  }
}
</script>