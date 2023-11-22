<script setup>
import { ref, onMounted } from "vue";
import { storeToRefs } from "pinia";
import { useRouter, useRoute } from "vue-router";
import { getDetail, sendLetters, isReadCheck } from "@/api/letter";
import { useMemberStore } from "@/stores/member";
import { httpStatusCode } from "@/util/http-status";
import router from "../../router";
const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);

const letter = ref({
    subject: "",
    nickName: "",
    sendDate: "",
    content: "",

});

const sendContent = ref({
    fromId: userInfo.value.id,
    toId: "",
    subject: "",
    content: "",
})

const isSend = ref(false);

onMounted(() => {
    //해당 메시지에 대해 읽음 처리
    isReadOk(route.params.userid);
    getDetailLetter(route.params.userid);
});
const route = useRoute();


function returnletter() {
    router.push({ name: 'letter-receive' })
}

function sendLetter() {
    this.sendContent.toId = route.params.username;
    this.sendContent.subject = "[" + this.letter.subject + "] 에 대한 답신";
    this.isSend = false;
    console.log(route.params.username)
    sendData(this.sendContent);
}


const isReadOk = async (userid) => {
    await isReadCheck(
        userid,
        (response) => {
            if (response.status === httpStatusCode.OK) {
                console.log("성공적으로 읽음여부 처리완료");
            } else {
                console.log("읽음처리 에러");
            }
        },
        async (error) => {
            console.error(
                "getUserInfo() error code [토큰 만료되어 사용 불가능.] ::: ",
                error.response.status
            );
            isValidToken.value = false;

            await tokenRegenerate();
        }
    );
};

const sendData = async (param) => {
    await sendLetters(
        param,
        (response) => {
            if (response.status === httpStatusCode.OK) {
                alert(response.data);
            } else {
                alert(response.data);
            }
        },
        async (error) => {
            console.error(
                "getUserInfo() error code [토큰 만료되어 사용 불가능.] ::: ",
                error.response.status
            );
            isValidToken.value = false;

            await tokenRegenerate();
        }
    );
};


const getDetailLetter = async (userId) => {
    await getDetail(
        userId,
        (response) => {
            if (response.status === httpStatusCode.OK) {
                letter.value = response.data;
                console.log(response.data)
            } else {
                console.log("유저 정보 없음!!!!");
            }
        },
        async (error) => {
            console.error(
                "getUserInfo() error code [토큰 만료되어 사용 불가능.] ::: ",
                error.response.status
            );
            isValidToken.value = false;

            await tokenRegenerate();
        }
    );
};


</script>

<template>
    <div class="mx-auto w-3/4">
        <li class="flex justify-between gap-x-6 py-5">
            <div class="flex min-w-0 gap-x-4">
                <div class="min-w-0 flex-auto">
                    <p class="text-m font-semibold leading-6 text-gray-900">{{ letter.subject }}</p>
                    <p class="mt-1 truncate text-xs leading-5 text-gray-500">
                        작성자 | {{ letter.nickName }}
                    </p>
                </div>
            </div>
            <div class="hidden shrink-0 sm:flex sm:flex-col sm:items-end">
                <p class="text-sm leading-6 text-gray-900">조회수 | 10</p>
                <p class="mt-1 text-xs leading-5 text-gray-500">
                    <time>{{ letter.sendDate }}</time>
                </p>
            </div>
        </li>
        <div class="mt-6 border-t border-gray-100">
            <dl class="divide-y divide-gray-100">
                <div class="px-4 py-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-0">
                    <dd class="mt-1 text-sm leading-6 text-gray-700 sm:col-span-2 sm:mt-0">
                        {{ letter.content }}
                    </dd>
                </div>

            </dl>
            <div class="flex max-w-7xl">
                <button @click="returnletter()"
                    class="mt-12 flex w-18 items-center justify-center rounded-md border border-transparent bg-indigo-500 px-2 py-3 text-base text-sm text-white hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2">
                    목록
                </button>
                <button @click="isSend = !isSend"
                    class="mt-12 flex w-18 items-center justify-center rounded-md border border-transparent bg-indigo-500 px-2 py-3 text-base text-sm text-white hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2">
                    답장 보내기
                </button>
            </div>
        </div>
    </div>

    <div v-if="isSend">
        <div class="border rounded-5 w-4/5 mx-auto shadow-sm p-5 h-80 mt-5">
            <div class="relative mt-2 rounded-md shadow-sm">

                <textarea name="content" id="contents" v-model="sendContent.content"
                    class="block w-full h-40 rounded-md border-0 py-1.5 pl-7 pr-20 text-gray-900 ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                    placeholder="here content...."> </textarea>

            </div>
            <button @click="sendLetter()"
                class="mt-3 float-right flex w-18 items-center justify-center rounded-md border border-transparent bg-indigo-500 px-2 py-3 text-base text-sm text-white hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2">
                답장 보내기
            </button>
        </div>

    </div>
</template>

<style scoped></style>