import { localAxios } from "@/util/http-commons";

const local = localAxios();

function getMyPlanList(userid, success, fail) {
  local.get(`/map/getmyplanlist/${userid}`).then(success).catch(fail);
}

function goInviteMember(planInfo, success, fail) {
    local.post(`/map/invitemember`, JSON.stringify(planInfo)).then(success).catch(fail);
  }

export { getMyPlanList, goInviteMember };