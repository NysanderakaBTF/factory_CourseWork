html
<template>
  <div class="border-t border-gray-200 px-4 py-5 sm:px-6">
    <h3 class="text-lg leading-6 font-bold text-gray-900">Tickets</h3>
    <div class="mt-5 flex flex-col items-center">
      <table class="min-w-full divide-y divide-gray-200">
        <thead class="bg-gray-50">
        <tr>
          <th scope="col"
              class="px-6 py-3 text-left text-md font-bold text-gray-600 uppercase tracking-wider">
            ID
          </th>
          <th scope="col"
              class="px-6 py-3 text-left text-md font-bold text-gray-600 uppercase tracking-wider">
            Attendance Date
          </th>
          <th scope="col"
              class="px-6 py-3 text-left text-md font-bold text-gray-600 uppercase tracking-wider">
            Creation Date
          </th>
          <th scope="col"
              class="px-6 py-3 text-left text-md font-bold text-gray-600 uppercase tracking-wider">
            Price
          </th>
        </tr>
        </thead>
        <tbody class="bg-white divide-y divide-gray-200">
        <tr v-for="ticket in tickets" :key="ticket.id">
          <td class="px-6 py-4 whitespace-nowrap">
            <div class="text-md font-medium text-gray-900">{{ ticket.id }}</div>
          </td>
          <td class="px-6 py-4 whitespace-nowrap">
            <div class="text-md font-medium text-gray-900">{{ ticket.attandenceDate }}</div>
          </td>
          <td class="px-6 py-4 whitespace-nowrap">
            <div class="text-md font-medium text-gray-900">{{ ticket.creationDate }}</div>
          </td>
          <td class="px-6 py-4 whitespace-nowrap">
            <div class="text-md font-medium text-gray-900">{{ ticket.price }}</div>
          </td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>
<script>
export default {
  props:{
    tickets:null
  }
};
</script>