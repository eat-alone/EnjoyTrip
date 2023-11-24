<script setup>
import { ref, onMounted } from "vue";
import MapSearch from "../components/map/MapSearch.vue";
import TripPlan from "../components/map/TripPlan.vue";
import selectDate from "../components/map/selectDate.vue";
import { useTripStore } from "@/stores/trip";
import { useRoute, useRouter } from "vue-router";
const route = useRoute();
const { isNew } = route.params;

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
  startDate,
  endDate,
} = tripStore;

const currDay = 24 * 60 * 60 * 1000;

const startDate2 = ref("");
const endDate2 = ref("");
const idate = ref(0);
const page = ref(1);
const attInfo = ref();

onMounted(() => {
  if (isNew == "yes") {
    startDate2.value = "";
    endDate2.value = "";
  } else {
    console.log(planList);
    if (planList.length != 0) {
      dateSelect(startDate, endDate, planName);
    }
  }
});

const dateSelect = (start, end, name) => {
  startDate2.value = start;
  endDate2.value = end;
  idate.value = (new Date(end) - new Date(start)) / currDay + 1;
  pageChange(1);
  setPlanName(name);
  setDate(start, end);
  console.log("date selected", startDate2, endDate2, idate);
};

const getTargetDate = (date, num) => {
  date = new Date(date);
  date.setDate(date.getDate() + num - 1);
  return date;
};

const pageChange = (num) => {
  page.value = num;
  getTargetDate(startDate2.value, num);
  changeDate(getTargetDate(startDate2.value, num));
  setPage(num);
};

const selectAttinfo = (att) => {
  attInfo.value = att;
};
</script>

<template>
  <div>
    <template v-if="!startDate2">
      <selectDate @date-select="dateSelect" />
    </template>
    <template v-if="startDate2">
      <MapSearch />

      <div
        style="
          position: fixed;
          left: 400px;
          top: 88px;
          z-index: 2;
          width: 400px;
          height: 95vh;
          background-color: white;
          display: flex;
          flex-direction: column;
        "
      >
        <div class="">
          <template v-for="i in idate" :key="i">
            <button
              @click="pageChange(i)"
              class="border border-1 p-1 rounded-3 shadow-sm ring-1 ring-inset ring-gray m-2 hover:bg-gray-500"
            >
              {{ i }}일 계획
            </button>
          </template>
        </div>
        <div style="overflow: auto">
          <template v-for="i in idate" :key="i">
            <template v-if="page == i">
              <div>
                <TripPlan :date="startDate2" :page="i" />
              </div>
            </template>
          </template>
        </div>
      </div>
    </template>
  </div>
</template>

<style scoped></style>
