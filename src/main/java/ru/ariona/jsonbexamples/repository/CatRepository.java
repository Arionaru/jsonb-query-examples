package ru.ariona.jsonbexamples.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.ariona.jsonbexamples.domain.Cat;

import java.util.List;

@Repository
public interface CatRepository extends JpaRepository<Cat, Long> {
    @Query(
            nativeQuery = true,
            value = "select * from cat where jsonb_exists(toys,:toy)")
    List<Cat> findAllByToy(@Param("toy") String toy);

    @Query(
            nativeQuery = true,
            value = "select * from cat where jsonb_exists_any(toys, string_to_array(:#{T(java.lang.String).join(',',#toys)},','))"
    )
    List<Cat> findByAnyToy(@Param("toys") List<String> toys);

    @Query(
            nativeQuery = true,
            value = "SELECT * from cat where lower(props->>'color') = lower(:color)"
    )
    List<Cat> findByColor(@Param("color") String color);

    @Query(
            nativeQuery = true,
            value = "select * from cat where cast(props->>'tailLength' as bigint) > :length"
    )
    List<Cat> findByCatsTailLongerThan(@Param("length") Long length);

    @Query(
            nativeQuery = true,
            value = "select * from cat where  jsonb_exists(to_jsonb(array(select jsonb_array_elements(slaves) ->> 'name')), :name)"
    )
    List<Cat> findBySlaveName(@Param("name") String name);
}