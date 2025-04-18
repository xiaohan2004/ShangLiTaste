package org.example.mapper;

import org.apache.ibatis.annotations.Select;
import org.example.pojo.OperateLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OperateLogMapper {

    //插入日志数据
    @Insert("insert into operate_log (operate_user, operate_time, class_name, method_name, cost_time) " +
            "values (#{operateUser}, #{operateTime}, #{className}, #{methodName}, #{costTime});")
    public void insertOperateLog(OperateLog log);

    @Select("select * from operate_log order by id desc limit 500")
    public List<OperateLog> getAllOperateLogs();

}
