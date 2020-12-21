package com.lixin.litemall.db.dao;

import com.lixin.litemall.db.domain.LitemallSearchHistory;
import com.lixin.litemall.db.domain.LitemallSearchHistoryExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LitemallSearchHistoryMapper {
    long countByExample(LitemallSearchHistoryExample example);

    int deleteByExample(LitemallSearchHistoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LitemallSearchHistory record);

    int insertSelective(LitemallSearchHistory record);

    LitemallSearchHistory selectOneByExample(LitemallSearchHistoryExample example);

    LitemallSearchHistory selectOneByExampleSelective(@Param("example") LitemallSearchHistoryExample example, @Param("selective") LitemallSearchHistory.Column... selective);

    List<LitemallSearchHistory> selectByExampleSelective(@Param("example") LitemallSearchHistoryExample example, @Param("selective") LitemallSearchHistory.Column... selective);

    List<LitemallSearchHistory> selectByExample(LitemallSearchHistoryExample example);

    LitemallSearchHistory selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") LitemallSearchHistory.Column... selective);

    LitemallSearchHistory selectByPrimaryKey(Integer id);

    LitemallSearchHistory selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    int updateByExampleSelective(@Param("record") LitemallSearchHistory record, @Param("example") LitemallSearchHistoryExample example);

    int updateByExample(@Param("record") LitemallSearchHistory record, @Param("example") LitemallSearchHistoryExample example);

    int updateByPrimaryKeySelective(LitemallSearchHistory record);

    int updateByPrimaryKey(LitemallSearchHistory record);

    int logicalDeleteByExample(@Param("example") LitemallSearchHistoryExample example);

    int logicalDeleteByPrimaryKey(Integer id);
}
