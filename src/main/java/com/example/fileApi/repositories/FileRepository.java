package com.example.fileApi.repositories;


import com.example.fileApi.model.File;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends PagingAndSortingRepository<File, Long> {


}
