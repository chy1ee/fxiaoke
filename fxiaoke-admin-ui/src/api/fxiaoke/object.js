import request from '@/utils/request'

export function list(query) {
  return request({
    url: '/fxiaoke/object/list',
    method: 'get',
    params: query
  })
}

export function describe(query) {
  return request({
    url: '/fxiaoke/object/describe',
    method: 'get',
    params: query
  })
}

export function cache(query) {
  return request({
    url: '/fxiaoke/object/cache',
    method: 'get',
    params: query
  })
}
