import { ref } from "vue";
import { defineStore } from "pinia";
import { sendlist, receivelist } from "@/api/letter";
import { httpStatusCode } from "@/util/http-status";
export const useLetterStore = defineStore("letterStore", () => {

    const receiveLetter = ref(null);
  const getReceiveLetter = async (userId) => {
    await receivelist(
      userId,
        (response) => {
          if (response.status === httpStatusCode.OK) {
              receiveLetter.value = response.data;
              console.log(response.data)
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

    const getDetailLetter = async (userId) => {
        await receivelist(
          userId,
            (response) => {
              if (response.status === httpStatusCode.OK) {
                  receiveLetter.value = response.data;
                  console.log(response.data)
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
      getReceiveLetter,
      getDetailLetter,
      receiveLetter,
      
  };
});
