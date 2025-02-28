// * all the login related API
import request from '@/utils/request'

// * return code + message
export const login = (email, password) => {
  return request.post('/user/login', {
    email,
    password,
  })
}

export const register = (username, password, email) => {
  return request.post('/user', {
    username,
    password,
    email,
  })
}
