package sfeir.ynshb.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import sfeir.ynshb.model.Todo;

@AllArgsConstructor
public class TodoSpecification implements Specification<Todo> {

    private SearchCriteria criteria;

    @Override
    public Predicate toPredicate(Root<Todo> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        String key = criteria.getKey();
        String operation = criteria.getOperation();
        Object value = criteria.getValue();
        Class<?> type = root.get(key).getJavaType();

        if (type == String.class) {
            String lowerCaseValue = ((String) value).toLowerCase();
            return switch (operation) {
                case ">" -> builder.greaterThan(builder.lower(root.get(key)), lowerCaseValue);
                case "<" -> builder.lessThan(builder.lower(root.get(key)), lowerCaseValue);
                case ":" -> builder.like(builder.lower(root.get(key)), "%" + lowerCaseValue + "%");
                default -> null;
            };
        } else if (type.isEnum()) {
            if (operation.equals(":")) {
                Enum<?> enumValue = Enum.valueOf((Class<Enum>) type, value.toString());
                return builder.equal(root.get(key), enumValue);
            }
            return null;
        } else {
            return switch (operation) {
                case ">" -> builder.greaterThan(root.get(key), value.toString());
                case "<" -> builder.lessThan(root.get(key), value.toString());
                case ":" -> builder.equal(root.get(key), value);
                default -> null;
            };
        }
    }
}
