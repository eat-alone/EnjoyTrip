import { ref } from "vue";
import { useRouter } from "vue-router";
import { defineStore } from "pinia";
import { jwtDecode } from "jwt-decode";
import { followlist, followerlist, unFollow, onFollow } from "@/api/follow";
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
                console.log(userId)
                console.log("팔로우 리스트 다 불러옴!!")
                console.log("***********아래쪽 반환값")
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

      const deleteFollow = async(toId, fromId) => {
        await unFollow(
          toId,
          fromId,
            (response) => {
            if (response.status === httpStatusCode.OK) {
              alert(response.data);
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
    
    const following = (toId, fromId) => {
      onFollow(
        toId,
        fromId,
          (response) => {
            if (response.status === httpStatusCode.OK) {
            alert(response.data)
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
      deleteFollow,
      following,
        follow,
        follower
  };
}, {
  persist:true
}

);
