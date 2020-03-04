import request from '@/utils/request'

export function fetchJob(query) {
  return request({
    url: '/job/job',
    method: 'get',
    params: query
  })
}

export function editJob(query) {
  return request({
    url: '/job/job',
    method: 'post',
    params: query
  })
}

export function deleteJob(query) {
  return request({
    url: '/job/job/' + query.id,
    method: 'delete'
  })
}

export function runJob(query) {
  return request({
    url: '/job/job/run',
    method: 'post',
    params: query
  })
}

export function enableJob(query) {
  return request({
    url: '/job/job/status',
    method: 'post',
    params: query
  })
}
