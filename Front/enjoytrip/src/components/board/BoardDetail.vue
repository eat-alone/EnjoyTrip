<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { detailArticle, deleteArticle, listComment, registComment, deleteComment } from "@/api/board";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";

const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);

import axios from "axios";
const { VITE_VUE_API_URL } = import.meta.env;

const route = useRoute();
const router = useRouter();
const { articleno } = route.params;

const article = ref({});
const comment = ref({});
const commentInfo = ref({
  boardId: articleno,
  userId: userInfo.value.id,
  content: "",
})

onMounted(() => {
  console.log(userInfo.value)
  getArticle();
  getComment();
});

const getArticle = () => {
  detailArticle(
    articleno,
    ({ data }) => {
      console.log(data);
      article.value = data;
    },
    (error) => {
      console.error(error);
    }
  );
};

function deleteComments(id) {
  console.log(id)
  deleteComment(
    id,
    ({ data }) => {
      console.log(data);
      alert("해당 댓글을 삭제하였습니다.");
      getComment();
    },
    (error) => {
      console.error(error);
    }
  )
}


const getComment = () => {
  listComment(
    articleno,
    ({ data }) => {
      console.log(data);
      comment.value = data;
    },
    (error) => {
      console.error(error);
    }
  )
}

const commentRegist = () => {
  console.log(commentInfo.value);
  registComment(
    commentInfo.value,
    ({ data }) => {
      console.log(data);
      getComment();
    },
    (error) => {
      console.error(error);
    }
  );
}

function moveList() {
  router.push({ name: "board-list" });
}

function moveModify() {
  router.push({ name: "board-write", params: { type: "regist", articleno: articleno } });
}

function onDeleteArticle() {
  deleteArticle(
    articleno,
    (response) => {
      if (response.status == 200) moveList();
    },
    (error) => {
      console.error(error);
    }
  );
}

function returnFileSize(number) {
  if (number < 1024) {
    return number + "bytes";
  } else if (number > 1024 && number < 1048576) {
    return (number / 1024).toFixed(1) + "KB";
  } else if (number > 1048576) {
    return (number / 1048576).toFixed(1) + "MB";
  }
}

function fileDownload(saveFile) {
  console.log(saveFile);
  axios({
    url: `${VITE_VUE_API_URL}/board/download/${saveFile}`, // 파일 다운로드 요청 URL
    method: "GET", // 혹은 'POST'
    responseType: "blob", // 응답 데이터 타입 정의
  }).then((response) => {
    // 다운로드(서버에서 전달 받은 데이터) 받은 바이너리 데이터를 blob으로 변환합니다.
    const blob = new Blob([response.data]); // 특정 타입을 정의해야 경우에는 옵션을 사용해 MIME 유형을 정의 할 수 있습니다. // const blob = new Blob([this.content], {type: 'text/plain'}) // blob을 사용해 객체 URL을 생성합니다.
    const fileObjectUrl = window.URL.createObjectURL(blob); // blob 객체 URL을 설정할 링크를 만듭니다.
    const link = document.createElement("a");

    link.href = fileObjectUrl;
    link.style.display = "none"; // 다운로드 파일 이름을 지정 할 수 있습니다. // 일반적으로 서버에서 전달해준 파일 이름은 응답 Header의 Content-Disposition에 설정됩니다.

    link.download = extractDownloadFilename(response); // 다운로드 파일 이름을 추출하는 함수

    function extractDownloadFilename(response) {
      const disposition = response.headers["content-disposition"];

      console.log(disposition);
      const fileName = decodeURI(disposition.substring(22, disposition.length - 2));
      console.log(fileName);
      return fileName;
    } // 다운로드 파일의 이름은 직접 지정 할 수 있습니다. // link.download = "sample-file.xlsx"; // 링크를 body에 추가하고 강제로 click 이벤트를 발생시켜 파일 다운로드를 실행시킵니다.

    document.body.appendChild(link);
    link.click();
    link.remove(); // 다운로드가 끝난 리소스(객체 URL)를 해제합니다.

    window.URL.revokeObjectURL(fileObjectUrl);
  });
}
</script>

<template>
  <div class="mx-auto w-2/4">
    <li class="flex justify-between gap-x-6 py-5">
      <div class="flex min-w-0 gap-x-4">
        <div class="min-w-0 flex-auto">
          <p class="text-m font-semibold leading-6 text-gray-900">{{ article.subject }}</p>
          <p class="mt-1 truncate text-xs leading-5 text-gray-500">
            작성자 | {{ article.userNickName }}
          </p>
        </div>
      </div>
      <div class="hidden shrink-0 sm:flex sm:flex-col sm:items-end">
        <p class="text-sm leading-6 text-gray-900">조회수 | {{ article.hit }}</p>
        <p class="mt-1 text-xs leading-5 text-gray-500">
          <time>{{ article.registerTime }}</time>
        </p>
      </div>
    </li>
    <div class="mt-6 border-t border-gray-100">
      <dl class="divide-y divide-gray-100">
        <div class="px-4 py-6 sm:grid sm:grid-cols-2 sm:gap-4 sm:px-0 flex mx-auto">
          <dd class="mt-1 text-sm leading-6 text-gray-700 sm:col-span-2 sm:mt-0">
            {{ article.content }}
          </dd>
        </div>
        <div class="px-4 py-6">
          <dt class="text-lg font-medium leading-6 text-gray-900 mt-5">첨부파일</dt>
          <dd class="mt-2 text-sm text-gray-900 sm:col-span-2 sm:mt-0">
            <template v-if="article.fileInfos">
              <ul v-for="attatch in article.fileInfos" :key="attatch.index" role="list"
                class="divide-y divide-gray-100 rounded-md border border-gray-200">
                <li class="flex items-center justify-between py-4 pl-4 pr-5 text-sm leading-6">
                  <div class="flex w-0 flex-1 items-center">
                    <svg class="h-5 w-5 flex-shrink-0 text-gray-400" viewBox="0 0 20 20" fill="currentColor"
                      aria-hidden="true">
                      <path fill-rule="evenodd"
                        d="M15.621 4.379a3 3 0 00-4.242 0l-7 7a3 3 0 004.241 4.243h.001l.497-.5a.75.75 0 011.064 1.057l-.498.501-.002.002a4.5 4.5 0 01-6.364-6.364l7-7a4.5 4.5 0 016.368 6.36l-3.455 3.553A2.625 2.625 0 119.52 9.52l3.45-3.451a.75.75 0 111.061 1.06l-3.45 3.451a1.125 1.125 0 001.587 1.595l3.454-3.553a3 3 0 000-4.242z"
                        clip-rule="evenodd" />
                    </svg>
                    <div class="ml-4 flex min-w-0 flex-1 gap-2">
                      <span class="truncate font-medium">{{ attatch.originalFile }}</span>
                      <span class="flex-shrink-0 text-gray-400">{{
                        returnFileSize(attatch.size)
                      }}</span>
                    </div>
                  </div>
                  <div class="ml-4 flex-shrink-0">
                    <button @click="fileDownload(attatch.saveFile)"
                      class="font-medium text-indigo-600 hover:text-indigo-500">
                      Download
                    </button>
                  </div>
                </li>
              </ul>
            </template>
            <template v-else>
              <div class="text-right">첨부파일이 없습니다.</div>
            </template>
          </dd>
        </div>
      </dl>
      <div class="flex max-w-7xl">
        <button @click="moveList"
          class="mt-12 flex w-18 items-center justify-center rounded-md border border-transparent bg-indigo-500 px-2 py-3 text-base text-sm text-white hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2">
          목록
        </button>
        <template v-if="route.params.type == 1">
          <button @click="onDeleteArticle"
            class="mt-12 flex w-18 items-center justify-center rounded-md border border-transparent bg-red-500 px-2 py-3 text-base text-sm text-white hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2">
            삭제
          </button>
          <button @click="moveModify"
            class="mt-12 flex w-18 items-end justify-end rounded-md border border-transparent bg-blue-500 px-2 py-3 text-base text-sm text-white hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2">
            수정
          </button>
        </template>
      </div>
    </div>
  </div>

  <hr class="w-[80%] mx-auto border-2 mt-4">



  <div class="bg-gray-300 w-[70%] mx-auto shadow-sm">
    <hr class="border-1  mx-auto">
    <p class="w-[95%] ml-20 mx-auto font-semibold text-lg">댓글</p>
    <hr class="border-1  mx-auto">
  </div>
  <div v-for="ment in comment" :key="comment.id" class="w-[90%] mx-auto">
    <div class="w-[80%] mx-auto">
      <hr class="border-1">
      <p>
        <span class="font-semibold ml-5"> {{ ment.userNickname }} </span>
        <span class="ml-5 text-sm"> {{ ment.writeDate }} </span>
      </p>
      <span class="ml-5"> {{ ment.content }} </span>
      <button @click="deleteComments(ment.id)" v-if="userInfo.Id === ment.userId"
        class="w-18 rounded-md bg-danger p-2 text-center text-sm text-white shadow-sm hover:bg-teal-500  float-right">
        삭제
      </button>

      <hr class="border-1 mx-auto">
    </div>
  </div>


  <form @submit.prevent="commentRegist" class="left-[10%] mt-6 w-[70%] mx-auto mb-20">
    <label for="message" class="block text-sm font-semibold leading-6 text-gray-900">댓글 작성</label>
    <div class="flex items-center">
      <div class="flex-1 mt-2.5">
        <textarea name="message" id="message" rows="1" v-model="commentInfo.content"
          class="w-full rounded-md border-1 px-3.5 py-2 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-teal-600 sm:text-sm sm:leading-6" />
      </div>
      <div class="ml-4">
        <button type="submit"
          class="w-18 rounded-md bg-teal-600 px-3.5 py-2.5 text-center text-sm font-semibold text-white shadow-sm hover:bg-teal-500 ">
          작성하기
        </button>
      </div>

      <div class=" mt-10">
      </div>
    </div>
  </form>
</template>

<style scoped></style>
