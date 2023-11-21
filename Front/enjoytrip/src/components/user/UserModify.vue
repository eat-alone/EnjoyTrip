<script setup>
import { ref, watch } from "vue";
import { storeToRefs } from "pinia";
import { useRoute, useRouter } from "vue-router";
import { useMemberStore } from "@/stores/member";
import { Dialog, DialogPanel, DialogTitle, TransitionChild, TransitionRoot } from '@headlessui/vue'
import { ExclamationTriangleIcon } from '@heroicons/vue/24/outline'

const route = useRoute();
const router = useRouter();


const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);
const { updateUser } = memberStore;

const open = ref(false)
const userPass = ref("");
const userConfirmPass = ref("");
const equalsMent = ref("값을 입력해주세요.");

const UpdateUser = async () => {
    await updateUser(userInfo);

    open.value = false;
    alert("회원정보가 수정되었습니다.")
    router.push({ name: 'user-mypage' })
}

function moveMyPage() {
    // console.log(userInfo.value)
    router.push({ name: 'user-mypage' });
}

watch(
    userConfirmPass,
    (nv, ov) => {
        if (userPass.value === nv) {
            equalsMent.value = "값이 일치합니다."
            userInfo.value.userPwd = nv;
        }
        else {
            equalsMent.value = "비밀번호가 일치하지 않습니다."
            userInfo.value.userPwd = nv;
        }
    }
)

watch(
    userPass,
    (nv, ov) => {
        if (userConfirmPass.value === nv) {
            equalsMent.value = "값이 일치합니다."
        }
        else {
            equalsMent.value = "비밀번호가 일치하지 않습니다."
        }
    }
)

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
            <img class="mx-auto h-10 w-auto" src="https://tailwindui.com/img/logos/mark.svg?color=indigo&shade=600"
                alt="Your Company" />
            <h2 class="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">회원정보 수정
            </h2>
        </div>

        <div class="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
            <form class="space-y-6" action="#" method="POST">
                <div>
                    <label for="password" class="block text-sm font-medium leading-6 text-gray-900">비밀번호</label>
                    <div class="mt-2">
                        <input id="password1" name="nicknamea" type="password" autocomplete="email" required=""
                            v-model="userPass"
                            class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6" />
                    </div>
                </div>

                <div>
                    <label for="password2" class="block text-sm font-medium leading-6 text-gray-900">비밀번호 재확인</label>
                    <div class="mt-2">
                        <input id="password" name="nicknameb" type="password" autocomplete="email" required=""
                            v-model="userConfirmPass"
                            class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6" />
                    </div>

                    <input id="textconfirm" name="nicknamec" type="email" autocomplete="email" required=""
                        v-model="equalsMent" disabled
                        class="block w-full rounded-md border-0 py-1.5 text-gray-900  ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6" />
                </div>

                <div>
                    <label for="text" class="block text-sm font-medium leading-6 text-gray-900">별명</label>
                    <div class="mt-2">
                        <input id="textnickname" name="nicknamed" type="email" autocomplete="email"
                            v-model="userInfo.userNickname"
                            class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6" />
                    </div>
                </div>


                <div class="bg-gray-50 px-4 py-3 sm:flex sm:flex-row-reverse sm:px-6">
                    <button type="submit" @click="moveMyPage"
                        class="flex justify-center rounded-md  px-3 py-1.5 text-sm font-semibold leading-6 shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600 border border-dark">취소</button>
                    <button type="submit" @click="open = true"
                        class="flex justify-center mr-5 rounded-md px-3 py-1.5 text-sm font-semibold leading-6 shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600 border border-dark">수정하기</button>
                </div>
            </form>

            <div v-if="open">
                <TransitionRoot as="template" :show="open">
                    <Dialog as="div" class="relative z-10" @close="open = false">
                        <TransitionChild as="template" enter="ease-out duration-300" enter-from="opacity-0"
                            enter-to="opacity-100" leave="ease-in duration-200" leave-from="opacity-100"
                            leave-to="opacity-0">
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
                                                    <ExclamationTriangleIcon class="h-6 w-6 text-red-600"
                                                        aria-hidden="true" />
                                                </div>
                                                <div class="mt-3 text-center sm:ml-4 sm:mt-0 sm:text-left">
                                                    <DialogTitle as="h3"
                                                        class="text-base font-semibold leading-6 text-gray-900">
                                                        입력된 정보대로 회원정보를 수정합니다.</DialogTitle>
                                                    <div class="mt-2">
                                                        <p class="text-sm text-gray-500">수정된 프로필 정보는 언제든 수정 가능합니다.</p>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="bg-gray-50 px-4 py-3 sm:flex sm:flex-row-reverse sm:px-6">
                                            <button type="button"
                                                class="mt-3 inline-flex w-full justify-center rounded-md bg-red-600 px-3 py-2 text-sm font-semibold  shadow-sm hover:bg-red-500 sm:ml-3 sm:w-auto"
                                                @click="UpdateUser">수정하기</button>
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
    </div>
</template>
<style scoped></style>
