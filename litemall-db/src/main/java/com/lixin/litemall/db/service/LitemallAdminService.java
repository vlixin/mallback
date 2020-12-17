package com.lixin.litemall.db.service;


import com.github.pagehelper.PageHelper;
import com.lixin.litemall.common.api.ResultCode;
import com.lixin.litemall.common.exception.ApiException;
import com.lixin.litemall.db.dao.LitemallAdminMapper;
import com.lixin.litemall.db.domain.LitemallAdmin;
import com.lixin.litemall.db.domain.LitemallAdmin.Column;
import com.lixin.litemall.db.domain.LitemallAdminExample;
import com.lixin.litemall.security.util.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LitemallAdminService {
    private final Column[] result = new Column[]{Column.id, Column.username, Column.avatar, Column.roleIds};
    private final BCryptPasswordEncoder crypt = new BCryptPasswordEncoder();
    @Resource
    private LitemallAdminMapper adminMapper;

    public List<LitemallAdmin> findAdmin(String username) {
        LitemallAdminExample example = new LitemallAdminExample();
        example.or().andUsernameEqualTo(username).andDeletedEqualTo(false);
        return adminMapper.selectByExample(example);
    }

    public LitemallAdmin findAdmin(Integer id) {
        return adminMapper.selectByPrimaryKey(id);
    }

    public List<LitemallAdmin> querySelective(String username, Integer page, Integer limit, String sort, String order) {
        LitemallAdminExample example = new LitemallAdminExample();
        LitemallAdminExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(username)) {
            criteria.andUsernameLike("%" + username + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return adminMapper.selectByExampleSelective(example, result);
    }

    public int updateById(LitemallAdmin admin) {
        admin.setUpdateTime(LocalDateTime.now());
        return adminMapper.updateByPrimaryKeySelective(admin);
    }

    public void deleteById(Integer id) {
        adminMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(LitemallAdmin admin) {
        admin.setAddTime(LocalDateTime.now());
        admin.setUpdateTime(LocalDateTime.now());
        adminMapper.insertSelective(admin);
    }

    public LitemallAdmin findById(Integer id) {
        return adminMapper.selectByPrimaryKeySelective(id, result);
    }

    public List<LitemallAdmin> all() {
        LitemallAdminExample example = new LitemallAdminExample();
        example.or().andDeletedEqualTo(false);
        return adminMapper.selectByExample(example);
    }

    /**
     * 验证用户名和密码
     *
     * @param username
     * @param password
     */

    public boolean verifyPassWord(String username, String password) {
        LitemallAdminExample litemallAdminExample = new LitemallAdminExample();
        LitemallAdminExample.Criteria criteria = litemallAdminExample.createCriteria();
        criteria.andUsernameEqualTo(username);
        LitemallAdmin litemallAdmin = adminMapper.selectOneByExampleSelective(litemallAdminExample);
        if (litemallAdmin == null) {
            throw new ApiException(ResultCode.WrongPassword);
        }
        if (!crypt.matches(password, litemallAdmin.getPassword())) {
            throw new ApiException(ResultCode.WrongPassword);
        }
        //判断其他错误
        return true;
    }

}
