<script setup>
import { ref } from "vue";
import { CalendarDaysIcon, HandRaisedIcon } from '@heroicons/vue/24/outline'
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";
import { useSearchStore } from "@/stores/search";
import { useFollowStore } from "@/stores/follow";

const memberStore = useMemberStore();
const searchStore = useSearchStore();
const followStore = useFollowStore();

const { userList } = storeToRefs(searchStore);
const { follow } = storeToRefs(followStore);
const { following } = followStore;
const { userInfo } = memberStore;
const { searchUser } = searchStore;

const searchId = ref("");



function search() {
    getUser()
}

const getUser = async () => {
    console.log(searchId.value)
    await searchUser(searchId.value);
    console.log(userList.value)
}

function followings(userId) {
    console.log(userInfo.Id)
    console.log(userId)
    following(userInfo.Id, userId);
}
</script>


<template>
    <div class="relative isolate overflow-hidden bg-gray-900 py-16 sm:py-24 lg:py-32">
        <div class="mx-auto max-w-7xl px-6 lg:px-8">
            <div class="mx-auto grid max-w-2xl grid-cols-1 gap-x-8 gap-y-16 lg:max-w-none lg:grid-cols-2">
                <div class="max-w-xl lg:max-w-lg">
                    <h2 class="text-3xl font-bold tracking-tight text-white sm:text-4xl">전국의 친구를 검색해보세요!</h2>
                    <p class="mt-4 text-lg leading-8 text-gray-300">검색하고자 하는 친구의 ID 혹은 닉네임을 입력해주세요.</p>
                    <div class="mt-6 flex max-w-md gap-x-4">
                        <label for="email-address" class="sr-only">Email address</label>
                        <input id="email-address" name="email" type="text" autocomplete="email" required=""
                            v-model="searchId"
                            class="min-w-0 flex-auto rounded-md border-0 bg-white/5 px-3.5 py-2 text-white shadow-sm ring-1 ring-inset ring-white/10 focus:ring-2 focus:ring-inset focus:ring-indigo-500 sm:text-sm sm:leading-6"
                            placeholder="Enter your friends" />
                        <button @click="search"
                            class="flex-none rounded-md bg-indigo-500 px-3.5 py-2.5 text-sm font-semibold text-white shadow-sm hover:bg-indigo-400 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-500">검색</button>
                    </div>
                </div>
                <dl class="grid grid-cols-1 gap-x-8 gap-y-10 sm:grid-cols-2 lg:pt-2">
                    <div class="flex flex-col items-start">
                        <div class="rounded-md bg-white/5 p-2 ring-1 ring-white/10">
                            <CalendarDaysIcon class="h-6 w-6 text-white" aria-hidden="true" />
                        </div>
                        <dt class="mt-4 font-semibold text-white">검색 시 주의사항..</dt>
                        <dd class="mt-2 leading-7 text-gray-400">아직 서로 팔로우 되어있는지에 대한 View는 구현하지 못했음.</dd>
                    </div>
                    <div class="flex flex-col items-start">
                        <div class="rounded-md bg-white/5 p-2 ring-1 ring-white/10">
                            <HandRaisedIcon class="h-6 w-6 text-white" aria-hidden="true" />
                        </div>
                        <dt class="mt-4 font-semibold text-white">No spam</dt>
                        <dd class="mt-2 leading-7 text-gray-400">Officia excepteur ullamco ut sint duis proident non
                            adipisicing. Voluptate incididunt anim.</dd>
                    </div>
                </dl>
            </div>
        </div>
        <div class="absolute left-1/2 top-0 -z-10 -translate-x-1/2 blur-3xl xl:-top-6" aria-hidden="true">
            <div class="aspect-[1155/678] w-[72.1875rem] bg-gradient-to-tr from-[#ff80b5] to-[#9089fc] opacity-30"
                style="clip-path: polygon(74.1% 44.1%, 100% 61.6%, 97.5% 26.9%, 85.5% 0.1%, 80.7% 2%, 72.5% 32.5%, 60.2% 62.4%, 52.4% 68.1%, 47.5% 58.3%, 45.2% 34.5%, 27.5% 76.7%, 0.1% 64.9%, 17.9% 100%, 27.6% 76.8%, 76.1% 97.7%, 74.1% 44.1%)" />
        </div>
    </div>

    <h2 class="text-center mt-4">검색 목록</h2>
    <h1 v-if="userList == null" class="text-center mt-5">
        텅</h1>

    <ul role="list" class="divide-y w-2/5 mx-auto">
        <li v-for="person in userList" :key="person.Id" class="flex justify-between gap-x-6 py-5">
            <div v-if="userInfo.Id != person.Id">
                <div class="flex min-w-0 gap-x-4">
                    <img class="h-12 w-12 flex-none rounded-full bg-gray-50"
                        src='https://images.unsplash.com/photo-1506794778202-cad84cf45f1d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80'
                        alt="" />
                    <div class="min-w-0 flex-auto">
                        <router-link :to="{ name: 'user-mypage', params: { userid: person.userId } }"
                            class="text-sm font-semibold leading-6 text-gray-900">{{
                                person.userId
                            }}</router-link>
                        <!-- { name: 'user-modify', params: { userid: userInfo.value.userId } -->
                        <p class="mt-1 truncate text-xs leading-5 text-gray-500">{{ person.userNickname }}</p>
                    </div>
                </div>
                <div class="hidden shrink-0 sm:flex sm:flex-col sm:items-end">

                    <div class="mt-1 flex items-center gap-x-1.5">
                        <div class="flex-none rounded-full bg-emerald-500/20 p-1">
                            <div class="h-1.5 w-1.5 rounded-full bg-emerald-500"></div>
                        </div>
                        <p class="text-xs mr-5 leading-5 text-gray-500">Online</p>

                        <button type="button"
                            class="inline-flex justify-center rounded-md bg-white px-3 py-2 text-sm font-semibold text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50 sm:mt-0 sm:w-auto"
                            @click="followings(person.Id)" ref="cancelButtonRef">팔로우</button>
                    </div>
                </div>
            </div>
        </li>
    </ul>
</template>
  
