package cn.accessbright.blade.repository;

import com.google.common.collect.Iterables;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.SingularAttribute;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by lee on 2016年6月29日.
 */
public class CustomSpecs {
    public static <T> Specification<T> byAuto(T example) {
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                EntityType<T> entityType = root.getModel();
                for (Attribute<T, ?> attr : entityType.getDeclaredAttributes()) {
                    Object attrValue = getValue(example, attr);
                    if (attrValue != null) {
                        if (attr.getJavaType() == String.class) {
                            if (!StringUtils.isEmpty(attrValue)) {
                                predicates.add(cb.like(root.get(attribute(entityType, attr.getName(), String.class)), pattern((String) attrValue)));
                            }
                        } else {
                            predicates.add(cb.equal(root.get(attribute(entityType, attr.getName(), attrValue.getClass())), attrValue));
                        }
                    }
                }
                return predicates.isEmpty() ? cb.conjunction() : cb.and(Iterables.toArray(predicates, Predicate.class));
            }
        };
    }

    private static <T> Object getValue(T example, Attribute<T, ?> attr) {
        return ReflectionUtils.getField((Field) attr.getJavaMember(), example);
    }

    private static String pattern(String attrValue) {
        return "%" + attrValue + "%";
    }

    private static <E, T> SingularAttribute<T, E> attribute(EntityType<T> entityType, String name, Class clazz) {
        return entityType.getDeclaredSingularAttribute(name, clazz);
    }
}
