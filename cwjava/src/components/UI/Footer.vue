<template>
  <footer class="bg-gray-900 text-gray-200 py-10">
    <div class="container mx-auto">
      <div class="flex flex-wrap justify-between items-center">
        <!-- Author info -->
        <div class="w-full flex row">
          <h2 class="text-2xl font-semibold">Grisha Reznikov</h2>
          <p class="mt-4">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis auctor, quam in posuere blandit, mi magna interdum sapien, a rutrum turpis justo vel libero.</p>
          <p class="mt-4">© 2021 Grisha Reznikov. Made with ❤️ using Vue.js and Tailwind CSS. All rights reserved.</p>
        </div>
      </div>
    </div>
  </footer>
</template>

<script>
export default {
  name: "Footer"
}
</script>

<style scoped>
.footer {
  background-color: #1a202c;
}

.footer a:hover {
  color: #cbd5e0;
}
</style>
