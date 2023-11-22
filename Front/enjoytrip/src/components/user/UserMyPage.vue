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

const open = ref(false)

const pageUser = ref({
    userId: "",
    userPwd: "",
});


onMounted(() => {
    console.log("팔로우 리스트 가져오는중 !!")
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

            <h2 class="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">{{ userInfo.userNickname
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

            <router-link :to="{ name: 'user-follow' }" class="ml-8 font-bold leading-9 tracking-tight text-gray-900">팔로우 {{
                follow.length }}
            </router-link>
        </div>

        <div class=" sm:mx-auto sm:w-full sm:max-w-sm">
            <button type="button" @click="moveUpdate"
                class="bi bi-gear flex w-full justify-center rounded-md px-3 py-1.5 text-sm  font-semibold leading-6  shadow-sm  focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">프로필
                편집 </button>
        </div>

        <!-- 게시글 수 정보 -->
        <h3 class="mt-10 mr-40 text-center font-bold leading-9 tracking-tight text-gray-900">나의 여행 플랜</h3>
        <hr class="mt-3 m-auto w-50 two">

        <div class="border border-1 rounded-3 border-primary m-auto mt-3">
            <span class="inline-flex">
                <img class="w-50 p-3"
                    src="data:image/jpeg;base64,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"
                    alt="" />
                <div>
                    <h3 class="text-2xl text-center font-bold ml-5 mt-3 leading-9 tracking-tight text-gray-900">도쿄 시부야 여행
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



        <!-- 회원탈퇴 -->
        <div class="mt-20 sm:mx-auto sm:w-full sm:max-w-sm">
            <button type="button" @click="open = true"
                class="flex w-full justify-center rounded-md px-3 py-1.5 text-sm bg-danger text-light font-semibold leading-6  shadow-sm  focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">회원탈퇴</button>
        </div>
    </div>


    <div v-if="open">
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
                                class="relative transform overflow-hidden rounded-lg bg-white text-left shadow-xl transition-all sm:my-8 sm:w-full sm:max-w-lg">
                                <div class="bg-white px-4 pb-4 pt-5 sm:p-6 sm:pb-4">
                                    <div class="sm:flex sm:items-start">
                                        <div
                                            class="mx-auto flex h-12 w-12 flex-shrink-0 items-center justify-center rounded-full bg-red-100 sm:mx-0 sm:h-10 sm:w-10">
                                            <ExclamationTriangleIcon class="h-6 w-6 text-red-600" aria-hidden="true" />
                                        </div>
                                        <div class="mt-3 text-center sm:ml-4 sm:mt-0 sm:text-left">
                                            <DialogTitle as="h3" class="text-base font-semibold leading-6 text-gray-900">
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
</template>
<style scoped></style>
