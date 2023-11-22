import { ref } from "vue";
import { defineStore } from "pinia";

export const useTripStore = defineStore("tripStore", () => {
  const planList = ref([]);

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
      planList.value.push(attInfo);
    }
  };

  const delPlan = (id) => {
    for (let i = 0; i < planList.value.length; i++) {
      if (planList.value[i].contentId == id) {
        planList.value.pop(i);
        break;
      }
    }
  };

  return {
    planList,
    addPlan,
    delPlan,
  };
});
