package org.example.controller;

import org.example.pojo.Result;
import org.example.pojo.Table;
import org.example.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/tables")
public class TableController {

    @Autowired
    private TableService tableService;

    @GetMapping
    public Result getAllTables() {
        return Result.success(tableService.getAllTables());
    }

    @GetMapping("/{id}")
    public Result getTableById(@PathVariable Integer id) {
        Table table = tableService.getTableById(id);
        if (table != null) {
            return Result.success(table);
        } else {
            return Result.error("Not Found");
        }
    }

    @PostMapping
    public Result createTable(@RequestBody Table table) {
        tableService.addTable(table);
        return Result.success(table);
    }

    @PutMapping("/{id}")
    public Result updateTable(@PathVariable Integer id, @RequestBody Table table) {
        Table existingTable = tableService.getTableById(id);
        if (existingTable != null) {
            table.setTableId(id);
            tableService.updateTable(table);
            return Result.success(table);
        } else {
            return Result.error("Not Found");
        }
    }

    @DeleteMapping("/{id}")
    public Result deleteTable(@PathVariable Integer id) {
        Table existingTable = tableService.getTableById(id);
        if (existingTable != null) {
            tableService.deleteTable(id);
            return Result.success();
        } else {
            return Result.error("Not Found");
        }
    }
}

