package com.library.api.exceptions;

import com.library.exception.BusisnessException;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ApiErros {
    List<String> erros;

    public ApiErros(BindingResult bindingResult){
        this.erros = new ArrayList<>();

        bindingResult.getAllErrors().forEach(error -> erros.add(error.getDefaultMessage()));
    }

    public ApiErros(BusisnessException ex) {
        this.erros = Arrays.asList(ex.getMessage());
    }

    public List<String> getErros(){
        return erros;
    }
}
