import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import BoardView from "../views/BoardView.vue";
import BoardList from "../components/board/BoardList.vue";
import BoardDetail from "../components/board/BoardDetail.vue";
import BoardWrite from "../components/board/BoardWrite.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: HomeView,
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
