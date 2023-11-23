<script setup>
import { ref, computed } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();

const currDay = 24 * 60 * 60 * 1000;
const planName = ref("");
const range = ref({
  start: new Date(),
  end: new Date(),
});

const startDate = computed(() => {
  return (
    range.value.start.getFullYear() +
    "-" +
    range.value.start.getMonth() +
    "-" +
    range.value.start.getDate()
  );
});

const endDate = computed(() => {
  return (
    range.value.end.getFullYear() +
    "-" +
    range.value.end.getMonth() +
    "-" +
    range.value.end.getDate()
  );
});

const moveSearch = () => {
  router.push({ name: "mapsearch" });
};
</script>

<template>
  <div class="pt-3 ml-10">
    <label for="planName">일정의 이름을 정해주세요</label>
    <input id="planName"
      class="w-60 rounded-md mt-1 border-1 border-rounded-3 border-black py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
      type="text" v-model="planName" placeholder="일정명 입력" />
    <VDatePicker class="mt-4 mb-4" v-model.range="range" />
    <div>시작일 : {{ startDate }}</div>
    <div>종료일 : {{ endDate }}</div>
    <!-- <div>차이 : {{ (new Date(endDate) - new Date(startDate)) / currDay }}</div> -->
    <button @click="$emit('dateSelect', startDate, endDate, planName)" class="btn bg-primary text-white mt-3">
      여행일정선택완료
    </button>
  </div>
</template>

<style scoped></style>
