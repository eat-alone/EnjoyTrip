<script setup>
import { ref, watch } from "vue";

const props = defineProps({
  attraction: {
    addr1: String,
    addr2: String,
    contentId: String,
    contentTypeId: String,
    firstImage: String,
    firstImage2: String,
    gugunCode: String,
    latitude: String,
    longitude: String,
    mlevel: String,
    readCount: String,
    sidoCode: String,
    tel: String,
    title: String,
    zipcode: String,
  },
});

const attList = ref([]);

watch(
  () => props.attraction,
  () => {
    console.log(props.attraction);
  },
  { deep: true }
);
</script>

<template>
  <div class="col-span-1">
    <template v-for="attraction in attList" :key="attraction.contentId">
      <b-card class="mt-3" :header="attraction.title ? attraction.title : attraction.addr1">
        <button>일정추가</button>
        <pre class="m-0">{{ attraction.firstImage }}</pre>
      </b-card>
    </template>
  </div>
</template>

<style scoped></style>
