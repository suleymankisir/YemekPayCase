package com.yemeksepeti.YemekPayCase.Mapper;

import lombok.AllArgsConstructor;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ModelMapperConfiguration {
    private ModelMapper modelMapper;


    public ModelMapper forResponse(){

        this.modelMapper.getConfiguration().
                setAmbiguityIgnored(true).
                setMatchingStrategy(MatchingStrategies.LOOSE);
        return this.modelMapper;
    }


    public ModelMapper forRequest(){
        this.modelMapper.getConfiguration().setAmbiguityIgnored(true).setMatchingStrategy(MatchingStrategies.STANDARD);
        return this.modelMapper;
    }


}
