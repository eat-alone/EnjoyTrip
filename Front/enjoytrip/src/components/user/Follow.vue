<script setup>
import { ref } from "vue";
import { storeToRefs } from "pinia";
import { useFollowStore } from "@/stores/follow";
const followStore = useFollowStore();
const { follow } = storeToRefs(followStore);



</script>

<template>
    <h2 class="text-center mt-4">팔로우 목록</h2>
    <ul role="list" class="divide-y w-2/5 mx-auto">
        <li v-for="person in follow" :key="person.Id" class="flex justify-between gap-x-6 py-5">
            <div class="flex min-w-0 gap-x-4">
                <img class="h-12 w-12 flex-none rounded-full bg-gray-50"
                    src='https://images.unsplash.com/photo-1506794778202-cad84cf45f1d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80'
                    alt="" />
                <div class="min-w-0 flex-auto">
                    <p class="text-sm font-semibold leading-6 text-gray-900">{{ person.userId }}</p>
                    <p class="mt-1 truncate text-xs leading-5 text-gray-500">{{ person.userNickname }}</p>
                </div>
            </div>
            <div class="hidden shrink-0 sm:flex sm:flex-col sm:items-end">
                <p class="text-sm leading-6 text-gray-900">{{ person.role }}</p>
                <p v-if="person.lastSeen" class="mt-1 text-xs leading-5 text-gray-500">
                    Last seen <time :datetime="person.lastSeenDateTime">{{ person.lastSeen }}</time>
                </p>
                <div v-else class="mt-1 flex items-center gap-x-1.5">
                    <div class="flex-none rounded-full bg-emerald-500/20 p-1">
                        <div class="h-1.5 w-1.5 rounded-full bg-emerald-500"></div>
                    </div>
                    <p class="text-xs mr-5 leading-5 text-gray-500">Online</p>

                    <button type="button"
                        class="inline-flex justify-center rounded-md bg-white px-3 py-2 text-sm font-semibold text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50 sm:mt-0 sm:w-auto"
                        @click="open = false" ref="cancelButtonRef">팔로우 취소</button>
                </div>
            </div>
        </li>
    </ul>
</template>
  
  