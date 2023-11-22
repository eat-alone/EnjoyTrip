import { localAxios } from "@/util/http-commons";

const local = localAxios();

async function followlist(userid, success, fail) {
    local.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
    await local.get(`http://localhost:80/vue/member/followlist/${userid}`).then(success).catch(fail);
}

async function followerlist(userid, success, fail) {
    local.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
    await local.get(`http://localhost:80/vue/member/followerlist/${userid}`).then(success).catch(fail);
}

async function unFollow(toId, fromId, success, fail) {
    local.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
    await local.get(`http://localhost:80/vue/member/unfollow/${toId}/${fromId}`).then(success).catch(fail);
}

async function onFollow(toId, fromId, success, fail) {
    local.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
    await local.get(`http://localhost:80/vue/member/following/${toId}/${fromId}`).then(success).catch(fail);
}



export {
    followlist,
    followerlist,
    unFollow,
    onFollow
};