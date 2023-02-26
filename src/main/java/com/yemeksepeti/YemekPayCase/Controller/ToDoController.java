package com.yemeksepeti.YemekPayCase.Controller;

import com.yemeksepeti.YemekPayCase.Service.ToDoService;
import com.yemeksepeti.YemekPayCase.dto.TodoRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/todo")
@AllArgsConstructor
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

    @PutMapping("/completed/{id}")
    public ResponseEntity<?> updateCompletedId(@PathVariable("id") int id,@RequestParam boolean completed){
        return ResponseEntity.ok(toDoService.updateCompletedId(id,completed));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") int id){
        return ResponseEntity.ok(toDoService.deleteById(id));
    }


}
