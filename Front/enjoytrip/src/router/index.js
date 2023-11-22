import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import BoardView from "../views/BoardView.vue";
import BoardList from "../components/board/BoardList.vue";
import BoardDetail from "../components/board/BoardDetail.vue";
import BoardWrite from "../components/board/BoardWrite.vue";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";

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
          path: "detail/:articleno",
          name: "board-detail",
          component: BoardDetail,
        },
        {
          path: "write/:type/:articleno",
          name: "board-write",
          component: BoardWrite,
        },
      ],
    },
  ],
});

export default router;
