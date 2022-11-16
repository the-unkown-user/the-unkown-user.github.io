const getUserInfo_ = (userId) => {
    return $axios({
        url: `/user/userInfo/${userId}`,
        method: 'get',
    })
};

const getLastProjectList = () => {
    return $axios({//获取最新项目
        url: `/project/list/${2}`,
        method: 'get',
    })
};

const getAllProject = () => {
    return $axios({//获取全部项目
        url: `/project/list/${1}`,
        method: 'get',
    })
};

const getSearchProject = (params) => {
    return $axios({
        url: `/project/search`,
        method: 'post',
        data: params
    })
};

