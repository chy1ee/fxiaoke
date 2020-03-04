import request from '@/utils/request'

export function sendMsg(query) {
  return request({
    url: '/fxiaoke/msg',
    method: 'post',
    params: query
  })
}
