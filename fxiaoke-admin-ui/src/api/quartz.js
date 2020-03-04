import request from '@/utils/request'

export function getJobList(params) {
  return request({
    url: '/quartz/job',
    method: 'get',
    params
  })
}

export function saveJob(params) {
  return request({
    url: '/quartz/job',
    method: 'post',
    params
  })
}

export function changeJobStatus(params) {
  return request({
    url: '/quartz/job/status',
    method: 'post',
    params
  })
}

export function deleteJob(params) {
  return request({
    url: '/quartz/job/delete',
    method: 'post',
    params
  })
}

export function triggerJob(params) {
  return request({
    url: '/quartz/job/trigger',
    method: 'post',
    params
  })
}

export function getTriggerList(params) {
  return request({
    url: '/quartz/trigger',
    method: 'get',
    params
  })
}

export function changeTriggerStatus(params) {
  return request({
    url: '/quartz/trigger/status',
    method: 'post',
    params
  })
}

export function saveTrigger(params) {
  return request({
    url: '/quartz/trigger',
    method: 'post',
    params
  })
}

export function deleteTrigger(params) {
  return request({
    url: '/quartz/trigger/delete',
    method: 'post',
    params
  })
}

export function getGroupList(params) {
  return request({
    url: '/quartz/group',
    method: 'get',
    params
  })
}

export function deleteGroup(params) {
  return request({
    url: '/quartz/group',
    method: 'delete',
    params
  })
}

export function addGroup(params) {
  return request({
    url: '/quartz/group',
    method: 'post',
    params
  })
}

export function listLog(params) {
  return request({
    url: '/quartz/log',
    method: 'get',
    params
  })
}
