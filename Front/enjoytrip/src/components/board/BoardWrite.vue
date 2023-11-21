<script setup>
import axios from "axios";
import { ref, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import { registArticle, getModifyArticle, modifyArticle } from "@/api/board";
const { VITE_VUE_API_URL } = import.meta.env;

const router = useRouter();
const route = useRoute();

const { type, articleno } = route.params;
console.log(type, articleno);
const isUseId = ref(false);

const formData = new FormData();

const selectFiles = ref([]);

const article = ref({
  articleNo: 0,
  subject: "",
  content: "",
  userId: 1,
  userNickName: "",
  hit: 0,
  registerTime: "",
  boardType: 1,
});

if (type === "modify") {
  getModifyArticle(
    articleno,
    ({ data }) => {
      article.value = data;
      isUseId.value = true;
    },
    (error) => {
      console.error(error);
    }
  );
  isUseId.value = true;
}

const subjectErrMsg = ref("");
const contentErrMsg = ref("");

watch(
  () => article.value.subject,
  (value) => {
    let len = value.length;
    if (len == 0 || len > 30) {
      subjectErrMsg.value = "제목을 확인해 주세요!!!";
    } else subjectErrMsg.value = "";
  },
  { immediate: true }
);

watch(
  () => article.value.content,
  (value) => {
    let len = value.length;
    if (len == 0 || len > 500) {
      contentErrMsg.value = "내용을 확인해 주세요!!!";
    } else contentErrMsg.value = "";
  },
  { immediate: true }
);

function returnFileSize(number) {
  if (number < 1024) {
    return number + "bytes";
  } else if (number > 1024 && number < 1048576) {
    return (number / 1024).toFixed(1) + "KB";
  } else if (number > 1048576) {
    return (number / 1048576).toFixed(1) + "MB";
  }
}

function selectFile(event) {
  for (let i = 0; i < event.target.files.length; i++) {
    selectFiles.value[i] = event.target.files[i];
  }
}

function onSubmit() {
  // event.preventDefault();

  if (subjectErrMsg.value) {
    alert(subjectErrMsg.value);
  } else if (contentErrMsg.value) {
    alert(contentErrMsg.value);
  } else {
    type === "regist" ? writeArticle() : updateArticle();
  }
}

function writeArticle() {
  console.log("글등록하자!!", article.value);
  console.log("첨부파일", selectFiles.value);
  formData.append(
    "article",
    new Blob([JSON.stringify(article.value)], { type: "application/json" })
  );
  for (let i = 0; i < selectFiles.value.length; i++) {
    formData.append("file", selectFiles.value[i]);
  }
  // formData.append("files", selectFiles.value);
  // for (let key of formData.keys()) {
  //   console.log(key);
  // }
  // for (let value of formData.values()) {
  //   console.log(value);
  // }
  // registArticle(
  //   article.value,
  //   (response) => {
  //     let msg = "글등록 처리시 문제 발생했습니다.";
  //     if (response.status == 201) msg = "글등록이 완료되었습니다.";
  //     alert(msg);
  //     moveList();
  //   },
  //   (error) => console.error(error)
  // );
  axios
    .post(`${VITE_VUE_API_URL}/board`, formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    })
    .then((response) => {
      // 응답 처리
      let msg = "글등록 처리시 문제 발생했습니다.";
      if (response.status == 201) msg = "글등록이 완료되었습니다.";
      alert(msg);
      moveList();
    })
    .catch((error) => {
      // 예외 처리
      (error) => console.error(error);
    });
}

function updateArticle() {
  console.log(article.value.articleNo + "번글 수정하자!!", article.value);
  modifyArticle(
    article.value,
    (response) => {
      let msg = "글수정 처리시 문제 발생했습니다.";
      if (response.status == 200) msg = "글정보 수정이 완료되었습니다.";
      alert(msg);
      moveList();
      // router.push({ name: "article-view" });
      // router.push(`/board/view/${article.value.articleNo}`);
    },
    (error) => console.log(error)
  );
}

function moveList() {
  router.push({ name: "board-list" });
}
</script>

<template>
  <div class="isolate bg-white px-6 py-24 sm:py-32 lg:px-8">
    <div
      class="absolute inset-x-0 top-[-10rem] -z-10 transform-gpu overflow-hidden blur-3xl sm:top-[-20rem]"
      aria-hidden="true"
    ></div>
    <div class="mx-auto max-w-2xl text-center">
      <h2 class="text-3xl font-bold tracking-tight text-gray-900 sm:text-4xl">글쓰기</h2>
    </div>
    <form @submit.prevent="onSubmit" class="mx-auto mt-16 max-w-xl sm:mt-20">
      <div class="grid grid-cols-1 gap-x-8 gap-y-6 sm:grid-cols-2">
        <div class="sm:col-span-2">
          <label for="company" class="block text-sm font-semibold leading-6 text-gray-900"
            >제목</label
          >
          <div class="mt-2.5">
            <input
              type="text"
              v-model="article.subject"
              :placeholder="article.subject"
              class="block w-full rounded-md border-0 px-3.5 py-2 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
            />
          </div>
        </div>

        <div class="sm:col-span-2">
          <label for="message" class="block text-sm font-semibold leading-6 text-gray-900"
            >내용</label
          >
          <div class="mt-2.5">
            <textarea
              v-model="article.content"
              :placeholder="article.content"
              class="block w-full rounded-md border-0 px-3.5 py-2 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
            ></textarea>
          </div>
        </div>
        <div class="mb-3">
          <label for="upfile" class="form-label">파일:</label>
          <input
            type="file"
            class="form-control border"
            id="upfile"
            name="upfile"
            multiple="multiple"
            @change="selectFile"
          />
          <ul v-if="selectFiles != null">
            <li v-for="file in selectFiles" :key="file.name">
              <span>{{ file.name }}</span>
              <span class="float-right">{{ returnFileSize(file.size) }}</span>
            </li>
          </ul>
        </div>
      </div>
      <div class="flex w-full">
        <button
          @click="moveList"
          class="mt-12 flex w-18 items-center justify-center rounded-md border border-transparent bg-indigo-500 px-2 py-3 text-base text-sm text-white hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2"
        >
          목록
        </button>
        <template v-if="true">
          <button
            type="submit"
            class="mr-0 flex mt-12 w-18 items-center justify-center rounded-md border border-transparent bg-indigo-500 px-2 py-3 text-base text-sm text-white hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2"
          >
            글작성
          </button>
        </template>
        <template v-else>
          <button
            type="submit"
            class="flex mt-12 w-18 items-end justify-end rounded-md border border-transparent bg-blue-500 px-2 py-3 text-base text-sm text-white hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2"
          >
            수정
          </button>
        </template>
      </div>
    </form>
  </div>
</template>

<style scoped></style>
