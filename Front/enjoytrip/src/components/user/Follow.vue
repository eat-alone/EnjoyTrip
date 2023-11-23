<script setup>
import { ref } from "vue";
import { storeToRefs } from "pinia";
import { useFollowStore } from "@/stores/follow";
import { sendLetters } from "@/api/letter";
import { httpStatusCode } from "@/util/http-status";
const followStore = useFollowStore();
const { follow } = storeToRefs(followStore);
import { useMemberStore } from "@/stores/member";
import { Dialog, DialogPanel, DialogTitle, TransitionChild, TransitionRoot } from '@headlessui/vue'
import { ExclamationTriangleIcon } from '@heroicons/vue/24/outline'

const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);
const { deleteFollow, getFollowList } = followStore;

const open = ref(false)
const sendContent = ref({
    fromId: userInfo.value.id,
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


function clickUnFollow(id) {
    unfollow(id);
    console.log(id)
}



const unfollow = async (id) => {
    await deleteFollow(userInfo.value.Id, id);
    getFollow();
}


const getFollow = async () => {
    await getFollowList(userInfo.value.userId);
}

</script>

<template>
    <h2 class="text-center mt-4">팔로우 목록</h2>
    <h1 v-if="follow.length == 0" class="text-center mt-5">
        텅</h1>
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
                        @click="clickUnFollow(person.Id)" ref="cancelButtonRef">팔로우 취소</button>
                    <button type="button"
                        class="inline-flex justify-center rounded-md bg-white px-3 py-2 text-sm font-semibold text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50 sm:mt-0 sm:w-auto"
                        @click="sendLetter(person.Id)" ref="cancelButtonRef">쪽지 보내기</button>
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


  
  