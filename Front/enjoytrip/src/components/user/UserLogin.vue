<script setup>
import { ref } from "vue";
import { storeToRefs } from "pinia";
import { useRouter } from "vue-router";
import { useMemberStore } from "@/stores/member";


import { Menu, MenuButton, MenuItem, MenuItems } from '@headlessui/vue'
import { ChevronDownIcon } from '@heroicons/vue/20/solid'


const router = useRouter();

const memberStore = useMemberStore();

const { isLogin } = storeToRefs(memberStore);
const { userLogin, getUserInfo } = memberStore;

const loginUser = ref({
    userId: "",
    userPwd: "",
});



const login = async () => {

    await userLogin(loginUser.value);
    let token = sessionStorage.getItem("accessToken");
    console.log(token);
    if (isLogin) {
        getUserInfo(token); //위에서는 토큰 발급과정, 여기는 이름값을 조회해서 피니아에 저장
    }
    router.push("/");
};

</script>

<template>
    <div
        :style="{ 'background-size': 'cover', 'background-image': 'url(https://e0.pxfuel.com/wallpapers/397/308/desktop-wallpaper-paper-plane-cute-paper-airplane.jpg)' }">

        <!--
      This example requires updating your template:
  
      ```
      <html class="h-full bg-white">
      <body class="h-full">
      ```
    -->
        <div class="flex min-h-full flex-1 flex-col justify-center px-6 py-12 lg:px-8">
            <div class="sm:mx-auto sm:w-full sm:max-w-sm">
                <img class="mx-auto h-10 w-auto" src="https://cdn-icons-png.flaticon.com/128/4652/4652340.png"
                    alt="Your Company" />
                <h2 class="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">로그인
                </h2>
            </div>

            <div class="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
                <form class="space-y-6" action="#" method="POST">
                    <div>
                        <label for="email" class="block text-sm font-medium leading-6 text-gray-900">아이디</label>
                        <div class="mt-2">
                            <input id="email" name="email" type="email" autocomplete="email" required=""
                                v-model="loginUser.userId"
                                class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6" />
                        </div>
                    </div>

                    <div>
                        <label for="password" class="block text-sm font-medium leading-6 text-gray-900">비밀번호</label>
                        <div class="mt-2">
                            <input id="password" name="email" type="password" autocomplete="email" required=""
                                v-model="loginUser.userPwd"
                                class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6" />
                        </div>
                    </div>

                    <button type="button" @click="login"
                        class="flex w-full justify-center rounded-md px-3 py-1.5 text-sm font-semibold leading-6  shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">Login</button>
                </form>

            </div>
        </div>
    </div>
</template>

<style scoped></style>