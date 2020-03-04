import request from '@/utils/request'

export function fetchInfo(data) {
  return request({
    url: '/sys/dashboard',
    method: 'get',
    params: data
  })
}