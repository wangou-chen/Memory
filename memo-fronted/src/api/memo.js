import request from '../utils/request'

// 1. 查询所有备忘录（对应后端 /api/memos）
export function getMemoList() {
  return request({
    url: '/memos',
    method: 'get'
  })
}

// 2. 新增备忘录（对应后端 POST /api/memos）
export function addMemo(data) {
  return request({
    url: '/memos',
    method: 'post',
    data
  })
}

// 3. 修改备忘录（对应后端 PUT /api/memos/ID）
export function updateMemo(id, data) {
  return request({
    url: `/memos/${id}`,
    method: 'put',
    data
  })
}

// 4. 删除备忘录（对应后端 DELETE /api/memos/ID）
export function deleteMemo(id) {
  return request({
    url: `/memos/${id}`,
    method: 'delete'
  })
}