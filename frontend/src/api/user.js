import request from '@/utils/request'
export const getUserNameRequest = () => {
  return request.get('/user/name')
}
