import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import BoardView from "../views/BoardView.vue";
import BoardList from "../components/board/BoardList.vue";
import BoardDetail from "../components/board/BoardDetail.vue";
import BoardWrite from "../components/board/BoardWrite.vue";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";
import MapView from "../views/MapView.vue";
import TestView from "../views/TestView.vue";
import MapSearch from "../components/map/MapSearch.vue";
import MyPlan from "../components/map/MyPlan.vue";
import SelectDate from "../components/map/SelectDate.vue";
import TripPlan from "../components/map/TripPlan.vue";

const onlyAuthUser = async (to, from, next) => {
  const memberStore = useMemberStore();
  const { userInfo, isValidToken } = storeToRefs(memberStore);
  const { getUserInfo } = memberStore;

  let token = sessionStorage.getItem("accessToken");
  if (userInfo.value != null && token) {
    await getUserInfo(token);
  }
  // console.log(isValidToken.value)
  // console.log(userInfo.value)
  if (!isValidToken.value || userInfo.value === null) {
    next({ name: "user-login" });
  } else {
    next();
  }
};

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: HomeView,
    },

    {
      path: "/user",
      name: "user",
      children: [
        {
          path: "login",
          name: "user-login",
          component: () => import("@/components/user/UserLogin.vue"),
        },
        {
          path: "join",
          name: "user-join",
          component: () => import("@/components/user/regist.vue"),
        },
        {
          path: "mypage/:userid",
          name: "user-mypage",
          component: () => import("@/components/user/UserMyPage.vue"),
        },
        {
          path: "modify/:userid",
          name: "user-modify",
          beforeEnter: onlyAuthUser,
          component: () => import("@/components/user/UserModify.vue"),
        },
        {
          path: "follow",
          name: "user-follow",
          beforeEnter: onlyAuthUser,
          component: () => import("@/components/user/Follow.vue"),
        },
        {
          path: "follower",
          name: "user-follower",
          beforeEnter: onlyAuthUser,
          component: () => import("@/components/user/Follower.vue"),
        },
        {
          path: "search",
          name: "user-search",
          beforeEnter: onlyAuthUser,
          component: () => import("@/components/user/UserSearch.vue"),
        },
      ],
    },
    {
      path: "/letter",
      name: "letter",
      children: [
        {
          path: "receive",
          name: "letter-receive",
          beforeEnter: onlyAuthUser,
          component: () => import("@/components/letter/letters.vue"),
        },
        {
          path: "detail/:userid/:username",
          name: "letter-detail",
          beforeEnter: onlyAuthUser,
          component: () => import("@/components/letter/letterDetail.vue"),
        },
      ],
    },
    {
      path: "/board",
      name: "board",
      component: BoardView,
      redirect: { name: "board-list" },
      children: [
        {
          path: "list/",
          name: "board-list",
          component: BoardList,
        },
        {
          path: "detail/:articleno?/:type?",
          name: "board-detail",
          component: BoardDetail,
        },
        {
          path: "write/:type/:articleno",
          name: "board-write",
          component: BoardWrite,
        },
        {
          path: "/announceList",
          name: "board-announce",
          component: () => import("@/components/board/announceList.vue"),
        },
      ],
    },
    {
      path: "/map",
      name: "map",
      component: MapView,
      children: [
        {
          path: "/test",
          name: "test",
          component: TestView,
          children: [
            {
              path: "/selectdate",
              name: "selectdate",
              component: SelectDate,
            },
            {
              path: "/mapsearch",
              name: "mapsearch",
              component: MapSearch,
            },
            {
              path: "/myplan",
              name: "myplan",
              component: MyPlan,
            },
            {
              path: "/tripplan",
              name: "tripplan",
              component: TripPlan,
            },
          ],
        },
      ],
    },
  ],
});

export default router;
