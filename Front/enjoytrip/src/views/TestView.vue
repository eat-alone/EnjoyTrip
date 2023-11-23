<script setup>
import { ref } from "vue";
import MapSearch from "../components/map/MapSearch.vue";
import TripPlan from "../components/map/TripPlan.vue";
import selectDate from "../components/map/selectDate.vue";
import { useTripStore } from "@/stores/trip";

const tripStore = useTripStore();
const {
  planList,
  addPlan,
  delPlan,
  planDate,
  changeDate,
  setPage,
  pageNum,
  planName,
  setPlanName,
  setDate,
} = tripStore;

const currDay = 24 * 60 * 60 * 1000;

const startDate = ref("");
const endDate = ref("");
const idate = ref(0);
const page = ref(1);
const attInfo = ref();

const dateSelect = (start, end, name) => {
  startDate.value = start;
  endDate.value = end;
  idate.value = (new Date(end) - new Date(start)) / currDay + 1;
  pageChange(1);
  setPlanName(name);
  setDate(start, end);
  console.log("date selected", startDate, endDate, idate);
};

const getTargetDate = (date, num) => {
  date = new Date(date);
  date.setDate(date.getDate() + num - 1);
  return date;
};

const pageChange = (num) => {
  page.value = num;
  getTargetDate(startDate.value, num);
  changeDate(getTargetDate(startDate.value, num));
  setPage(num);
};

const selectAttinfo = (att) => {
  attInfo.value = att;
};
</script>

<template>
  <div class="grid grid-cols-4 row2">
    <button @click="moveNewPlan" class="col-span-1">새 일정 만들기</button>
    <button class="col-span-1">내 일정 보기</button>
    <button class="col-span-1">일정 탐색 하기</button>
    <button class="col-span-1">날짜 정하기</button>
    <template v-if="!startDate">
      <selectDate @date-select="dateSelect" />
    </template>
    <template v-if="startDate">
      <MapSearch />
    </template>
    <div>
      <template v-for="i in idate" :key="i">
        <button @click="pageChange(i)">{{ i }}일</button>
      </template>
      <template v-for="i in idate" :key="i">
        <template v-if="page == i">
          <div>
            {{ i }}일 일정
            <TripPlan :date="startDate" :page="i" />
          </div>
        </template>
      </template>
    </div>
  </div>
</template>

<style scoped></style>
