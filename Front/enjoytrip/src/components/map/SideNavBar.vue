<script setup>
import { ref } from "vue";

const emit = defineEmits(["isView"]);

const isView = ref(false);

function viewClick() {
  isView.value = !isView.value;
  emit("isView", isView.value);
}
</script>

<template>
  <div class="" style="width: 400px; height: 93vh; display: flex; flex-direction: column">
    <div style="">
      <ul class="nav justify-content-center mx-auto pt-2">
        <li class="nav-item border border-1 rounded-5 bg-gray-200 m-1 sm:ring-1 sm:ring-gray-900/10">
          <router-link :to="{ name: 'test' }" class="nav-link active text-black" aria-current="page" href="#">새일정생성
          </router-link>
        </li>
        <li class="nav-item border-1 rounded-5 bg-gray-200 m-1 bg-gray-200 sm:ring-1 sm:ring-gray-900/10">
          <a class="nav-link text-black" href="#">내 일정 목록</a>
        </li>
        <li class="nav-item border-1 rounded-5 bg-gray-200 m-1 sm:ring-1 sm:ring-gray-900/10">
          <a class="nav-link text-black" href="#">일정 탐색</a>
        </li>
      </ul>
    </div>
    <div style="overflow: auto">
      <router-view></router-view>
    </div>
  </div>
</template>

<style scoped></style>
