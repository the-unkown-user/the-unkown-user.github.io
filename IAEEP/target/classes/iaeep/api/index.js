const registerUser= (params) => {
    return $axios({
        url: `/user/register`,
        method: 'post',
        data:params
    })
};

const sendCode_ = (params) => {
    return $axios({
        url: `/user/sendCode`,
        method: 'post',
        data:JSON.stringify({"phone":params})
    })
};

const login = (params) => {
    return $axios({
        url: `/user/login`,
        method: 'post',
        data:params,
    })
};

const resetPassword = (params) => {
    return $axios({
        url: `/user/resetPassword`,
        method: 'post',
        data:params
    })
};

const loginOut = () => {
  return $axios({
      url: `/user/loginOut`,
      method: 'post',
  })
};

const savePersonalInfo = (params) => {
    return $axios({
        url: `/personal/savePersonalInfo`,
        method: 'post',
        data:params
    })
};

const saveEnterpriseInfo = (params) => {
    return $axios({
        url: `/enterprise/saveEnterpriseInfo`,
        method: 'post',
        data:params
    })
};

const getAllContacts = (userId) => {
    return $axios({
        url: `/contact/getAllContacts/${userId}`,
        method: 'get',
    })
};

const findContactList = (username) => {
    return $axios({
        url: `/contact/findContactList?username=`+username,
        method: 'get',
    })
};

const addContacts = (row) => {
    return $axios({
        url: `/contact/addContacts`,
        method: 'post',
        data:row
    })
};

const deleteContact = (id) => {
    return $axios({
        url: `/contact/deleteContact/${id}`,
        method: 'get',
    })
};

const getChatMessage = (params) => {
    return $axios({
        url: `/chat/getChatMessage`,
        method: 'post',
        data:params
    })
};

const getFollowList = (params) => {
    return $axios({
        url: `/follow/getFollowList`,
        method: 'post',
        data:params
    })
};

const cancelFollow = (id) => {
    return $axios({
        url: `/follow/cancel/${id}`,
        method: 'get',
    })
};

const cancelPostCollection = (id) => {
    return $axios({
        url: `/collection/cancelCollection/${id}`,
        method: 'get',
    })
};

const addProjectInfo = (params) => {
    return $axios({
        url: `/project/addProjectInfo`,
        method: 'post',
        data:params
    })
};


const updateProjectInfo = (params) => {
    return $axios({
        url: `/project/updateProjectInfo`,
        method: 'post',
        data:params
    })
};

const getSearchProjectList = (params) => {
    return $axios({
        url: `/project/getSearchProjectList`,
        method: 'post',
        data:params,
    })
};

const getSearchPostList = (params) => {
    return $axios({
        url: `/posts/getSearchPostList`,
        method: 'post',
        data:params,
    })
};

const getPostCollection = (userId) => {
    return $axios({
        url: `/collection/getCollection/${userId}`,
        method: 'get',
    })
};

const getProject = (userId) => {
    return $axios({
        url: `/project/getProject/${userId}`,
        method: 'get',
    })
};

const changeToRead = (params) => {
    return $axios({
        url: `/contact/changeToRead`,
        method: 'post',
        data:params
    })
};
const toRead = (params) => {
    return $axios({
        url: `/contact/toRead`,
        method: 'post',
        data:params
    })
};