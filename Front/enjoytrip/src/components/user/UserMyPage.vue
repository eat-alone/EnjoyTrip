<script setup>
import { ref, onMounted } from "vue";
import { storeToRefs } from "pinia";
import { useRouter, useRoute } from "vue-router";
import { useMemberStore } from "@/stores/member";
import { useFollowStore } from "@/stores/follow";
import { getMyPlanList, goInviteMember } from "@/api/plan";
import { Dialog, DialogPanel, DialogTitle, TransitionChild, TransitionRoot } from '@headlessui/vue'
import { ExclamationTriangleIcon } from '@heroicons/vue/24/outline'
import { httpStatusCode } from "@/util/http-status";

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
const openInvite = ref(false);

const pageUser = ref({
    userId: "",
    userPwd: "",
});

const planList = ref(null);
const planInfo = ref({
    nowId: "",
    userId: "",
})

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
    getPlanList(route.params.userid);
});

const getFollow = async () => {
    console.log(route.params.userid)
    await getFollowList(route.params.userid);
}

const getFollower = async () => {
    await getFollowerList(route.params.userid);
}

function goInviteThisPlan(userid) {
    console.log(userid + "***********");
    planInfo.value.userId = userid;
    goInviteMembers();
}

const goInviteMembers = async () => {
    await goInviteMember(
        planInfo.value,
        (response) => {
            if (response.status === httpStatusCode.OK) {
                alert(response.data)
            }
            else {
                console.log("성공적으로 초대완료");
                console.log(response.data);
                getPlanList(route.params.userid)
                openInvite.value = false;
            }
        }
    );
};

function inviteMember(planid) {
    planInfo.value.nowId = planid;
    console.log(planInfo.value.nowId);
    openInvite.value = true;
    console.log(openInvite.value)
}

const getPlanList = async (userid) => {
    await getMyPlanList(
        userid,
        (response) => {
            console.log("성공적으로 여행플랜 불러옴.");
            planList.value = response.data;
            console.log(response.data)
        }
    );
};


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
    <img class="rounded mx-auto d-block h-30 w-24 mt-5 rounded-full ring-2 ring-black"
        src="c:\Users\KimJeongHyeon\Desktop\image.png" alt="" />


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

        <div v-if="isMypage" class="mt-3">
            <div class=" sm:mx-auto sm:w-full sm:max-w-sm">
                <button type="button" @click="moveUpdate"
                    class="bi bi-gear flex w-full justify-center rounded-md px-3 py-1.5 text-sm border-1 border-black font-semibold leading-6  shadow-sm  focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">프로필
                    편집 </button>
            </div>
        </div>
        <!-- 게시글 수 정보 -->
        <h3 class="mt-10 mr-40 mx-auto text-center font-bold leading-9 tracking-tight text-gray-900">여행 플랜 리스트</h3>
        <hr class="mt-3 m-auto w-50 two">

        <!-- 여기서부터 for문 -->
        <div v-for="plan in planList" :key="planList.plan_id" class="border border-1 rounded-5 border-primary m-auto mt-3">
            <span class="inline-flex">
                <img class="w-[50%] h-[13rem] p-3"
                    src="https://rimage.gnst.jp/livejapan.com/public/article/detail/a/00/03/a0003120/img/basic/a0003120_main.jpg?20210203113541&q=80"
                    alt="" />
                <div class="mr-5">
                    <h3 class="text-xl text-center mr-5 font-bold ml-5 mt-3 leading-9 tracking-tight text-gray-900">
                        {{ plan.planName }}
                    </h3>

                    <h3 class="text-sm ml-5">{{ plan.startDate }} ~ {{ plan.endDate }}</h3>
                    <div class="inline-flex ml-5 mt-3">
                        <img class="h-8 w-auto inling-flex" src="https://cdn-icons-png.flaticon.com/128/2815/2815428.png"
                            alt="" />
                        <h3 class="text-sm ml-1 mt-2 font-semibold">{{ plan.teamCnt }} 명</h3>

                    </div>
                    <div class="flex">
                        <h3 class="text-sm ml-5 font-semibold mt-2">생성자 : {{ plan.creatorId }}</h3>
                        <button
                            class="inline-flex rounded-md float-right bg-success px-3 py-2 ml-5 mb-3 text-sm font-semibold text-gray-900 text-white shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50"
                            @click="inviteMember(plan.planId)" ref="cancelButtonRef">초대</button>
                    </div>
                </div>
                <!-- inviteMember(plan.planId) -->
            </span>
        </div>


        <div v-if="isMypage">
            <!-- 회원탈퇴 -->
            <div class="mt-20 mb-10 sm:mx-auto sm:w-full sm:max-w-sm">
                <button type="button" @click="open = true"
                    class="flex w-full justify-center rounded-4 px-3 py-1.5 text-sm bg-danger text-black font-semibold leading-6  shadow-sm  focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">회원탈퇴</button>
            </div>
        </div>


        <div v-if="openInvite">
            <TransitionRoot as="template" :show="openInvite">
                <Dialog as="div" class="relative z-10" @close="openInvite = false">
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
                                        <h3 class="text-center mt-4">팔로우 목록</h3>
                                        <h1 v-if="follow.length == 0" class="text-center mt-5">
                                            텅</h1>
                                        <ul role="list" class="">
                                            <li v-for="person in follow" :key="person.Id"
                                                class="flex justify-between gap-x-6 py-2">
                                                <div class="flex min-w-0 gap-x-4">
                                                    <img class="mt-1 h-12 w-12 flex-none rounded-full bg-gray-50"
                                                        src='https://images.unsplash.com/photo-1506794778202-cad84cf45f1d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80'
                                                        alt="" />
                                                    <div class="min-w-0 flex-auto">
                                                        <p class=" font-semibold text-lg  text-gray-500">
                                                            {{
                                                                person.userNickname }}</p>
                                                        <p class="text-sm font-semibold  text-gray-900">{{
                                                            person.userId }}</p>
                                                    </div>
                                                </div>
                                                <div class="hidden shrink-0 sm:flex sm:flex-col sm:items-end">
                                                    <p class="text-sm leading-6 text-gray-900">{{ person.role }}</p>
                                                    <p v-if="person.lastSeen" class="mt-1 text-xs leading-5 text-gray-500">
                                                        Last seen <time :datetime="person.lastSeenDateTime">{{
                                                            person.lastSeen }}</time>
                                                    </p>
                                                    <div v-else class="mt-1 flex items-center gap-x-1.5">
                                                        <div class="flex-none rounded-full bg-emerald-500/20 p-1">
                                                            <div class="h-1.5 w-1.5 rounded-full bg-emerald-500"></div>
                                                        </div>
                                                        <p class="text-xs mr-5 leading-5 text-gray-500">Online</p>

                                                        <button type="button"
                                                            class="inline-flex justify-center rounded-3 border-1 border-gray-300 bg-white px-3 py-2 text-sm font-semibold text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50 sm:mt-0 sm:w-auto"
                                                            @click="goInviteThisPlan(person.Id)"
                                                            ref="cancelButtonRef">초대하기</button>
                                                    </div>
                                                </div>
                                            </li>
                                        </ul>
                                    </div>
                                    <div class="bg-gray-50 px-4 py-3 sm:flex sm:flex-row-reverse sm:px-6">
                                        <button type="button"
                                            class="mt-3 inline-flex w-full justify-center rounded-md bg-white px-3 py-2 text-sm font-semibold text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50 sm:mt-0 sm:w-auto"
                                            @click="openInvite = false" ref="cancelButtonRef">취소</button>
                                    </div>
                                </DialogPanel>
                            </TransitionChild>
                        </div>
                    </div>
                </Dialog>
            </TransitionRoot>
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
                                                    탈퇴를 진행하시겠습니까?</DialogTitle>
                                                <div class="mt-2">
                                                    <p class="text-sm text-gray-500">탈퇴 버튼 선택 시,
                                                        계정은 삭제되며 복구되지 않습니다.</p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="bg-gray-50 px-4 py-3 sm:flex sm:flex-row-reverse sm:px-6">
                                        <button type="button"
                                            class="mt-3 inline-flex w-full justify-center rounded-md bg-red-600 px-3 py-2 text-sm font-semibold  shadow-sm hover:bg-red-500 sm:ml-3 sm:w-auto"
                                            @click="deleteuser">영구탈퇴</button>
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
