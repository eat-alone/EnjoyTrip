import { ref } from "vue";
import { useRouter } from "vue-router";
import { defineStore } from "pinia";
import { jwtDecode } from "jwt-decode";
import { followlist, followerlist } from "@/api/follow";
import { httpStatusCode } from "@/util/http-status";

export const useFollowStore = defineStore("followStore", () => {
  const router = useRouter();

    const follow = ref(null);
    const follower = ref(null);

    const getFollowList = (userId) => {
        followlist(
          userId,
            (response) => {
            if (response.status === httpStatusCode.OK) {
                follow.value = response.data;
                console.log(response.data);
            } else {
              console.log("유저 정보 없음!!!!");
            }
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
    
    const getFollowerList = (userId) => {
        followerlist(
            userId,
            (response) => {
            if (response.status === httpStatusCode.OK) {
                follower.value = response.data;
            } else {
              console.log("유저 정보 없음!!!!");
            }
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

      const deleteFollow = (userId) => {
        followerlist(
            userId,
            (response) => {
            if (response.status === httpStatusCode.OK) {
                follower.value = response.data;
            } else {
              console.log("유저 정보 없음!!!!");
            }
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
    
    return {
        getFollowList,
        getFollowerList,
        follow,
        follower
  };
}, {
  persist:true
}

);
