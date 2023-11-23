<script setup>
import { ref, onMounted } from "vue";
import { storeToRefs } from "pinia";
import { useRouter, useRoute } from "vue-router";
import { useMemberStore } from "@/stores/member";
import { useFollowStore } from "@/stores/follow";
import { Dialog, DialogPanel, DialogTitle, TransitionChild, TransitionRoot } from '@headlessui/vue'
import { ExclamationTriangleIcon } from '@heroicons/vue/24/outline'


const route = useRoute();
const router = useRouter();
const memberStore = useMemberStore();
const followStore = useFollowStore();
const { userInfo } = storeToRefs(memberStore);
const { follow, follower } = storeToRefs(followStore);
const { deleteUser } = memberStore;
const { getFollowList, getFollowerList } = followStore;
const isMypage = ref(null);
const open = ref(false)

const pageUser = ref({
    userId: "",
    userPwd: "",
});


onMounted(() => {
    console.log("팔로우 리스트 가져오는중 !!")
    if (userInfo.value.userId == route.params.userid) {
        isMypage.value = true;
    }
    else {
        isMypage.value = false;
    }

    console.log("*****")
    console.log(userInfo.value.userId)
    console.log(route.params.userid);
    console.log(isMypage.value)
    getFollow();
    getFollower();
});

const getFollow = async () => {
    console.log(route.params.userid)
    await getFollowList(route.params.userid);
}

const getFollower = async () => {
    await getFollowerList(route.params.userid);
}

const deleteuser = async () => {
    let token = sessionStorage.getItem("accessToken");
    console.log(token)
    await deleteUser(token);

    open.value = false;
    alert("회원탈퇴 되었습니다.")
    router.push("/")
}

function moveUpdate() {
    router.push({ name: 'user-modify', params: { userid: userInfo.value.userId } });
}

</script>


<template>
    <!--
      This example requires updating your template:
  
      ```
      <html class="h-full bg-white">
      <body class="h-full">
      ```
    -->
    <img class="rounded mx-auto d-block h-30 w-24 rounded-full ring-2 ring-white"
        src="data:image/jpeg;base64,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"
        alt="" />


    <div class="flex min-h-full flex-1 flex-col justify-center px-6 lg:px-8">
        <div class="sm:mx-auto sm:w-full sm:max-w-sm">
            <!-- <h2 class="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">{{ userInfo.userId }}님
            </h2> -->

            <h2 class="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">{{
                route.params.userid
            }}
            </h2>
        </div>

        <div class="sm:mx-auto sm:w-full sm:max-w-sm">
            <!-- <h2 class="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">{{ userInfo.userId }}님
            </h2> -->

            <router-link to="" class="ml-10 mr-8 font-bold leading-9 tracking-tight text-gray-900">게시물 7
            </router-link>

            <router-link :to="{ name: 'user-follower' }"
                class="text-center font-bold leading-9 tracking-tight text-gray-900">팔로워 {{ follower.length }}
            </router-link>

            <router-link :to="{ name: 'user-follow' }" class="ml-8 font-bold leading-9 tracking-tight text-gray-900">팔로우
                {{
                    follow.length }}
            </router-link>
        </div>

        <div v-if="isMypage">
            <div class=" sm:mx-auto sm:w-full sm:max-w-sm">
                <button type="button" @click="moveUpdate"
                    class="bi bi-gear flex w-full justify-center rounded-md px-3 py-1.5 text-sm  font-semibold leading-6  shadow-sm  focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">프로필
                    편집 </button>
            </div>
        </div>
        <!-- 게시글 수 정보 -->
        <h3 class="mt-10 mr-40 text-center font-bold leading-9 tracking-tight text-gray-900">나의 여행 플랜</h3>
        <hr class="mt-3 m-auto w-50 two">

        <div class="border border-1 rounded-3 border-primary m-auto mt-3">
            <span class="inline-flex">
                <img class="w-auto h-[15rem] p-3"
                    src="https://rimage.gnst.jp/livejapan.com/public/article/detail/a/00/03/a0003120/img/basic/a0003120_main.jpg?20210203113541&q=80"
                    alt="" />
                <div class="mr-5">
                    <h3 class="text-2xl text-center mr-5 font-bold ml-5 mt-3 leading-9 tracking-tight text-gray-900">도쿄 시부야
                        여행
                    </h3>

                    <h3 class="text-sm ml-5">2023-01-06 ~ 2023-01-09</h3>
                    <div class="inline-flex ml-5 mt-3">
                        <img class="h-8 w-auto inling-flex" src="https://cdn-icons-png.flaticon.com/128/2815/2815428.png"
                            alt="" />
                        <h3 class="text-sm ml-1 mt-2 font-semibold">인원 3명</h3>
                    </div>
                    <button type="button"
                        class="inline-flex rounded-md float-right bg-success px-3 py-2 text-sm font-semibold text-gray-900 text-white shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50 sm:mt-3"
                        @click="open = false" ref="cancelButtonRef">초대</button>
                </div>
            </span>
        </div>


        <div v-if="isMypage">
            <!-- 회원탈퇴 -->
            <div class="mt-20 sm:mx-auto sm:w-full sm:max-w-sm">
                <button type="button" @click="open = true"
                    class="flex w-full justify-center rounded-md px-3 py-1.5 text-sm bg-danger text-light font-semibold leading-6  shadow-sm  focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">회원탈퇴</button>
            </div>
        </div>


        <div v-if="open">
            <TransitionRoot as="template" :show="open">
                <Dialog as="div" class="relative z-10" @close="open = false">
                    <TransitionChild as="template" enter="ease-out duration-300" enter-from="opacity-0"
                        enter-to="opacity-100" leave="ease-in duration-200" leave-from="opacity-100" leave-to="opacity-0">
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
                                    class="relative transform overflow-hidden rounded-lg bg-white text-left shadow-xl transition-all sm:my-8 sm:w-full sm:max-w-lg">
                                    <div class="bg-white px-4 pb-4 pt-5 sm:p-6 sm:pb-4">
                                        <div class="sm:flex sm:items-start">
                                            <div
                                                class="mx-auto flex h-12 w-12 flex-shrink-0 items-center justify-center rounded-full bg-red-100 sm:mx-0 sm:h-10 sm:w-10">
                                                <ExclamationTriangleIcon class="h-6 w-6 text-red-600" aria-hidden="true" />
                                            </div>
                                            <div class="mt-3 text-center sm:ml-4 sm:mt-0 sm:text-left">
                                                <DialogTitle as="h3"
                                                    class="text-base font-semibold leading-6 text-gray-900">
                                                    혼또니 탈퇴 진행하시겠습니까?</DialogTitle>
                                                <div class="mt-2">
                                                    <p class="text-sm text-gray-500">탈퇴 버튼 선택 시, 계정은 삭제되며 복구되지 않습니다.</p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="bg-gray-50 px-4 py-3 sm:flex sm:flex-row-reverse sm:px-6">
                                        <button type="button"
                                            class="mt-3 inline-flex w-full justify-center rounded-md bg-red-600 px-3 py-2 text-sm font-semibold  shadow-sm hover:bg-red-500 sm:ml-3 sm:w-auto"
                                            @click="deleteuser">회원탈퇴</button>
                                        <button type="button"
                                            class="mt-3 inline-flex w-full justify-center rounded-md bg-white px-3 py-2 text-sm font-semibold text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50 sm:mt-0 sm:w-auto"
                                            @click="open = false" ref="cancelButtonRef">취소</button>
                                    </div>
                                </DialogPanel>
                            </TransitionChild>
                        </div>
                    </div>
                </Dialog>
            </TransitionRoot>
        </div>
    </div>
</template>
<style scoped></style>
