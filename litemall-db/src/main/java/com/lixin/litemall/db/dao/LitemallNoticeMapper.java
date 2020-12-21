package com.lixin.litemall.db.dao;

import com.lixin.litemall.db.domain.LitemallNotice;
import com.lixin.litemall.db.domain.LitemallNoticeExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LitemallNoticeMapper {
    long countByExample(LitemallNoticeExample example);

    int deleteByExample(LitemallNoticeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LitemallNotice record);

    int insertSelective(LitemallNotice record);

    LitemallNotice selectOneByExample(LitemallNoticeExample example);

    LitemallNotice selectOneByExampleSelective(@Param("example") LitemallNoticeExample example, @Param("selective") LitemallNotice.Column... selective);

    List<LitemallNotice> selectByExampleSelective(@Param("example") LitemallNoticeExample example, @Param("selective") LitemallNotice.Column... selective);

    List<LitemallNotice> selectByExample(LitemallNoticeExample example);

    LitemallNotice selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") LitemallNotice.Column... selective);

    LitemallNotice selectByPrimaryKey(Integer id);

    LitemallNotice selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") LitemallNotice record, @Param("example") LitemallNoticeExample example);

    int updateByExample(@Param("record") LitemallNotice record, @Param("example") LitemallNoticeExample example);

    int updateByPrimaryKeySelective(LitemallNotice record);

    int updateByPrimaryKey(LitemallNotice record);

    int logicalDeleteByExample(@Param("example") LitemallNoticeExample example);

    int logicalDeleteByPrimaryKey(Integer id);
}
