<script setup>
import { ref, onMounted } from "vue";
import { storeToRefs } from "pinia";
import { useRouter, useRoute } from "vue-router";
import { useMemberStore } from "@/stores/member";
import { useLetterStore } from "@/stores/letter";


const route = useRoute();
const router = useRouter();
const letterStore = useLetterStore();
const memberStore = useMemberStore();
const { receiveLetter } = storeToRefs(letterStore);
const { getReceiveLetter } = letterStore;
const { userInfo } = storeToRefs(memberStore);


onMounted(() => {
    getReceiveletter(userInfo.value.userId);
});

function deleteLetter() {
    //삭제 로직 추가
    alert("삭제 되었습니다.")
}

const getReceiveletter = async (id) => {
    console.log(id)
    await getReceiveLetter(id);
}

const type = ref(true);

</script>

<template>
    <div class="mx-auto text-center ms-5">
        <span class="float-left">보내신 분</span>
        <span class="text-center">제목</span>
        <span class="">날짜</span>
    </div>
    <hr class="mx-auto w-[70%]">
    <div class="border rounded-5 w-4/5 mx-auto shadow-sm p-5 h-80 overflow-scroll">

        <div v-if="type">
            <ul role="list" class="">
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
                        </router-link>
                        <div class="hidden shrink-0 sm:flex sm:flex-col sm:items-end">
                            <p class="text-sm leading-6 text-gray-900 mt-3 ">{{ letter.sendDate }}</p>
                        </div>
                        <button @click="deleteLetter(letter.Id)" class="bg-danger">삭제</button>
                    </div>

                </li>
            </ul>
        </div>






        <div v-else>

            <ul role="list" class="divide-y w-2/5 ">
                <!-- v-for="person in follow" :key="person.Id" -->
                <li class="flex justify-between gap-x-6 py-5">
                    <div class="flex min-w-0 gap-x-4">
                        <img class="h-12 w-12 flex-none rounded-full bg-gray-50"
                            src='https://images.unsplash.com/photo-1506794778202-cad84cf45f1d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80'
                            alt="" />
                        <div class="min-w-0 flex-auto">
                            <p class="text-sm font-semibold leading-6 text-gray-900">clsrndls4923</p>
                            <p class="mt-1 truncate text-xs leading-5 text-gray-500">골목대장퉁퉁이</p>
                        </div>
                    </div>
                    <div class="hidden shrink-0 sm:flex sm:flex-col sm:items-end">
                        <p class="text-sm leading-6 text-gray-900">맛집 정보좀요 ㅠㅠㅠ</p>
                    </div>
                </li>
            </ul>

        </div>
    </div>
</template>

<style scoped></style>