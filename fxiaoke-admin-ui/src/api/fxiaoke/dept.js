import request from '@/utils/request'

export function fetchDept(query) {
  return request({
    url: '/fxiaoke/dept',
    method: 'get',
    params: query
  })
}

export function fetchDeptUser(query) {
  return request({
    url: '/fxiaoke/dept/' + query.deptId,
    method: 'get',
    params: query
  })
}
