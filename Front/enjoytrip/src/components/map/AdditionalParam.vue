<script setup>
import { ref } from "vue";

const props = defineProps({ contentId: String });

const price = ref("비용");
const holdTime = ref("시간");
const memo = ref("메모");
</script>

<template>
  <div>
    <!-- {{ props.contentId }} -->
    <button @click="$emit('setParam', price, holdTime, memo, props.contentId)"
      class="border-1 p-1 rounded-4 bg-gray-300 mb-2 hover:bg-gray-400 mb-2">양식제출</button>
    <div class="flex">
      <label for="price" class="mt-1">예상 비용</label>
      <input id="price" type="text" v-model="price" :placeholder="price"
        class=" w-[70%] rounded-md border-1 ml-4 py-1 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6" />
    </div>
    <div class="flex mt-2">
      <label for="time" class="mt-1">예상 시간</label>
      <input id="time" type="text" v-model="holdTime" :placeholder="holdTime"
        class=" w-[70%] rounded-md border-1 ml-4 py-1 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6" />
    </div>
    <div class="flex mt-2 mb-4">
      <label for="memo" class="mt-1">메모</label>
      <input id="memo" type="text" v-model="memo" :placeholder="memo"
        class=" w-[70%] justify-end rounded-md border-1 ml-10 py-1 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6" />
    </div>
  </div>
</template>

<style scoped></style>
