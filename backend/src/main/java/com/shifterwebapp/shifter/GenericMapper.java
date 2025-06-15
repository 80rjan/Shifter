package com.shifterwebapp.shifter;

import com.shifterwebapp.shifter.review.Review;
import com.shifterwebapp.shifter.review.ReviewDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

public interface GenericMapper<E, D> {

    D toDto(E entity);
    List<D> toDto(List<E> entities);

    E toEntity(D dto);
    List<E> toEntity(List<D> reviews);
}
