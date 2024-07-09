package sfeir.ynshb.specification;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import sfeir.ynshb.model.Todo;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class TodoSpecificationBuilder {
    private final List<SearchCriteria> params;

    public void with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
    }

    public Specification<Todo> build() {
        if (params.isEmpty()) {
            return null;
        }

        List<Specification<Todo>> specs = new ArrayList<>();
        for (SearchCriteria param : params) {
            specs.add(new TodoSpecification(param));
        }

        Specification<Todo> result = specs.get(0);

        for (int i = 1; i < specs.size(); i++) {
            result = Specification.where(result).and(specs.get(i));
        }

        return result;
    }
}
