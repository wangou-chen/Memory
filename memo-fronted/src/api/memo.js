import request from '../utils/request'

// 1. 查询所有备忘录（分页版）
export function getMemoList(pageNum = 1, pageSize = 10) {
  return request({
    url: '/api/memos',
    method: 'get',
    params: {
      pageNum,
      pageSize
    }
  })
}

// 2. 新增备忘录
export function addMemo(data) {
  return request({
    url: '/api/memos',
    method: 'post',
    data
  })
}

// 3. 修改备忘录
export function updateMemo(id, data) {
  return request({
    url: `/api/memos/${id}`,
    method: 'put',
    data
  })
}

// 4. 删除备忘录
export function deleteMemo(id) {
  return request({
    url: `/api/memos/${id}`,
    method: 'delete',
  })
}