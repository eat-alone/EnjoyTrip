<script setup>
import { ref, onMounted } from "vue";
import { httpStatusCode } from "@/util/http-status";
import { getReceiveCount } from "@/api/letter"
import {
  Dialog,
  DialogPanel,
  Disclosure,
  DisclosureButton,
  DisclosurePanel,
  Popover,
  PopoverButton,
  PopoverGroup,
  PopoverPanel,
} from "@headlessui/vue";
import {
  ArrowPathIcon,
  Bars3Icon,
  ChartPieIcon,
  CursorArrowRaysIcon,
  FingerPrintIcon,
  SquaresPlusIcon,
  XMarkIcon,
} from "@heroicons/vue/24/outline";
import { ChevronDownIcon, PhoneIcon, PlayCircleIcon } from "@heroicons/vue/20/solid";


const mobileMenuOpen = ref(false);

import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";
import { useRouter } from "vue-router";

const memberStore = useMemberStore();

const { isLogin } = storeToRefs(memberStore);
const { userInfo } = storeToRefs(memberStore);

const router = useRouter();

onMounted(async () => {
  await getLetterCount(userInfo.value.Id);
})


const noReadLetterCount = ref(null);
const noReadLetterCountvalue = ref(false);
const getLetterCount = async (userId) => {
  await getReceiveCount(
    userId,
    (response) => {
      if (response.status === httpStatusCode.OK) {
        console.log(response.data)
        if (response.data.substr(0, 1) > 0) {
          alert(response.data);
          noReadLetterCount.value = response.data.substr(0, 1)
          noReadLetterCountvalue.value = true;
        } else {
          noReadLetterCountvalue.value = false;
        }
        // console.log(noReadLetterCount)
      } else {
        alert(response.data);
      }
    },
  );
};

// const join = () => {
//   router.push({ name: 'join' });
// };

const logout = () => {
  console.log("???");
  isLogin.value = false;
  userInfo.value = null;
  alert("로그아웃 하였습니다.");
  router.push({ name: 'home' })
};
</script>

<template>
  <header class="bg-gray-200">
    <nav class="mx-auto flex max-w-7xl items-center justify-between p-1 lg:px-8" aria-label="Global">
      <div class="flex sm:flex-auto">
        <router-link :to="{ name: 'home' }" class="-m-1.5 p-1.5">
          <span class="sr-only">Your Company</span>
          <img class="h-10 w-auto" src="https://cdn-icons-png.flaticon.com/128/4652/4652340.png" alt="" />
        </router-link>
        <h3 class="font-bold text-2xl ml-2 mt-2"> 걸어서 세계속으로</h3>
      </div>
      <PopoverGroup class="hidden lg:flex lg:gap-x-12">
        <router-link :to="{ name: 'board-announce' }" href="#"
          class="text-sm font-semibold leading-6 text-gray-900">공지사항</router-link>
        <router-link :to="{ name: 'board' }" href="#" class="text-sm font-semibold leading-6 text-gray-900">자유
          게시판</router-link>
        <a href="#" class="text-sm font-semibold leading-6 text-gray-900">여행지 찾기</a>
        <router-link :to="{ name: 'user-search' }" class="text-sm font-semibold mr-20 leading-6 text-gray-900">유저 검색
        </router-link>
      </PopoverGroup>

      <div v-if="isLogin" class="hidden lg:flex  ">

        <span class="sr-only">Your Company</span>
        <div class="relative">
          <router-link :to="{ name: 'letter-receive' }" class="-m-1.5 p-1.5">
            <img class="h-8 w-auto" src="https://cdn-icons-png.flaticon.com/128/12328/12328821.png" alt="" />
          </router-link>
          <p v-if="noReadLetterCountvalue"
            class="absolute top-4 left-5 text-xs p-1 px-2 border rounded-circle bg-danger text-white ">{{
              noReadLetterCount
            }}
          </p>
        </div>

        <router-link :to="{ name: 'user-mypage', params: { userid: userInfo.userId } }"
          class="text-sm font-semibold leading-6 text-gray-900 ml-5 mt-4">마이페이지 <span
            aria-hidden="true">&rarr;</span></router-link>
        <router-link to="/" @click.prevent="logout" class="text-sm font-semibold ml-3 leading-6 text-gray-900 mt-4">로그아웃
          <span aria-hidden="true">&rarr;</span></router-link>
        <p class="text-sm font-semibold ml-5 leading-6 text-gray-900 mt-4"> {{ userInfo.userId }}님 반갑습니다.</p>
      </div>

      <div v-else class="hidden lg:flex lg:flex-1 lg:justify-end">
        <router-link :to="{ name: 'user-login' }" class="text-sm font-semibold leading-6 text-gray-900">로그인 <span
            aria-hidden="true">&rarr;</span></router-link>
        <router-link :to="{ name: 'user-join' }" class="text-sm font-semibold ml-3 leading-6 text-gray-900">회원가입 <span
            aria-hidden="true">&rarr;</span></router-link>
      </div>
    </nav>
    <Dialog as="div" class="lg:hidden" @close="mobileMenuOpen = false" :open="mobileMenuOpen">
      <div class="fixed inset-0 z-10" />
      <DialogPanel
        class="fixed inset-y-0 right-0 z-10 w-full overflow-y-auto bg-white px-6 py-6 sm:max-w-sm sm:ring-1 sm:ring-gray-900/10">
        <div class="flex items-center justify-between">
          <a href="#" class="-m-1.5 p-1.5">
            <span class="sr-only">Your Company</span>
            <img class="h-8 w-auto" src="https://tailwindui.com/img/logos/mark.svg?color=indigo&shade=600" alt="" />
          </a>
          <button type="button" class="-m-2.5 rounded-md p-2.5 text-gray-700" @click="mobileMenuOpen = false">
            <span class="sr-only">Close menu</span>
            <XMarkIcon class="h-6 w-6" aria-hidden="true" />
          </button>
        </div>
        <div class="mt-6 flow-root">
          <div class="-my-6 divide-y divide-gray-500/10">
            <div class="space-y-2 py-6">
              <Disclosure as="div" class="-mx-3" v-slot="{ open }">
                <DisclosureButton
                  class="flex w-full items-center justify-between rounded-lg py-2 pl-3 pr-3.5 text-base font-semibold leading-7 text-gray-900 hover:bg-gray-50">
                  Product
                  <ChevronDownIcon :class="[open ? 'rotate-180' : '', 'h-5 w-5 flex-none']" aria-hidden="true" />
                </DisclosureButton>
                <DisclosurePanel class="mt-2 space-y-2">
                  <DisclosureButton v-for="item in [...products, ...callsToAction]" :key="item.name" as="a"
                    :href="item.href"
                    class="block rounded-lg py-2 pl-6 pr-3 text-sm font-semibold leading-7 text-gray-900 hover:bg-gray-50">
                    {{ item.name }}</DisclosureButton>
                </DisclosurePanel>
              </Disclosure>
              <router-link :to="{ name: 'board-list' }"
                class="-mx-3 block rounded-lg px-3 py-2 text-base font-semibold leading-7 text-gray-900 hover:bg-gray-50">Features</router-link>
              <a href="#"
                class="-mx-3 block rounded-lg px-3 py-2 text-base font-semibold leading-7 text-gray-900 hover:bg-gray-50">Marketplace</a>
              <a href="#"
                class="-mx-3 block rounded-lg px-3 py-2 text-base font-semibold leading-7 text-gray-900 hover:bg-gray-50">Company</a>
            </div>
            <div class="py-6">
              <a href="#"
                class="-mx-3 block rounded-lg px-3 py-2.5 text-base font-semibold leading-7 text-gray-900 hover:bg-gray-50">Log
                in</a>
            </div>
          </div>
        </div>
      </DialogPanel>
    </Dialog>
  </header>
</template>