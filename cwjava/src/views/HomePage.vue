<template>
  <div class="bg-gray-100 py-10">
    <div class="max-w-2xl mx-auto px-4 sm:px-6 lg:px-8">
      <div class="text-center mb-12">
        <h1 class="text-5xl font-bold mb-6">Welcome to Chocolate Factory!</h1>
        <p class="text-lg leading-relaxed mb-8">
          We are passionate about creating delicious chocolates that bring joy to people all
          over the world. Our chocolates are made with the finest cocoa beans from around the
          world, combined with the freshest ingredients to create the perfect indulgence.
        </p>
      </div>
      <div class="grid grid-cols-1 md:grid-cols-2 gap-8">
        <div>
          <img src="../../assets/chocolate2.jpg" alt="Chocolate 2" class="rounded-lg shadow-lg w-full h-auto mb-4">
          <p class="text-center font-semibold">The freshest ingredients</p>
        </div>
        <div>
          <img src="../../assets/chocolate3.jpg" alt="Chocolate 3" class="rounded-lg shadow-lg w-full h-auto mb-4">
          <p class="text-center font-semibold">The perfect indulgence</p>
        </div>
      </div>
      <div class="text-center mt-12">
        <p class="text-lg mb-8">
          Our shop offers a wide variety of chocolates, from milk chocolate to dark chocolate
          and everything in between. We also offer seasonal flavors and special collections for
          those who want to indulge in something truly unique.
        </p>
        <router-link
            to="/store"
            class="inline-block px-6 py-3 text-lg font-semibold text-slate-50 bg-slate-400 hover:bg-slate-500 transition-all duration-300 ease-in-out rounded-lg"
        >
          Explore Our Shop
        </router-link>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: 'HomePage',
};
</script>
<style>
/* No custom styles needed - this is all taken care of by Tailwind */
</style>