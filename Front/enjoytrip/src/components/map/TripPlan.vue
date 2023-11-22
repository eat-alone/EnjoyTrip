<script setup>
import { ref, watch } from "vue";
import { storeToRefs } from "pinia";
import { useTripStore } from "@/stores/trip";

const tripStore = useTripStore();
const { planList, addPlan, delPlan } = tripStore;

const deletePlan = (id) => {
  delPlan(id);
};
</script>

<template>
  <div class="col-span-1">
    <template v-for="attraction in planList" :key="attraction.contentId">
      <b-card class="mt-3" :header="attraction.title ? attraction.title : attraction.addr1">
        <button @click="deletePlan(attraction.contentId)">일정삭제</button>
        <pre class="m-0">{{ attraction.firstImage }}</pre>
      </b-card>
    </template>
  </div>
</template>

<style scoped></style>
