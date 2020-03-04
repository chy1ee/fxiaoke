import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/user/login',
    method: 'post',
    data
  })
}

export function logout() {
  return request({
    url: '/user/logout',
    method: 'post'
  })
}

export function getInfo(token) {
  return request({
    url: '/sys/user/info',
    method: 'get',
    params: { token }
  })
}

export function password(data) {
  return request({
    url: '/sys/user/password',
    method: 'post',
    params: data
  })
}
