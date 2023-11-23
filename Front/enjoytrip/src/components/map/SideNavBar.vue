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
  <span class="col-span-1">
    <div class="d-flex flex-column p-1 text-white bg-white">
      <hr />
      <ul class="nav nav-pills flex-column mb-auto">
        <li class="nav-item">
          <a href="#" class="nav-link active" aria-current="page">
            <svg class="bi me-2" width="16" height="16"><use xlink:href="#home" /></svg>
            Home
          </a>
        </li>
        <li>
          <button
            @click="viewClick()"
            class="nav-link text-black btn btn-primary"
            type="button"
            data-bs-toggle="offcanvas"
            data-bs-target="#offcanvasScrolling"
            aria-controls="offcanvasScrolling"
          >
            <svg class="bi me-2" width="16" height="16"><use xlink:href="#speedometer2" /></svg>
            Dashboard
          </button>
        </li>
        <li>
          <a href="#" class="nav-link text-black">
            <svg class="bi me-2" width="16" height="16"><use xlink:href="#table" /></svg>
            Orders
          </a>
        </li>
        <li>
          <a href="#" class="nav-link text-black">
            <svg class="bi me-2" width="16" height="16"><use xlink:href="#grid" /></svg>
            Products
          </a>
        </li>
        <li>
          <a href="#" class="nav-link text-black">
            <svg class="bi me-2" width="16" height="16"><use xlink:href="#people-circle" /></svg>
            Customers
          </a>
        </li>
      </ul>
      <hr />
    </div>
  </span>
</template>

<style scoped></style>
