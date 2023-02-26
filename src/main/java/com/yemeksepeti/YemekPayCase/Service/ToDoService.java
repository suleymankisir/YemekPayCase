package com.yemeksepeti.YemekPayCase.Service;

import com.yemeksepeti.YemekPayCase.Mapper.ModelMapperConfiguration;
import com.yemeksepeti.YemekPayCase.Model.ToDo;
import com.yemeksepeti.YemekPayCase.Repo.ToDoRepo;
import com.yemeksepeti.YemekPayCase.dto.TodoRequest;
import com.yemeksepeti.YemekPayCase.dto.TodoResponse;
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
        ToDo toDo = toDoRepo.findById(id).get();
        return this.modelMapperConfiguration.forResponse().map(toDo,TodoResponse.class);

    }

    public TodoResponse add(TodoRequest todoRequest){
        ToDo toDo = this.modelMapperConfiguration.forRequest().map(todoRequest,ToDo.class);
        ToDo saveTodo=this.toDoRepo.save(toDo);
        return this.modelMapperConfiguration.forResponse().map(saveTodo,TodoResponse.class);
    }

    @Transactional
    public Optional<?> updateById(int id,TodoRequest todoRequest){
        if (toDoRepo.findById(id).isPresent()) {
            ToDo updateToDo = this.modelMapperConfiguration.forRequest().map(todoRequest, ToDo.class);
            updateToDo.setId(id);
            toDoRepo.save(updateToDo);
            TodoResponse todoResponse = this.modelMapperConfiguration.forResponse().map(updateToDo, TodoResponse.class);

            return Optional.ofNullable(todoResponse);
        }
        return Optional.of("Todo Not Found");
    }


    public Optional<?> updateCompletedId(int id,boolean completed){
        int i = toDoRepo.updateById(id,completed);
        if (i==1){
            ToDo toDo = toDoRepo.findById(id).get();
            return Optional.ofNullable(this.modelMapperConfiguration.forResponse().map(toDo,TodoResponse.class));
        }
        else
            return Optional.of("there is a problem");
    }

    public Optional<?> deleteById(int id){

        Optional<ToDo> toDoOptional = toDoRepo.findById(id);
        if (toDoOptional.isPresent()) {
            this.toDoRepo.deleteById(id);
            return Optional.ofNullable(this.modelMapperConfiguration.forResponse().map(toDoOptional.get(),TodoResponse.class));
        }
        else
            return Optional.of("Todo not Found");
    }

}
