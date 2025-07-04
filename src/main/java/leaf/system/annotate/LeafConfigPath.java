package leaf.system.annotate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * LeafConfig配置文件路径
 */
@Target(ElementType.TYPE) // 只能用于类上
@Retention(RetentionPolicy.RUNTIME) // 运行时保留
public @interface LeafConfigPath {
    String value() default "/LeafConfig.properties";
}
