<script setup>
import { ref, onMounted } from "vue";
import { CalendarDaysIcon, HandRaisedIcon, ExclamationTriangleIcon } from '@heroicons/vue/24/outline'
import { Dialog, DialogPanel, DialogTitle, TransitionChild, TransitionRoot } from '@headlessui/vue'

import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";
import { useSearchStore } from "@/stores/search";
import { useFollowStore } from "@/stores/follow";
import { sendLetters } from "@/api/letter";
import { httpStatusCode } from "@/util/http-status";

const memberStore = useMemberStore();
const searchStore = useSearchStore();
const followStore = useFollowStore();

const { userList } = storeToRefs(searchStore);
const { follow } = storeToRefs(followStore);
const { following } = followStore;
const { userInfo } = storeToRefs(memberStore);
const { searchUser } = searchStore;

const searchId = ref("");

const open = ref(false)

const sendContent = ref({
    fromId: userInfo.value.Id,
    toId: "",
    subject: "",
    content: "",
})
function sendCancel() {
    open.value = false;
    sendContent.value.subject = "";
    sendContent.value.content = "";
}

async function sendData() {
    this.isSend = false;
    await sendDatas(this.sendContent);

    open.value = false;
}

onMounted(() => {
    userList.value = null;
})

const sendDatas = async (param) => {
    await sendLetters(
        param,
        (response) => {
            if (response.status === httpStatusCode.OK) {
                alert(response.data);
            } else {
                alert(response.data);
            }
        },
        async (error) => {
            console.error(
                "getUserInfo() error code [토큰 만료되어 사용 불가능.] ::: ",
                error.response.status
            );
            isValidToken.value = false;

            await tokenRegenerate();
        }
    );
};


function sendLetter(id) {
    sendContent.value.toId = id;
    open.value = true;
}


function search() {
    getUser()
}

const getUser = async () => {
    console.log(searchId.value)
    await searchUser(searchId.value);
    console.log(userList.value)
}

function followings(userId) {
    console.log(userInfo.value.Id)
    console.log(userId)
    following(userInfo.value.Id, userId);
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
                        <dd class="mt-2 leading-7 text-gray-400">아직 서로 팔로우 되어있는지에 대한 View는 구현하지 못했어요 ㅠㅠ.. 마이페이지에서 확인 부탁드립니다!
                        </dd>
                    </div>
                    <div class="flex flex-col items-start">
                        <div class="rounded-md bg-white/5 p-2 ring-1 ring-white/10">
                            <HandRaisedIcon class="h-6 w-6 text-white" aria-hidden="true" />
                        </div>
                        <dt class="mt-4 font-semibold text-white">쪽지 기능</dt>
                        <dd class="mt-2 leading-7 text-gray-400">모르는 부분들을 친구 검색을 통해 다양한 질문으로 해결해보아요!</dd>
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
                        <button type="button"
                            class="inline-flex justify-center rounded-md bg-white px-3 py-2 text-sm font-semibold text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50 sm:mt-0 sm:w-auto"
                            @click="sendLetter(person.Id)" ref="cancelButtonRef">쪽지 보내기</button>
                    </div>
                </div>
            </div>
        </li>
    </ul>


    <TransitionRoot as="template" :show="open">
        <Dialog as="div" class="relative z-10" @close="open = false">
            <TransitionChild as="template" enter="ease-out duration-300" enter-from="opacity-0" enter-to="opacity-100"
                leave="ease-in duration-200" leave-from="opacity-100" leave-to="opacity-0">
                <div class="fixed inset-0 bg-gray-500 bg-opacity-75 transition-opacity" />
            </TransitionChild>

            <div class="fixed inset-0 z-10 w-screen overflow-y-auto">
                <div class="flex min-h-full items-end justify-center p-4 text-center sm:items-center sm:p-0">
                    <TransitionChild as="template" enter="ease-out duration-300"
                        enter-from="opacity-0 translate-y-4 sm:translate-y-0 sm:scale-95"
                        enter-to="opacity-100 translate-y-0 sm:scale-100" leave="ease-in duration-200"
                        leave-from="opacity-100 translate-y-0 sm:scale-100"
                        leave-to="opacity-0 translate-y-4 sm:translate-y-0 sm:scale-95">
                        <DialogPanel
                            class="transform overflow-hidden rounded-lg bg-white text-left shadow-xl transition-all sm:my-8 sm:w-full sm:max-w-lg">
                            <div class="bg-white px-2 pb-2 pt-3 sm:p-6 sm:pb-4">
                                <div class="px-3">
                                    <div class="mt-3 text-center sm:ml-4 sm:mt-0 sm:text-left">
                                        <DialogTitle as="h3" class="text-base font-semibold leading-6 text-gray-900">
                                            보내실 메시지를 작성해주세요</DialogTitle>
                                        <div class="mt-2">
                                            <div class="mb-3">
                                                <span class="float-left">주제 : </span>
                                                <input id="email" name="email" type="text" autocomplete="email" required=""
                                                    v-model="sendContent.subject"
                                                    class="inline-block w-[85%] rounded-md border-1 mb-2 py-0.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6" />
                                            </div>
                                            <p class="float-left mr-5">내용 </p>
                                            <textarea name="content" id="contents" v-model="sendContent.content"
                                                class="block w-[85%] h-40 rounded-md border-1 py-1.5 pl-7 pr-20 text-gray-900 ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                                                placeholder="here content...."> </textarea>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="bg-gray-50 px-4 py-3 sm:flex sm:flex-row-reverse sm:px-6">
                                <button @click="sendData()"
                                    class="mt-3 inline-flex w-full justify-center bg-secondary rounded-md px-3 py-2 text-sm font-semibold text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-500 sm:mt-0 sm:w-auto">
                                    답장 보내기
                                </button>
                                <button type="button"
                                    class="mt-3 mr-3 inline-flex w-full justify-center rounded-md bg-white px-3 py-2 text-sm font-semibold text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50 sm:mt-0 sm:w-auto"
                                    @click="sendCancel()" ref="cancelButtonRef">취소</button>
                            </div>
                        </DialogPanel>
                    </TransitionChild>
                </div>
            </div>
        </Dialog>
    </TransitionRoot>
</template>
  
