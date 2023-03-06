package com.yemeksepeti.YemekPayCase.Controller;

import com.yemeksepeti.YemekPayCase.Dto.TodoRequest;
import com.yemeksepeti.YemekPayCase.Service.ToDoService;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/todo")
@AllArgsConstructor
@RestControllerAdvice("ToDoService.class")
public class ToDoController {

    private ToDoService toDoService;

    @GetMapping
    public ResponseEntity<?> getall(){
        return ResponseEntity.ok(toDoService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") int id){
        return ResponseEntity.ok(toDoService.getById(id));
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody TodoRequest todoRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(toDoService.add(todoRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateById(@PathVariable("id") int id, @RequestBody TodoRequest todoRequest){
        return ResponseEntity.ok(toDoService.updateById(id,todoRequest));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") int id){
        return ResponseEntity.ok(toDoService.deleteById(id));
    }


}
