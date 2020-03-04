import request from '@/utils/request'

export function fetchConfig(data) {
  return request({
    url: '/sys/config/' + data.type,
    method: 'get',
    params: data
  })
}

export function saveApi(data) {
  return request({
    url: '/sys/config/api',
    method: 'post',
    params: data
  })
}

export function saveReport(data) {
  return request({
    url: '/sys/config/report',
    method: 'post',
    params: data
  })
}
