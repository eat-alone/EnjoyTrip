import { localAxios } from "@/util/http-commons";

const local = localAxios();

function listType(success, fail) {
  local.get("/map/type").then(success).catch(fail);
}

function listSido(success, fail) {
  local.get(`/map/sido`).then(success).catch(fail);
}

function listGugun(sidoCode, success, fail) {
  local.get(`/map/gugun/${sidoCode}`).then(success).catch(fail);
}

function listAttraction(param, success, fail) {
  local.get("/map/attraction", { params: param }).then(success).catch(fail);
}

function hotattraction(success, fail) {
  local.get(`/map/hotattraction`).then(success).catch(fail);
}

export { listSido, listGugun, listAttraction, listType, hotattraction };
