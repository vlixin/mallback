package com.lixin.litemall.db.dao;

import com.lixin.litemall.db.domain.LitemallLog;
import com.lixin.litemall.db.domain.LitemallLogExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LitemallLogMapper {
    long countByExample(LitemallLogExample example);

    int deleteByExample(LitemallLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LitemallLog record);

    int insertSelective(LitemallLog record);

    LitemallLog selectOneByExample(LitemallLogExample example);

    LitemallLog selectOneByExampleSelective(@Param("example") LitemallLogExample example, @Param("selective") LitemallLog.Column... selective);

    List<LitemallLog> selectByExampleSelective(@Param("example") LitemallLogExample example, @Param("selective") LitemallLog.Column... selective);

    List<LitemallLog> selectByExample(LitemallLogExample example);

    LitemallLog selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") LitemallLog.Column... selective);

    LitemallLog selectByPrimaryKey(Integer id);

    LitemallLog selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") LitemallLog record, @Param("example") LitemallLogExample example);

    int updateByExample(@Param("record") LitemallLog record, @Param("example") LitemallLogExample example);

    int updateByPrimaryKeySelective(LitemallLog record);

    int updateByPrimaryKey(LitemallLog record);

    int logicalDeleteByExample(@Param("example") LitemallLogExample example);

    int logicalDeleteByPrimaryKey(Integer id);
}
