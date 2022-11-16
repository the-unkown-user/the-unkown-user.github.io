const changeLike = (params) => {
    return $axios({
        url: `/commentInformation/like`,
        method: 'post',
        data: params
    })
};

const getCommentByPost = (params)=>{
  return $axios({
      url: `/commentInformation/get`,
      method: 'post',
      data: params
  })
};

const sendCommentOrReply = (params)=>{
    return $axios({
        url: `/commentInformation/send`,
        method: 'post',
        data: params
    })
};

const getUserInfo__ = (userId)=>{
    return $axios({
        url: `/user/userInfo/${userId}`,
        method: 'GET',
    })
};