<script setup>
import { ref, onMounted } from 'vue';
import { getHotArticleList } from "@/api/board";
import { hotattraction } from "@/api/map";
import { httpStatusCode } from "@/util/http-status";
import { useRouter, useRoute } from "vue-router";

const router = useRouter();

const hotBoardList = ref({
    article_no: "",
    user_id: "",
    subject: "",
    content: "",
    hit: "",
    register_time: "",
    board_type: "",
    thumbnail: "",
})
const hotAttractionList = ref(null);
// const hotAttractionList = ref({
//     content_id: "",
//     content_type_id: "",
//     title: "",
//     addr1: "",
//     addr2: "",
//     zipcode: "",
//     tel: "",
//     first_image: "",
//     first_image2: "",
//     readcount: "",
//     sido_code: "",
//     gugun_code: "",
//     latitude: "",
//     longitude: "",
//     mlevel: "",

// });


onMounted(async () => {
    //해당 메시지에 대해 읽음 처리
    await getHotBoardList();
    await getHotAttractionList();
});


const getHotBoardList = async () => {
    await getHotArticleList(
        (response) => {
            console.log("성공적으로 인기게시글 불러옴");
            hotBoardList.value = response.data;
            console.log(hotBoardList)
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

const getHotAttractionList = async () => {
    await hotattraction(
        (response) => {
            console.log("성공적으로 인기관광지 불러옴");
            hotAttractionList.value = response.data;
            console.log(response.data)
        },
        (error) => {
            console.error(error);
        }
    );
};




// More products...
</script>

<template>
    <div class="relative">
        <img src="https://images.unsplash.com/photo-1698779165481-064ef9425c28?w=800&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHx0b3BpYy1mZWVkfDE3OHxGem8zenVPSE42d3x8ZW58MHx8fHx8"
            class="w-full h-[30rem] object-cover opacity-60" />
        <div
            class="isolate absolute top-1/2 left-[40%] -translate-y-1/2 -translate-x-1/2 text-blue text-4xl text-center leading-normal">
            <p>오늘은 어디로 여행을 가고 싶나요?</p>
        </div>
        <div
            class="isolate absolute top-[55%] left-[60%] -translate-y-1/2 -translate-x-1/2 text-white text-4xl text-center leading-normal mt-5">
            <h2 class=""><router-link to="" class="text-black text-3xl">여행지 둘러보기</router-link></h2>
        </div>


    </div>


    <div class="">
        <h1 class="mt-20 ml-20 text-3xl font-bold leading-9 tracking-tight text-gray-900">인기 게시물
        </h1>
        <div class="bg-white">
            <div class="mx-auto max-w-2xl px-4 py-16 sm:px-6 lg:max-w-7xl lg:px-8">
                <div class="grid grid-cols-1 gap-x-6 gap-y-10 sm:grid-cols-2 lg:grid-cols-4 xl:gap-x-8">
                    <div v-for="product in hotBoardList" :key="product.article_no" class="group relative">
                        <div
                            class="aspect-h-1 aspect-w-1 w-full overflow-hidden rounded-md bg-gray-200 lg:aspect-none group-hover:opacity-75 lg:h-60">
                            <img :src="product.thumbnail" alt="없음"
                                class="h-full w-full object-cover object-center lg:h-full lg:w-full" />
                        </div>
                        <div class="mt-4 flex justify-between">
                            <div>
                                <h3 class="text-sm text-gray-700">
                                    <router-link :to="{ name: 'board-detail', params: { articleno: product.articleNo } }">
                                        <span aria-hidden="true" class="absolute inset-0">
                                        </span>
                                        제목 : {{ product.subject }}

                                    </router-link>
                                </h3>
                                <p class="mt-1 text-sm text-gray-500">조회수 {{ product.hit }}</p>
                            </div>
                            <p class="text-sm font-medium text-gray-900">{{ product.register_time }}</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div>
        <h1 class="mt-20 ml-20 text-3xl font-bold leading-9 tracking-tight text-gray-900">추천 여행지
        </h1>
        <div class="bg-white">
            <div class="mx-auto max-w-2xl px-4 py-16 sm:px-6 lg:max-w-7xl lg:px-8">
                <div class="grid grid-cols-1 gap-x-6 gap-y-10 sm:grid-cols-2 lg:grid-cols-4 xl:gap-x-8">
                    <div v-for="product in hotAttractionList" :key="product.id" class="group relative">
                        <div
                            class="aspect-h-1 aspect-w-1 w-full overflow-hidden rounded-md bg-gray-200 lg:aspect-none group-hover:opacity-75 lg:h-60">
                            <img :src="product.firstImage" :alt="product.firstImage2"
                                class="h-full w-full object-cover object-center lg:h-full lg:w-full" />
                        </div>
                        <div class="mt-4 flex justify-between">
                            <div>
                                <h3 class="text-sm text-gray-700 font-semibold text-center">
                                    <span aria-hidden="true" class="absolute inset-0">
                                    </span>
                                    {{ product.title }}
                                </h3>
                                <p class="mt-1 text-sm text-gray-500">주소 : {{ product.addr1 }}</p>
                                <p class="text-sm font-medium text-gray-900">추천수: {{ product.readCount }}</p>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>
</template>
