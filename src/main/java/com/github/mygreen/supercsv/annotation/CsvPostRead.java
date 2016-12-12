package com.github.mygreen.supercsv.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ライフサイクルイベントをハンドリングするためのアノテーションです。
 * <p>レコードの読み込み後に、このアノテーションを付与した任意のメソッドが実行されます。</p>
 * 
 * 
 * 
 * @since 2.0
 * @author T.TSUCHIE
 *
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CsvPostRead {
    
}
