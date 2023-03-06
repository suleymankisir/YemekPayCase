package com.yemeksepeti.YemekPayCase.Service;

import com.yemeksepeti.YemekPayCase.Dto.TodoRequest;
import com.yemeksepeti.YemekPayCase.Dto.TodoResponse;
import com.yemeksepeti.YemekPayCase.Mapper.ModelMapperConfiguration;
import com.yemeksepeti.YemekPayCase.Model.ToDo;
import com.yemeksepeti.YemekPayCase.Repo.ToDoRepo;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ToDoService {


    private ToDoRepo toDoRepo;

    private ModelMapperConfiguration modelMapperConfiguration;

    public List<TodoResponse> getAll(){

        List<ToDo> toDo = this.toDoRepo.findAll();
        List<TodoResponse> todoResponses = toDo.stream().map(todo -> this.modelMapperConfiguration.forResponse().map(todo,TodoResponse.class)).toList();
        return  todoResponses;
    }

    public TodoResponse getById(int id){
        Optional<ToDo> toDo = toDoRepo.findById(id);
        if(toDo.isPresent()){
            return this.modelMapperConfiguration.forResponse().map(toDo.get(),TodoResponse.class);
        }
        throw new RuntimeException();
    }

    public TodoResponse add(TodoRequest todoRequest){
        ToDo toDo = this.modelMapperConfiguration.forRequest().map(todoRequest,ToDo.class);
        ToDo saveTodo=this.toDoRepo.save(toDo);
        return this.modelMapperConfiguration.forResponse().map(saveTodo,TodoResponse.class);
    }

    @Transactional
    public Optional<TodoResponse> updateById(int id,TodoRequest todoRequest) {

        Optional<ToDo> todo = toDoRepo.findById(id);
        if(todo.isPresent()){
            ToDo updateToDo = this.modelMapperConfiguration.forRequest().map(todoRequest, ToDo.class);
            updateToDo.setId(id);
            toDoRepo.save(updateToDo);
            TodoResponse todoResponse = this.modelMapperConfiguration.forResponse().map(updateToDo, TodoResponse.class);
            return Optional.ofNullable(todoResponse);
        }
        throw new RuntimeException();


    }



    public Optional<TodoResponse> deleteById(int id){


        Optional<ToDo> toDoOptional = toDoRepo.findById(id);
        if (toDoOptional.isPresent()){
            toDoRepo.deleteById(id);
            return Optional.ofNullable(this.modelMapperConfiguration.forResponse().map(toDoOptional.get(),TodoResponse.class));
        }
        throw new RuntimeException();
    }

}
