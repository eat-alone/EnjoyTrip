<script setup>
import { ref } from "vue";

const props = defineProps({ contentId: String });

const price = ref("비용");
const holdTime = ref("시간");
const memo = ref("메모");
</script>

<template>
  <div>
    {{ props.contentId }}
    <label for="price">예상 비용</label>
    <input id="price" type="text" v-model="price" :placeholder="price" />
    <label for="time">예상 시간</label>
    <input id="time" type="text" v-model="holdTime" :placeholder="holdTime" />
    <label for="memo">메모</label>
    <input id="memo" type="text" v-model="memo" :placeholder="memo" />
    <button @click="$emit('setParam', price, holdTime, memo, props.contentId)">양식제출</button>
  </div>
</template>

<style scoped></style>
