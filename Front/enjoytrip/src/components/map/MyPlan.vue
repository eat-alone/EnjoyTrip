<script setup>
import { ref, onMounted } from "vue";
import { storeToRefs } from "pinia";
import { useRouter, useRoute } from "vue-router";
import { useMemberStore } from "@/stores/member";
import { useFollowStore } from "@/stores/follow";
import { getMyPlanList, goInviteMember } from "@/api/plan";
import { useTripStore } from "@/stores/trip";
import { Dialog, DialogPanel, DialogTitle, TransitionChild, TransitionRoot } from "@headlessui/vue";
import { ExclamationTriangleIcon } from "@heroicons/vue/24/outline";
import { httpStatusCode } from "@/util/http-status";
const tripStore = useTripStore();
const { changePlan } = tripStore;
const route = useRoute();
const router = useRouter();
const memberStore = useMemberStore();
const followStore = useFollowStore();
const { userInfo } = storeToRefs(memberStore);
const { follow, follower } = storeToRefs(followStore);
const isMypage = ref(null);
const open = ref(false);
const openInvite = ref(false);

const pageUser = ref({
  userId: "",
  userPwd: "",
});

onMounted(() => {
  console.log(userInfo.value.userId);
  getPlanList(userInfo.value.userId);
});

const planList = ref(null);
const planInfo = ref({
  nowId: "",
  userId: "",
});
const getPlanList = async (userid) => {
  await getMyPlanList(userid, (response) => {
    console.log("성공적으로 여행플랜 불러옴.");
    planList.value = response.data;
    console.log(response.data);
  });
};

const viewPlan = (plan) => {
  console.log("플랜전송", plan);
  changePlan(plan);
  router.push({ name: "test", params: { isNew: "no" } });
};
</script>

<template>
  <div
    v-for="plan in planList"
    :key="planList.plan_id"
    class="border border-1 rounded-5 border-primary m-auto mt-3"
  >
    <span class="inline-flex">
      <img
        class="w-[50%] h-[13rem] p-3"
        src="https://rimage.gnst.jp/livejapan.com/public/article/detail/a/00/03/a0003120/img/basic/a0003120_main.jpg?20210203113541&q=80"
        alt=""
      />
      <div class="mr-5">
        <button @click="viewPlan(plan)">
          <h3
            class="text-xl text-center mr-5 font-bold ml-5 mt-3 leading-9 tracking-tight text-gray-900"
          >
            {{ plan.planName }}
          </h3>
        </button>

        <h3 class="text-sm ml-5">{{ plan.startDate }} ~ {{ plan.endDate }}</h3>
        <div class="inline-flex ml-5 mt-3">
          <img
            class="h-8 w-auto inling-flex"
            src="https://cdn-icons-png.flaticon.com/128/2815/2815428.png"
            alt=""
          />
          <h3 class="text-sm ml-1 mt-2 font-semibold">{{ plan.teamCnt }} 명</h3>
        </div>
        <div class="flex">
          <h3 class="text-sm ml-5 font-semibold mt-2">생성자 : {{ plan.creatorId }}</h3>
          <button
            class="inline-flex rounded-md float-right bg-success px-3 py-2 ml-5 mb-3 text-sm font-semibold text-gray-900 text-white shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50"
            @click="inviteMember(plan.planId)"
            ref="cancelButtonRef"
          >
            초대
          </button>
        </div>
      </div>
      <!-- inviteMember(plan.planId) -->
    </span>
  </div>
</template>

<style scoped></style>
