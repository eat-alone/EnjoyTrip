<script setup>
import { ref, onMounted } from 'vue';
import { Menu, MenuButton, MenuItem, MenuItems } from '@headlessui/vue'
import { ChevronDownIcon } from '@heroicons/vue/20/solid'
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";
import { useRoute, useRouter } from "vue-router";
import { registUser } from "@/api/user";

const router = useRouter();
const route = useRoute();

const memberStore = useMemberStore();

const { idValidMent } = storeToRefs(memberStore);
const { idCheck } = memberStore;


const Gender = ref("남자");

const userInfo = ref({
    userId: "",
    userName: "",
    userPwd: "",
    userNickname: "",
    userGender: "0",
    userBirthday: "",
    // joinDate: "",
})

onMounted(() => {
    idValidMent.value = null;
});

function genderchange_man() {
    this.userInfo.userGender = "0"
    this.Gender = "남자"
    console.log(userInfo.userGender)
    console.log(userInfo.value)
}

function genderchange_femail() {
    this.userInfo.userGender = "1"
    this.Gender = "여자"
    console.log(userInfo.value)
}

function onSubmit() {
    // event.preventDefault();
    console.log(userInfo.value);
    console.log("로그인 전")
    registerUsers();
}


function idcheck() {
    console.log(userInfo.value.userId);
    idCheck(userInfo.value.userId);
}

function registerUsers() {
    registUser(
        userInfo.value,
        (response) => {
            let msg = "회원가입 처리시 문제 발생했습니다.";
            if (response.status == 200) msg = "회원가입이 완료되었습니다.";
            alert(msg);
            moveMain();
        },
        (error) => console.log(error)
    );
}

function moveMain() {
    router.push({ name: 'home' });
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
    <div class="flex min-h-full flex-1 flex-col justify-center px-6 py-12 lg:px-8">
        <div class="sm:mx-auto sm:w-full sm:max-w-sm">
            <img class="mx-auto h-10 w-auto" src="https://cdn-icons-png.flaticon.com/128/4652/4652340.png"
                alt="Your Company" />
            <h2 class="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">회원가입
            </h2>
        </div>

        <div class="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
            <form class="space-y-6" action="#" method="POST">
                <div>
                    <label for="text" class="block text-sm font-medium leading-6 text-gray-900">아이디</label>
                    <div class="mt-2">
                        <input id="email" name="email" type="text" autocomplete="email" required=""
                            v-model="userInfo.userId"
                            class="w-60 rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6" />
                        <button @click="idcheck"
                            class="ml-3 rounded-md px-3 py-1.5 text-sm font-semibold leading-6  shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">중복확인</button>

                    </div>
                    <div v-if="idValidMent != null">
                        <span v-show="!idValidMent" class="ml-2">중복되는 아이디가 존재합니다.</span>
                        <span v-show="idValidMent">사용 가능한 아이디 입니다.</span>
                    </div>
                </div>

                <div>
                    <label for="email" class="block text-sm font-medium leading-6 text-gray-900">이름</label>
                    <div class="mt-2">
                        <input id="email" name="email" type="email" autocomplete="email" required=""
                            v-model="userInfo.userName"
                            class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6" />
                    </div>
                </div>

                <div>
                    <label for="password" class="block text-sm font-medium leading-6 text-gray-900">비밀번호</label>
                    <div class="mt-2">
                        <input id="password" name="email" type="password" autocomplete="email" required=""
                            v-model="userInfo.userPwd"
                            class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6" />
                    </div>
                </div>

                <div>
                    <label for="text" class="block text-sm font-medium leading-6 text-gray-900">별명</label>
                    <div class="mt-2">
                        <input id="text" name="email" type="email" autocomplete="email" required=""
                            v-model="userInfo.userNickname"
                            class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6" />
                    </div>
                </div>

                <div>
                    <label for="text" class="block text-sm font-medium leading-6 text-gray-900">성별</label>
                    <Menu as="div" class="relative inline-block text-left">
                        <div>
                            <MenuButton
                                class="inline-flex w-full justify-center gap-x-1.5 rounded-md bg-white px-3 py-2 text-sm font-semibold text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50">
                                <input type="hidden"> {{ Gender }}
                                <ChevronDownIcon class="-mr-1 h-5 w-5 text-gray-400" aria-hidden="true" />
                            </MenuButton>
                        </div>

                        <transition enter-active-class="transition ease-out duration-100"
                            enter-from-class="transform opacity-0 scale-95" enter-to-class="transform opacity-100 scale-100"
                            leave-active-class="transition ease-in duration-75"
                            leave-from-class="transform opacity-100 scale-100"
                            leave-to-class="transform opacity-0 scale-95">
                            <MenuItems v-model="userInfo.userGender"
                                class="absolute right-0 z-10 mt-2 w-56 origin-top-right rounded-md bg-white shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none">
                                <div class="py-1">
                                    <MenuItem v-slot="{ active }" v-model="userInfo.userGender">
                                    <p @click="genderchange_man()"
                                        :class="[active ? 'bg-gray-100 text-gray-900' : 'text-gray-700', 'block px-4 py-2 text-sm']">
                                        남자</p>
                                    </MenuItem>
                                    <MenuItem v-slot="{ active }">
                                    <p @click="genderchange_femail()"
                                        :class="[active ? 'bg-gray-100 text-gray-900' : 'text-gray-700', 'block px-4 py-2 text-sm']">
                                        여자</p>
                                    </MenuItem>
                                </div>
                            </MenuItems>
                        </transition>
                    </Menu>
                </div>

                <div>
                    <label for="text" class="block text-sm font-medium leading-6 text-gray-900">생일</label>
                    <div class="mt-2">
                        <input id="text" name="email" type="email" placeholder="ex) 1997-03-25" autocomplete="email"
                            v-model="userInfo.userBirthday" required=""
                            class="  rounded-md  py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6" />
                    </div>
                </div>

                <form @submit.prevent="onSubmit">
                    <button type="submit"
                        class="flex w-full justify-center rounded-md bg-indigo-600 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">회원가입</button>
                </form>
            </form>

        </div>
    </div>
</template>

<style scoped></style>