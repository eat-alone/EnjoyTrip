import { ref } from "vue";
import { defineStore } from "pinia";
import { saveUserPlan } from "@/api/map";
import { httpStatusCode } from "@/util/http-status";

export const useTripStore = defineStore("tripStore", () => {
  const planList = ref([]);
  const planDate = ref("");
  const pageNum = ref(0);
  const planName = ref("");
  const startDate = ref("");
  const endDate = ref("");
  const tripSearchList = ref([]);

  const setTripSearchList = (list) => {
    let length = tripSearchList.value.length;
    for (let i = 0; i < length; i++) {
      tripSearchList.value.pop();
    }
    for (let i = 0; i < list.length; i++) {
      tripSearchList.value.push(list[i]);
    }
    console.log("검색한 결과", tripSearchList.value);
  };

  const setDate = (start, end) => {
    startDate.value = start;
    endDate.value = end;
  };

  const setAdditionalParam = (val1, val2, val3, contentId) => {
    console.log("비용 시간 메모", val1, val2, val3, contentId);
    setTimeout(() => console.log("비용 시간 메모", val1, val2, val3, contentId), 3000);
    for (let i = 0; i < planList.value.length; i++) {
      if (planList.value[i].contentId == contentId) {
        planList.value[i].price = val1;
        planList.value[i].durationTime = val2;
        planList.value[i].memo = val3;
        break;
      }
    }
  };

  const addPlan = (attInfo) => {
    let flag = 0;
    for (let i = 0; i < planList.value.length; i++) {
      if (planList.value[i].contentId == attInfo.contentId) {
        console.log("이미 일정에 추가되어 있습니다.");
        flag = 1;
        break;
      }
    }
    if (flag == 0) {
      attInfo.date = planDate.value;
      attInfo.page = pageNum.value;
      attInfo.price = "1";
      attInfo.durationTime = "1";
      attInfo.memo = "1";
      console.log(attInfo);
      planList.value.push(attInfo);
    }
  };

  const delPlan = (id) => {
    console.log("ddddd", id);
    for (let i = 0; i < planList.value.length; i++) {
      if (planList.value[i].contentId == id) {
        planList.value[i] = {};
        planList.value[i].page = 0;
        break;
      }
    }
  };

  const changeDate = (date) => {
    let year = date.getFullYear();
    let month = date.getMonth() + 1;
    let day = date.getDate();
    planDate.value = year + "-" + month + "-" + day;
  };

  const setPage = (num) => {
    pageNum.value = num;
  };

  const setPlanName = (str) => {
    planName.value = str;
  };

  const savePlan = (id) => {
    let param = {};
    let createTime = new Date();
    let year = createTime.getFullYear();
    let month = createTime.getMonth() + 1;
    let day = createTime.getDate();
    createTime = year + "-" + month + "-" + day;
    param.userId = id;
    param.name = planName.value;
    param.planList = [];
    for (let i = 0; i < planList.value.length; i++) {
      param.planList.push({
        attractionId: planList.value[i].contentId,
        contentTypeId: planList.value[i].contentTypeId,
        date: planList.value[i].date,
        price: planList.value[i].price,
        durationTime: planList.value[i].durationTime,
        memo: planList.value[i].memo,
      });
    }
    param.createDate = createTime;
    param.startDate = startDate.value;
    param.endDate = endDate.value;
    console.log(param);
    saveUserPlan(
      param,
      ({ data }) => {
        alert("저장 완료");
      },
      (error) => {
        console.error(error);
      }
    );
  };

  return {
    planList,
    planDate,
    pageNum,
    planName,
    startDate,
    endDate,
    addPlan,
    delPlan,
    changeDate,
    setPage,
    setPlanName,
    savePlan,
    setDate,
    setAdditionalParam,
    tripSearchList,
    setTripSearchList,
  };
});
