package org.example.mapper;

import org.example.pojo.Table;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TableMapper {
    @Select("SELECT * FROM tables")
    List<Table> getAllTables();

    @Select("SELECT * FROM tables WHERE table_id = #{tableId}")
    Table getTableById(Integer tableId);

    @Insert("INSERT INTO tables(status, location) VALUES(#{status}, #{location})")
    @Options(useGeneratedKeys = true, keyProperty = "tableId")
    void insertTable(Table table);

    @Update("UPDATE tables SET status = #{status}, location = #{location} WHERE table_id = #{tableId}")
    void updateTable(Table table);

    @Delete("DELETE FROM tables WHERE table_id = #{tableId}")
    void deleteTable(Integer tableId);
}

