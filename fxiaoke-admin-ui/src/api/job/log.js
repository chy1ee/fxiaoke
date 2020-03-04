import request from '@/utils/request'

export function listLog(query) {
  return request({
    url: '/job/log',
    method: 'get',
    params: query
  })
}

export function listLogDetail(query) {
  return request({
    url: '/job/log/detail/' + query.logId,
    method: 'get',
    params: query
  })
}

export function resetXXX(query) {
  return request({
    url: '/job/log/reset/' + query.id,
    method: 'post'
  })
}
