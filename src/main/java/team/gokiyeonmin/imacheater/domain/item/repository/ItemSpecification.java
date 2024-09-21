package team.gokiyeonmin.imacheater.domain.item.repository;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import team.gokiyeonmin.imacheater.domain.Direction;
import team.gokiyeonmin.imacheater.domain.item.Door;
import team.gokiyeonmin.imacheater.domain.item.entity.Item;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ItemSpecification {

    public static Specification<Item> withFilters(Door door, Long floor, Long roomCount, Direction windowDirection,
                                                  Long minDeposit, Long maxDeposit, Long minRent, Long maxRent,
                                                  Boolean maintenanceFeeIncluded, LocalDate moveInDate, Boolean isSold) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (door != null) {
                predicates.add(criteriaBuilder.equal(root.get("door"), door));
            }
            if (floor != null) {
                predicates.add(criteriaBuilder.equal(root.get("floor"), floor));
            }
            if (roomCount != null) {
                predicates.add(criteriaBuilder.equal(root.get("roomCount"), roomCount));
            }
            if (windowDirection != null) {
                predicates.add(criteriaBuilder.equal(root.get("windowDirection"), windowDirection));
            }
            if (minDeposit != null && maxDeposit != null) {
                predicates.add(criteriaBuilder.between(root.get("deposit"), minDeposit, maxDeposit));
            }
            if (minRent != null && maxRent != null) {
                predicates.add(criteriaBuilder.between(root.get("rent"), minRent, maxRent));
            }
            if (maintenanceFeeIncluded != null) {
                predicates.add(criteriaBuilder.equal(root.get("maintenanceFeeIncluded"), maintenanceFeeIncluded));
            }
            if (moveInDate != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("moveInDate"), moveInDate));
            }
            if (isSold != null) {
                predicates.add(criteriaBuilder.equal(root.get("isSold"), isSold));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}