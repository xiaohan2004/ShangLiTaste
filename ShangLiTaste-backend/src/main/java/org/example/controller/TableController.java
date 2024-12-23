package org.example.controller;

import org.example.pojo.Table;
import org.example.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tables")
public class TableController {

    @Autowired
    private TableService tableService;

    @GetMapping
    public ResponseEntity<List<Table>> getAllTables() {
        return new ResponseEntity<>(tableService.getAllTables(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Table> getTableById(@PathVariable Integer id) {
        Table table = tableService.getTableById(id);
        if (table != null) {
            return new ResponseEntity<>(table, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Table> createTable(@RequestBody Table table) {
        tableService.addTable(table);
        return new ResponseEntity<>(table, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Table> updateTable(@PathVariable Integer id, @RequestBody Table table) {
        Table existingTable = tableService.getTableById(id);
        if (existingTable != null) {
            table.setTableId(id);
            tableService.updateTable(table);
            return new ResponseEntity<>(table, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTable(@PathVariable Integer id) {
        Table existingTable = tableService.getTableById(id);
        if (existingTable != null) {
            tableService.deleteTable(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

