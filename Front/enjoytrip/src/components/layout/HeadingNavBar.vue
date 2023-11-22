<script setup>
import { ref } from "vue";
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

const products = [
  {
    name: "Analytics",
    description: "Get a better understanding of your traffic",
    href: "#",
    icon: ChartPieIcon,
  },
  {
    name: "Engagement",
    description: "Speak directly to your customers",
    href: "#",
    icon: CursorArrowRaysIcon,
  },
  {
    name: "Security",
    description: "Your customers’ data will be safe and secure",
    href: "#",
    icon: FingerPrintIcon,
  },
  {
    name: "Integrations",
    description: "Connect with third-party tools",
    href: "#",
    icon: SquaresPlusIcon,
  },
  {
    name: "Automations",
    description: "Build strategic funnels that will convert",
    href: "#",
    icon: ArrowPathIcon,
  },
];
const callsToAction = [
  { name: "Watch demo", href: "#", icon: PlayCircleIcon },
  { name: "Contact sales", href: "#", icon: PhoneIcon },
];

const mobileMenuOpen = ref(false);

import { useMenuStore } from "@/stores/menu";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";
import { useRouter } from "vue-router";

const menuStore = useMenuStore();
const memberStore = useMemberStore();

const { isLogin } = storeToRefs(memberStore);
const { userInfo } = storeToRefs(memberStore);

const router = useRouter();

// const join = () => {
//   router.push({ name: 'join' });
// };

const logout = () => {
  console.log("???");
  isLogin.value = false;
  userInfo.value = null;
  alert("로그아웃 하였습니다.");
};
</script>

<template>
  <header class="bg-white">
    <nav
      class="mx-auto flex max-w-7xl items-center justify-between p-6 lg:px-8"
      aria-label="Global"
    >
      <div class="flex lg:flex-1">
        <router-link :to="{ name: 'home' }" class="-m-1.5 p-1.5">
          <span class="sr-only">Your Company</span>
          <img class="h-8 w-auto" src="https://cdn-icons-png.flaticon.com/128/4652/4652340.png" alt="" />
        </router-link>
        <h3 class="font-semibold text-xl ml-2 mt-1"> 여행의 시발점</h3>
      </div>
      <div class="flex lg:hidden">
        <button
          type="button"
          class="-m-2.5 inline-flex items-center justify-center rounded-md p-2.5 text-gray-700"
          @click="mobileMenuOpen = true"
        >
          <span class="sr-only">Open main menu</span>
          <Bars3Icon class="h-6 w-6" aria-hidden="true" />
        </button>
      </div>
      <PopoverGroup class="hidden lg:flex lg:gap-x-12">
        <Popover class="relative">
          <PopoverButton class="flex items-center gap-x-1 text-sm font-semibold leading-6 text-gray-900">
            임시 NAV
            <ChevronDownIcon class="h-5 w-5 flex-none text-gray-400" aria-hidden="true" />
          </PopoverButton>

          <transition
            enter-active-class="transition ease-out duration-200"
            enter-from-class="opacity-0 translate-y-1"
            enter-to-class="opacity-100 translate-y-0"
            leave-active-class="transition ease-in duration-150"
            leave-from-class="opacity-100 translate-y-0"
            leave-to-class="opacity-0 translate-y-1"
          >
            <PopoverPanel
              class="absolute -left-8 top-full z-10 mt-3 w-screen max-w-md overflow-hidden rounded-3xl bg-white shadow-lg ring-1 ring-gray-900/5"
            >
              <div class="p-4">
                <div
                  v-for="item in products"
                  :key="item.name"
                  class="group relative flex items-center gap-x-6 rounded-lg p-4 text-sm leading-6 hover:bg-gray-50"
                >
                  <div
                    class="flex h-11 w-11 flex-none items-center justify-center rounded-lg bg-gray-50 group-hover:bg-white"
                  >
                    <component
                      :is="item.icon"
                      class="h-6 w-6 text-gray-600 group-hover:text-indigo-600"
                      aria-hidden="true"
                    />
                  </div>
                  <div class="flex-auto">
                    <a :href="item.href" class="block font-semibold text-gray-900">
                      {{ item.name }}
                      <span class="absolute inset-0" />
                    </a>
                    <p class="mt-1 text-gray-600">{{ item.description }}</p>
                  </div>
                </div>
              </div>
              <div class="grid grid-cols-2 divide-x divide-gray-900/5 bg-gray-50">
                <a
                  v-for="item in callsToAction"
                  :key="item.name"
                  :href="item.href"
                  class="flex items-center justify-center gap-x-2.5 p-3 text-sm font-semibold leading-6 text-gray-900 hover:bg-gray-100"
                >
                  <component
                    :is="item.icon"
                    class="h-5 w-5 flex-none text-gray-400"
                    aria-hidden="true"
                  />
                  {{ item.name }}
                </a>
              </div>
            </PopoverPanel>
          </transition>
        </Popover>
        <router-link
          :to="{ name: 'map' }"
          href="#"
          class="text-sm font-semibold leading-6 text-gray-900"
          >Marketplace</router-link
        >
        <a href="#" class="text-sm font-semibold mr-10 leading-6 text-gray-900">Company</a>
      <div v-if="isLogin" class="hidden lg:flex lg:flex-1 lg:justify-end">
        <router-link
          :to="{ name: 'user-mypage', params: { userid: userInfo.userId } }"
          class="text-sm font-semibold leading-6 text-gray-900"
          >마이페이지 <span aria-hidden="true">&rarr;</span></router-link
        >
        <router-link
          to="/"
          @click.prevent="logout"
          class="text-sm font-semibold ml-3 leading-6 text-gray-900"
          >로그아웃 <span aria-hidden="true">&rarr;</span></router-link
        >
        <p class="text-sm font-semibold ml-5 leading-6 text-gray-900">
          {{ userInfo.userId }}님 반갑습니다.
        </p>
        <router-link :to="{ name: 'board' }" href="#"
          class="text-sm font-semibold leading-6 text-gray-900">게시판</router-link>
        <a href="#" class="text-sm font-semibold leading-6 text-gray-900">여행지 찾기</a>
        <router-link :to="{ name: 'user-search' }" class="text-sm font-semibold mr-20 leading-6 text-gray-900">유저 검색
        </router-link>
      </PopoverGroup>

      <div v-if="isLogin" class="hidden lg:flex lg:flex-1 lg:justify-end">
        <router-link :to="{ name: 'home' }" class="-m-1.5 p-1.5">
          <span class="sr-only">Your Company</span>
          <img class="h-8 w-auto mt-1" src="https://cdn-icons-png.flaticon.com/128/12328/12328821.png" alt="" />
        </router-link>

        <router-link :to="{ name: 'user-mypage', params: { userid: userInfo.userId } }"
          class="text-sm font-semibold leading-6 text-gray-900 ml-5 mt-2">마이페이지 <span
            aria-hidden="true">&rarr;</span></router-link>
        <router-link to="/" @click.prevent="logout" class="text-sm font-semibold ml-3 leading-6 text-gray-900 mt-2">로그아웃
          <span aria-hidden="true">&rarr;</span></router-link>
        <p class="text-sm font-semibold ml-5 leading-6 text-gray-900 mt-2"> {{ userInfo.userId }}님 반갑습니다.</p>
      </div>

      <div v-else class="hidden lg:flex lg:flex-1 lg:justify-end">
        <router-link
          :to="{ name: 'user-login' }"
          class="text-sm font-semibold leading-6 text-gray-900"
          >로그인 <span aria-hidden="true">&rarr;</span></router-link
        >
        <router-link
          :to="{ name: 'user-join' }"
          class="text-sm font-semibold ml-3 leading-6 text-gray-900"
          >회원가입 <span aria-hidden="true">&rarr;</span></router-link
        >
      </div>
    </nav>
    <Dialog as="div" class="lg:hidden" @close="mobileMenuOpen = false" :open="mobileMenuOpen">
      <div class="fixed inset-0 z-10" />
      <DialogPanel
        class="fixed inset-y-0 right-0 z-10 w-full overflow-y-auto bg-white px-6 py-6 sm:max-w-sm sm:ring-1 sm:ring-gray-900/10"
      >
        <div class="flex items-center justify-between">
          <a href="#" class="-m-1.5 p-1.5">
            <span class="sr-only">Your Company</span>
            <img
              class="h-8 w-auto"
              src="https://tailwindui.com/img/logos/mark.svg?color=indigo&shade=600"
              alt=""
            />
          </a>
          <button
            type="button"
            class="-m-2.5 rounded-md p-2.5 text-gray-700"
            @click="mobileMenuOpen = false"
          >
            <span class="sr-only">Close menu</span>
            <XMarkIcon class="h-6 w-6" aria-hidden="true" />
          </button>
        </div>
        <div class="mt-6 flow-root">
          <div class="-my-6 divide-y divide-gray-500/10">
            <div class="space-y-2 py-6">
              <Disclosure as="div" class="-mx-3" v-slot="{ open }">
                <DisclosureButton
                  class="flex w-full items-center justify-between rounded-lg py-2 pl-3 pr-3.5 text-base font-semibold leading-7 text-gray-900 hover:bg-gray-50"
                >
                  Product
                  <ChevronDownIcon
                    :class="[open ? 'rotate-180' : '', 'h-5 w-5 flex-none']"
                    aria-hidden="true"
                  />
                </DisclosureButton>
                <DisclosurePanel class="mt-2 space-y-2">
                  <DisclosureButton
                    v-for="item in [...products, ...callsToAction]"
                    :key="item.name"
                    as="a"
                    :href="item.href"
                    class="block rounded-lg py-2 pl-6 pr-3 text-sm font-semibold leading-7 text-gray-900 hover:bg-gray-50"
                  >
                    {{ item.name }}</DisclosureButton
                  >
                </DisclosurePanel>
              </Disclosure>
              <router-link
                :to="{ name: 'board-list' }"
                class="-mx-3 block rounded-lg px-3 py-2 text-base font-semibold leading-7 text-gray-900 hover:bg-gray-50"
                >Features</router-link
              >
              <a
                href="#"
                class="-mx-3 block rounded-lg px-3 py-2 text-base font-semibold leading-7 text-gray-900 hover:bg-gray-50"
                >Marketplace</a
              >
              <a
                href="#"
                class="-mx-3 block rounded-lg px-3 py-2 text-base font-semibold leading-7 text-gray-900 hover:bg-gray-50"
                >Company</a
              >
            </div>
            <div class="py-6">
              <a
                href="#"
                class="-mx-3 block rounded-lg px-3 py-2.5 text-base font-semibold leading-7 text-gray-900 hover:bg-gray-50"
                >Log in</a
              >
            </div>
          </div>
        </div>
      </DialogPanel>
    </Dialog>
  </header>
</template>
