<script setup>
import { ref, onMounted } from "vue";
import { storeToRefs } from "pinia";
import { useRouter, useRoute } from "vue-router";
import { useMemberStore } from "@/stores/member";
import { useLetterStore } from "@/stores/letter";
import { deleteData } from "@/api/letter";
import { httpStatusCode } from "@/util/http-status";

const route = useRoute();
const router = useRouter();
const letterStore = useLetterStore();
const memberStore = useMemberStore();
const { receiveLetter, sendLetter } = storeToRefs(letterStore);
const { getReceiveLetter, getSendLetter } = letterStore;
const { userInfo } = storeToRefs(memberStore);


onMounted(async () => {
    console.log(receiveLetter.value)
    await getReceiveLetter(userInfo.value.userId);
    await getSendLetter(userInfo.value.userId);
});

async function deleteLetter(id) {
    //삭제 로직 추가
    await deleteDatas(id);
    console.log("성공적으로 쪽지 삭제 했습니다.")
    await getReceiveLetter(userInfo.value.userId);
    await getSendLetter(userInfo.value.userId);
}


function typeChangeReceive() {
    type.value = "수신";
}

function typeChangeSend() {
    type.value = "발신";
}

const deleteDatas = async (id) => {
    await deleteData(
        id,
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

const type = ref("수신");

</script>

<template>
    <div class="w-[60rem] m-auto">
        <button type="button"
            class="inline-flex rounded-md mr-2 bg-success px-3 py-2 text-sm font-semibold text-gray-900 text-white shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50 sm:mt-3"
            @click="typeChangeReceive()" ref="cancelButtonRef">수신함</button>
        <button type="button"
            class="inline-flex rounded-md bg-success px-3 py-2 text-sm font-semibold text-gray-900 text-white shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50 sm:mt-3"
            @click="typeChangeSend()" ref="cancelButtonRef">발신함</button>
    </div>
    <hr class="mx-auto w-[70%]">
    <div class="border rounded-5 w-4/5 mx-auto shadow-sm p-5 h-80 overflow-scroll">

        <div v-if="type === '수신'">
            <h1 class="text-lg font-semibold ml-10 leading-6 text-gray-900 mb-3">수신 메시지</h1>
            <div v-if="receiveLetter.length == 0">
                <h1 class="m-auto w-[20%] mt-5"> 텅 </h1>
            </div>
            <ul v-if="receiveLetter.length > 0" role="list" class="">
                <li v-for="letter in receiveLetter" :key="receiveLetter.Id" class="border border-1 p-2">
                    <div class="flex min-w-0 gap-x-4">
                        <img class="h-12 w-12 flex-none rounded-full bg-gray-50"
                            src='https://images.unsplash.com/photo-1506794778202-cad84cf45f1d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80'
                            alt="" />
                        <router-link :to="{ name: 'user-mypage', params: { userid: letter.fromId } }">
                            <p class="text-sm font-semibold leading-6 text-gray-900 mt-3">{{ letter.nickName }}</p>
                        </router-link>
                        <router-link
                            :to="{ name: 'letter-detail', params: { userid: letter.Id, username: letter.fromId } }">
                            <div v-if="letter.isRead == '1'" class="bg-secondary">
                                <div class="hidden shrink-0 sm:flex sm:flex-col sm:items-end">
                                    <p class="text-sm leading-6 text-gray-900 mt-3">{{ letter.subject }}</p>
                                </div>
                            </div>
                            <div v-else>
                                <div class="hidden shrink-0 sm:flex sm:flex-col sm:items-end">
                                    <p class="text-sm leading-6 text-gray-900 mt-3">{{ letter.subject }}</p>
                                </div>
                            </div>
                        </router-link>
                        <div class="hidden shrink-0 sm:flex sm:flex-col sm:items-end">
                            <p class="text-sm leading-6 text-gray-900 mt-3 ">{{ letter.sendDate }}</p>
                        </div>
                        <button type="button"
                            class="inline-flex rounded-md bg-danger px-3 py-2 text-sm font-semibold text-gray-900 text-white shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50 sm:mt-4 mb-3"
                            @click="deleteLetter(letter.Id)" ref="cancelButtonRef">삭제</button>
                    </div>

                </li>
            </ul>
        </div>






        <div v-else>
            <h1 class="text-lg font-semibold ml-10 leading-6 text-gray-900 mb-3">발신 메시지</h1>
            <ul role="list" class="">
                <div v-if="sendLetter.length == 0">
                    <h1 class="m-auto w-[20%] mt-5"> 텅 </h1>
                </div>
                <li v-for="letter in sendLetter" :key="sendLetter.Id" class="border border-1 p-2">
                    <div class="flex min-w-0 gap-x-4">
                        <img class="h-12 w-12 flex-none rounded-full bg-gray-50"
                            src='https://images.unsplash.com/photo-1506794778202-cad84cf45f1d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80'
                            alt="" />
                        <router-link :to="{ name: 'user-mypage', params: { userid: letter.fromId } }">
                            <p class="text-sm font-semibold leading-6 text-gray-900 mt-3">{{ letter.nickName }}</p>
                        </router-link>
                        <router-link
                            :to="{ name: 'letter-detail', params: { userid: letter.Id, username: letter.fromId } }">
                            <div class="hidden shrink-0 sm:flex sm:flex-col sm:items-end">
                                <p class="text-sm leading-6 text-gray-900 mt-3">{{ letter.subject }}</p>
                            </div>
                        </router-link>
                        <div class="hidden shrink-0 sm:flex sm:flex-col sm:items-end">
                            <p class="text-sm leading-6 text-gray-900 mt-3 ">{{ letter.sendDate }}</p>
                        </div>
                        <button type="button"
                            class="inline-flex rounded-md bg-danger px-3 py-2 text-sm font-semibold text-gray-900 text-white shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50 sm:mt-4 mb-3"
                            @click="deleteLetter(letter.Id)" ref="cancelButtonRef">삭제</button>
                    </div>

                </li>
            </ul>
        </div>
    </div>
</template>

<style scoped></style>