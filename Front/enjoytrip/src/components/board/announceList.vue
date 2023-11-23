<template>
    <ul role="list" class="mx-auto divide-y hover:divide-solid divide-gray-400 w-3/4">
        <div class="flex justify-end mb-4">
            <div class="mt-10 flex gap-x-4 ">
                <select class="rounded-md" v-model="key" @change="changeKey(key)">
                    <option v-for="option in selectOption" :key="option.value" :value="option.value"
                        :disabled="option.value === '' ? true : false">
                        {{ option.text }}
                    </option>
                </select>
                <input id="keyword" name="keyword" v-model="param.word"
                    class="min-w-0 flex-auto rounded-md border-1 border-black bg-white/5 px-3 py-1 text-black shadow-sm ring-1 ring-inset ring-white/10 focus:ring-2 focus:ring-inset focus:ring-indigo-500 sm:text-sm sm:leading-6"
                    placeholder="검색어" />
                <button type="button" @click="getArticleList"
                    class="flex-none rounded-md bg-indigo-500 px-2 py-2 text-sm text-white shadow-sm hover:bg-indigo-400 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-500">
                    검색
                </button>
            </div>
        </div>
        <li v-for="article in articles" :key="article.articleno" class="flex justify-between gap-x-6 py-5">
            <div class="flex min-w-0 gap-x-4">
                <p class="text-sm font-semibold leading-6 text-gray-900">{{ article.articleNo }}</p>
                <div class="min-w-0 flex-auto">
                    <p class="text-sm font-semibold leading-6 text-gray-900">
                        <router-link :to="{
                            name: 'board-detail',
                            params: { articleno: article.articleNo, type: 2 },
                        }">{{ article.subject }}</router-link>
                    </p>
                    <p class="mt-1 truncate text-xs leading-5 text-gray-500">
                        작성자 |
                        <router-link :to="{ name: 'board-list' }">{{ article.userNickName }}</router-link>
                    </p>
                </div>
            </div>
            <div class="hidden shrink-0 sm:flex sm:flex-col sm:items-end">
                <p class="text-sm leading-6 text-gray-900">조회수 | {{ article.hit }}</p>
                <p class="mt-1 text-xs leading-5 text-gray-500">
                    <time :datetime="article.registtime">{{ article.registerTime }}</time>
                </p>
            </div>
        </li>
        <PageNavigation :current-page="currentPage" :total-page="totalPage" @pageChange="onPageChange">
        </PageNavigation>
    </ul>
</template>
  
<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { listArticle } from "@/api/board.js";
import PageNavigation from "../common/PageNavigation.vue";
const router = useRouter();
const key = ref("");
const selectOption = ref([
    { text: "검색조건", value: "" },
    { text: "글번호", value: "article_no" },
    { text: "제목", value: "subject" },
    { text: "작성자아이디", value: "user_id" },
]);

const articles = ref([]);
const currentPage = ref(1);
const totalPage = ref(0);
const { VITE_ARTICLE_LIST_SIZE } = import.meta.env;
const param = ref({
    pgno: currentPage.value,
    spp: VITE_ARTICLE_LIST_SIZE,
    key: "",
    word: "",
    boardtype: "2",
});

onMounted(() => {
    getArticleList();
});

const changeKey = (val) => {
    param.value.key = val;
};

const getArticleList = () => {
    listArticle(
        param.value,
        ({ data }) => {
            console.log(param.value)
            articles.value = data.articles;
            currentPage.value = data.currentPage;
            totalPage.value = data.totalPageCount;
        },
        (error) => {
            console.error(error);
        }
    );
};

const onPageChange = (val) => {
    currentPage.value = val;
    param.value.pgno = val;
    getArticleList();
};

const moveWrite = () => {
    router.push({ name: "board-announce", params: { type: "regist", articleno: 0 } });
};
</script>
  