package ru.practicum.stats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.stats.model.statshit.StatsHit;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsRepository extends JpaRepository<StatsHit, Long> {

    List<StatsHit> findAllByUri(String uri);

    @Query(value = "SELECT DISTINCT s.app, s.uri, s.ip FROM StatsHit AS s WHERE s.uri = ?1")
    List<StatsHit> findDistinctByUriAndIpAndApp(String uri);

    List<StatsHit> findAllByUriAndTimestampBetween(String uri, LocalDateTime start, LocalDateTime end);

    @Query(value = "SELECT DISTINCT s.app, s.uri, s.ip FROM StatsHit AS s " +
            "WHERE s.uri = ?1 AND s.timestamp BETWEEN ?2 AND ?3")
    List<StatsHit> findDistinctByUriAndTimestampBetween(String uri, LocalDateTime start, LocalDateTime end);
}