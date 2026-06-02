package com.riwi.intro.repository;

import com.riwi.intro.dto.EventSummaryDTO;
import com.riwi.intro.models.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Integer> {

    Page<Event> findByNameContainingIgnoreCase(String name, Pageable pageable);

    @Override
    @EntityGraph(attributePaths = {"venue", "categories"})
    Optional<Event> findById(Integer id);

    @Query("""
            select new com.riwi.intro.dto.EventSummaryDTO(
                e.id,
                e.name,
                e.date,
                v.name,
                v.city
            )
            from Event e
            join e.venue v
            where (:name is null or :name = '' or lower(e.name) like lower(concat('%', :name, '%')))
              and (:city is null or :city = '' or lower(v.city) like lower(concat('%', :city, '%')))
              and (:category is null or :category = '' or exists (
                    select 1
                    from Event e2 join e2.categories c
                    where e2.id = e.id
                      and lower(c.name) like lower(concat('%', :category, '%'))
              ))
              and (:capacity is null or v.capacity >= :capacity)
              and (:dateFrom is null or e.date >= :dateFrom)
              and (:dateTo is null or e.date <= :dateTo)
            order by e.date desc
            """)
    Slice<EventSummaryDTO> searchSummaries(
            @Param("name") String name,
            @Param("city") String city,
            @Param("category") String category,
            @Param("capacity") Integer capacity,
            @Param("dateFrom") String dateFrom,
            @Param("dateTo") String dateTo,
            Pageable pageable);
}
