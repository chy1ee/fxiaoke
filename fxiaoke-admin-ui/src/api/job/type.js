import request from '@/utils/request'

export function listType(query) {
  return request({
    url: '/job/type',
    method: 'get',
    params: query
  })
}

export function refresh(query) {
  return request({
    url: '/job/type/refresh',
    method: 'get',
    params: query
  })
}
