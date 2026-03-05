package com.jewelry.managementsystem.mapper;

import org.mapstruct.MappingTarget;

import java.util.List;

///  Create the contract for all mappers and implementations remain cleaner
public interface GenericMapper <E,D> {

    D toDto(E e);
    E toEntity(D d);
    List<D> toDtosList (List<E> e);

    void updateFromDto( D dto, @MappingTarget E e);
}
