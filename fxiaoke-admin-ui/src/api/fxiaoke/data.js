import request from '@/utils/request'

export function query(query) {
  return request({
    url: '/fxiaoke/data/query',
    method: 'get',
    params: query
  })
}

export function get(query) {
  return request({
    url: '/fxiaoke/data/get',
    method: 'get',
    params: query
  })
}
