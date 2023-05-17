package hmfb.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.Comparator;

import hmfb.core.constants.MessageFieldType;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FixedString {
    int value();                          // 항목길이
    MessageFieldType type();              // 항목타입
    int order();                          // 항목순서
    String grpYn() default "N";           // 그룹여부
    String grpName() default "UNDEFINED"; // 그룹객체
    String grpCnt() default "1";          // 단건 : 1, 다건 : N
    int encType() default 0;              // 대상외 : 0, 양방향 : 1, 단방향 : 2
    
    Comparator<Field> FIELD_COMPARATOR = new Comparator<Field>() {
        @Override
        public int compare(Field o1, Field o2) {
            final int order1 = o1.getAnnotation(FixedString.class).order();
            final int order2 = o2.getAnnotation(FixedString.class).order();
            return order1 - order2;
        }
    };
}
