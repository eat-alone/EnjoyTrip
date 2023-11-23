import { localAxios } from "@/util/http-commons";

const local = localAxios();

async function getReceiveCount(userid, success, fail) { //처리해야함 ㅠㅠ
    local.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
    await local.get(`http://localhost:80/vue/member/getReceiveCount/${userid}`).then(success).catch(fail);
}

async function receivelist(userid, success, fail) {
    local.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
    await local.get(`http://localhost:80/vue/member/receivelist/${userid}`).then(success).catch(fail);
}

async function sendlist(userid, success, fail) {
    local.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
    await local.get(`http://localhost:80/vue/member/sendlist/${userid}`).then(success).catch(fail);
}

async function getDetail(userid, success, fail) {
    local.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
    await local.get(`http://localhost:80/vue/member/getDetailLetter/${userid}`).then(success).catch(fail);
}

async function sendLetters(param, success, fail) {
    local.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
    await local.post(`http://localhost:80/vue/member/sendLetter`, param).then(success).catch(fail);
}

async function isReadCheck(userid, success, fail) {
    local.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
    await local.post(`http://localhost:80/vue/member/isReadCheck`, userid).then(success).catch(fail);
} 

async function deleteData(id, success, fail) {
    local.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
    await local.get(`http://localhost:80/vue/member/deleteLetter/${id}`).then(success).catch(fail);
}

export {
    receivelist,
    getDetail,
    sendLetters,
    isReadCheck,
    deleteData,
    getReceiveCount,
    sendlist
};