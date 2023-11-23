import { ref } from "vue";
import { defineStore } from "pinia";
import { receivelist, sendlist, getReceiveCount } from "@/api/letter";
import { httpStatusCode } from "@/util/http-status";
export const useLetterStore = defineStore("letterStore", () => {

  
  const receiveLetter = ref({
    Id: "",
content: "",
fromId: "",
id: "",
isRead: "",
nickName: "",
sendDate: "",
subject: "",
toId: ""
  });
  const sendLetter = ref({
    Id: "",
    content: "",
    fromId: "",
    id: "",
    isRead: "",
    nickName: "",
    sendDate: "",
    subject: "",
    toId: ""
  });
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
  const getSendLetter = async (userId) => {
    await sendlist(
      userId,
        (response) => {
          if (response.status === httpStatusCode.OK) {
            sendLetter.value = response.data;
            console.log(response.data)
            console.log("*********")
            console.log(sendLetter.value);
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

  
    const noReadLetterCount = ref(null);
    const noReadLetterCountvalue = ref(false);
    
    const getLetterCount = async (userId) => {
      await getReceiveCount(
        userId,
        (response) => {
          if (response.status === httpStatusCode.OK) {
            console.log(response.data)
            if (response.data.substr(0, 1) > 0) {
              alert(response.data);
              noReadLetterCount.value = response.data.substr(0, 1)
              noReadLetterCountvalue.value = true;
            } else {
              noReadLetterCountvalue.value = false;
            }
            // console.log(noReadLetterCount)
          } else {
            alert(response.data);
          }
        },
      );
    };

    
    

  return {
    getReceiveLetter,
    getSendLetter,
    getLetterCount,
    receiveLetter,
    sendLetter
  };
});
