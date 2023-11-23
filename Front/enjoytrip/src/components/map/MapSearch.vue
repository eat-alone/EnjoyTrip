<script setup>
import { ref, onMounted, watch } from "vue";
import { listSido, listGugun, listAttraction, listType } from "@/api/map.js";
import PageNavigation from "../common/PageNavigation.vue";
import { useTripStore } from "@/stores/trip";
import { useMemberStore } from "@/stores/member";

// const emit = defineEmits({planer : object});
const tripStore = useTripStore();
const { planList, addPlan, delPlan, savePlan } = tripStore;
const { userInfo } = useMemberStore();
const sidoList = ref([]);
const gugunList = ref([{ text: "구/군 선택", value: "" }]);
const typeList = ref([]);
const attractionList = ref([]);
const currentPage = ref(1);
const totalPage = ref(0);

const param = ref({
  sido: "",
  gugun: "",
  category: "",
  word: "",
  pgno: currentPage.value,
  spp: 10,
});

onMounted(() => {
  getSidoList();
  getTypeList();
  getAttractionList();
});

watch(
  () => param.value.sido,
  () => {
    console.log(param.value.sido);
    getGugunList();
  },
  { deep: true }
);

const appendPlan = (attraction) => {
  addPlan(attraction);
  console.log(planList);
};

const getAttractionList = () => {
  listAttraction(
    param.value,
    ({ data }) => {
      attractionList.value = data.attInfo;
      console.log(attractionList.value);
      currentPage.value = data.currentPage;
      totalPage.value = data.totalPageCount;
    },
    (error) => {
      console.error(error);
    }
  );
};

const getTypeList = () => {
  listType(
    ({ data }) => {
      let options = [];
      options.push({ text: "카테고리 선택", value: "" });
      data.forEach((type) => {
        options.push({ text: type.contentName, value: type.contentTypeId });
      });
      typeList.value = options;
    },
    (error) => {
      console.error(error);
    }
  );
};

const getSidoList = () => {
  listSido(
    ({ data }) => {
      console.log(data);
      let options = [];
      options.push({ text: "시/도 선택", value: "" });
      data.forEach((sido) => {
        options.push({ text: sido.sidoName, value: sido.sidoCode });
      });
      sidoList.value = options;
    },
    (error) => {
      console.error(error);
    }
  );
};

const getGugunList = () => {
  console.log("구군코드가져오자");
  listGugun(
    param.value.sido,
    ({ data }) => {
      console.log(data);
      let options = [];
      options.push({ text: "구/군 선택", value: "" });

      data.forEach((gugun) => {
        options.push({ text: gugun.gugunName, value: gugun.gugunCode });
      });
      gugunList.value = options;
    },
    (error) => {
      console.error(error);
    }
  );
};

const onSubmit = () => {
  getAttractionList();
};

const onReset = () => {
  param.value = {
    sido: "",
    gugun: "",
    category: "",
    word: "",
  };
};

const onPageChange = (val) => {
  currentPage.value = val;
  param.value.pgno = val;
  getAttractionList();
};

const savePlan1 = () => {
  if (userInfo == null) {
    alert("로그인 후 이용 가능합니다.");
  } else {
    savePlan(userInfo.id);
  }
};
</script>

<template>
  <div class="text-center">
    <b-form @submit="onSubmit" @reset="onReset">
      <b-form-group id="input-group-1" label="시/도 선택" label-for="input-1" class="mb-4">
        <b-form-select id="input-1" v-model="param.sido" :options="sidoList"></b-form-select>
      </b-form-group>

      <b-form-group id="input-group-2" label="구/군 선택" label-for="input-2" class="mb-4">
        <b-form-select id="input-2" v-model="param.gugun" :options="gugunList"></b-form-select>
      </b-form-group>

      <b-form-group id="input-group-3" label="카테고리 선택" label-for="input-3" class="mb-4">
        <b-form-select id="input-3" v-model="param.category" :options="typeList"></b-form-select>
      </b-form-group>

      <b-form-group id="input-group-4" label="키워드 검색" label-for="input-4" class="mb-4">
        <b-form-input id="input-4" v-model="param.word" placeholder="키워드 입력"></b-form-input>
      </b-form-group>

      <b-button type="submit" variant="primary" class="mr-2">검색</b-button>
      <b-button type="reset" variant="danger">초기화</b-button>
      <b-button variant="primary" @click="savePlan1" class="float-right mr-5">일정 저장</b-button>
    </b-form>
    <template v-for="attraction in attractionList" :key="attraction.contentId">
      <b-card class="mt-3" :header="attraction.title ? attraction.title : attraction.addr1"
        @click="appendPlan(attraction)">
        <!-- <button @click="appendPlan(attraction)" class="border border-1 rounded-4 bg-gray-300 p-1">일정추가</button> -->
        <img :src="attraction.firstImage" :alt="attraction.firstImage">
        <!-- <pre class="m-0">{{ attraction.firstImage }}</pre> -->
      </b-card>
    </template>
    <PageNavigation :current-page="currentPage" :total-page="totalPage" @pageChange="onPageChange"></PageNavigation>
  </div>
</template>

<style scoped></style>
