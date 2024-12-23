package org.example.service;

import org.example.pojo.Table;
import java.util.List;

public interface TableService {
    List<Table> getAllTables();
    Table getTableById(Integer id);
    void addTable(Table table);
    void updateTable(Table table);
    void deleteTable(Integer id);
}

