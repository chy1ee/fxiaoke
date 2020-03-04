import request from '@/utils/request'

export function fetchGroup(query) {
  return request({
    url: '/job/group',
    method: 'get',
    params: query
  })
}

export function editGroup(query) {
  return request({
    url: '/job/group',
    method: 'post',
    params: query
  })
}

export function editStatus(query) {
  return request({
    url: '/job/group/status',
    method: 'post',
    params: query
  })
}
