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
  <div>
    <label for="planName">일정의 이름을 정해주세요</label>
    <input id="planName" type="text" v-model="planName" placeholder="일정명 입력" />
    <VDatePicker v-model.range="range" />
    <div>시작일 : {{ startDate }}</div>
    <div>종료일 : {{ endDate }}</div>
    <div>차이 : {{ (new Date(endDate) - new Date(startDate)) / currDay }}</div>
    <button
      @click="$emit('dateSelect', startDate, endDate, planName)"
      class="btn bg-primary text-white"
    >
      여행일정선택완료
    </button>
  </div>
</template>

<style scoped></style>
