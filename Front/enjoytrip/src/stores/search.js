import { ref } from "vue";
import { useRouter } from "vue-router";
import { defineStore } from "pinia";
import { jwtDecode } from "jwt-decode";

import { UserList} from "@/api/user";
import { httpStatusCode } from "@/util/http-status";

export const useSearchStore = defineStore("searchStore", () => {

    const userList = ref(null);


    const searchUser = (userId) => {
        UserList(
            userId,
            (response) => {
                if (response.status === httpStatusCode.OK) {
                    userList.value = response.data;
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
        userList,
        searchUser
    };

});