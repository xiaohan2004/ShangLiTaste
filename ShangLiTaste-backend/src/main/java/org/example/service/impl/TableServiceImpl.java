package org.example.service.impl;

import org.example.mapper.TableMapper;
import org.example.pojo.Table;
import org.example.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TableServiceImpl implements TableService {

    @Autowired
    private TableMapper tableMapper;

    @Override
    public List<Table> getAllTables() {
        return tableMapper.getAllTables();
    }

    @Override
    public Table getTableById(Integer id) {
        return tableMapper.getTableById(id);
    }

    @Override
    public void addTable(Table table) {
        tableMapper.insertTable(table);
    }

    @Override
    public void updateTable(Table table) {
        tableMapper.updateTable(table);
    }

    @Override
    public void deleteTable(Integer id) {
        tableMapper.deleteTable(id);
    }
}

