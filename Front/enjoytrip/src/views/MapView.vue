<script setup>
import { ref } from "vue";
import VKaKaoMap from "../components/common/VKaKaoMap.vue";
import SideNavBar from "../components/map/SideNavBar.vue";
import SideTabView from "../components/map/SideTabView.vue";

const isView1 = ref(false);
function changeParam(param) {
  console.log("asdf");
  isView1.value = param;
}
</script>

<template>
  <div class="zindex-dropdown grid grid-cols-12 gap-0 bg-transparent">
    <SideNavBar @is-view="changeParam" />
    <VKaKaoMap />
    <template v-show="isView1">
      <SideTabView />
    </template>
  </div>
</template>

<style scoped></style>
