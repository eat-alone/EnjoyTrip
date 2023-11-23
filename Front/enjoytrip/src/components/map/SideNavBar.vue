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
      <ul class="nav justify-content-center">
        <li class="nav-item">
          <router-link :to="{ name: 'test' }" class="nav-link active" aria-current="page" href="#"
            >새일정생성
          </router-link>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="#">내 일정 목록</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="#">일정 탐색</a>
        </li>
      </ul>
    </div>
    <div style="overflow: auto">
      <router-view></router-view>
    </div>
  </div>
</template>

<style scoped></style>
