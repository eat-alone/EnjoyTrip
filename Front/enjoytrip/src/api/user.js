import { localAxios } from "@/util/http-commons";

const local = localAxios();

function registUser(userInfo, success, fail) {
    console.log("User Info-Detail", userInfo);
    local.post(`http://localhost:80/vue/member/join`, JSON.stringify(userInfo)).then(success).catch(fail);
}

async function userConfirm(param, success, fail) {
    await local.post(`http://localhost:80/vue/member/login`, param).then(success).catch(fail);
}
  
async function findById(userid, success, fail) {
    local.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
    await local.get(`http://localhost:80/vue/member/info/${userid}`).then(success).catch(fail);
}

async function userDelete(userid, success, fail) {
    local.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
    await local.get(`http://localhost:80/vue/member/deleteMember/${userid}`).then(success).catch(fail);
}

async function userUpdate(userInfo, success, fail) {
  local.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
  await local.post(`http://localhost:80/vue/member/updateMember`, JSON.stringify(userInfo)).then(success).catch(fail);
}

async function userIdCheck(userid, success, fail) {
    local.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
    await local.get(`http://localhost:80/vue/member/idCheck/${userid}`).then(success).catch(fail);
  }

export {
    registUser,
    userConfirm,
    findById,
  userDelete,
    userUpdate,
    userIdCheck
};