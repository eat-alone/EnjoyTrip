<script setup>
import VKaKaoMap from "../components/common/VKaKaoMap.vue";
import SideNavBar from "../components/map/SideNavBar.vue";
import SideTabView from "../components/map/SideTabView.vue";
</script>

<template>
  <div class="zindex-dropdown grid grid-cols-12 gap-0 bg-transparent">
    <SideTabView />
    <SideNavBar />
    <VKaKaoMap />
  </div>
</template>

<style scoped></style>
