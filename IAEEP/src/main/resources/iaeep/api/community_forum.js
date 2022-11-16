// 查询详情接口
const getPosts= (id) => {
    return $axios({
        url: `/posts/getPost/${id}`,
        method: 'get'
    })
};

const addFollowUser = (username) => {
    return $axios({
        url: `/follow/addUser/${username}`,
        method: 'get'
    })
};

const addContactsUser = (username) => {
    return $axios({
        url: `/contact/addContacts/`,
        method: 'post',
        data:JSON.stringify({"name": username})
    })
};

const getNewPost = () => {
    return $axios({
        url: `/posts/getPostList/${1}`,
        method: 'get',
    })
};

const getHotPost = () => {
    return $axios({
        url: `/posts/getPostList/${2}`,
        method: 'get',
    })
};

const getRecommendPost = () => {
    return $axios({
        url: `/posts/getPostList/${3}`,
        method: 'get',
    })
};

const getUserInfo = (userId) => {
    return $axios({
        url: `/user/userInfo/${userId}`,
        method: 'get',
    })
};
