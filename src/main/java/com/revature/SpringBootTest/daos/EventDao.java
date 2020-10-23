package com.revature.SpringBootTest.daos;

import com.revature.SpringBootTest.models.Event;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "events", path = "events", itemResourceRel = "event")
public interface EventDao extends PagingAndSortingRepository<Event, Long> {
}
