package com.github.mygreen.supercsv.annotation.constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collection;

import org.supercsv.cellprocessor.ift.CellProcessor;

import com.github.mygreen.supercsv.annotation.DefaultGroup;
import com.github.mygreen.supercsv.builder.BuildCase;
import com.github.mygreen.supercsv.cellprocessor.constraint.ForbiddenWordProvider;

/**
 * 禁止語彙を含まないかどうか検証するためのアノテーションです。
 * <p>文字列型に指定可能です。</p>
 * 
 * <h3 class="description">基本的な使い方</h3>
 * <p>属性{@link #value()}で語彙を指定します。</p>
 * 
 * <pre class="highlight"><code class="java">
 * {@literal @CsvBean}
 * public class SampleCsv {
 *     
 *     {@literal @CsvColumn(number=1)}
 *     {@literal @CsvWordForbid({"馬鹿", "あほ"})}
 *     private String comment;
 *     
 *     // getter/setterは省略
 * }
 * </code></pre>
 * 
 * <h3 class="description">DBやファイルなどのリソースから取得する場合</h3>
 * <p>語彙をDBやファイルなどの別リソースから取得する場合は、属性{@link #provider()}にて、
 *    プロバイダ{@link ForbiddenWordProvider}の実装クラスを指定します。
 * </p>
 * <p>Spring Frameworkと連携している場合は、プロバイダクラスをSpringBeanとして登録しておくことでインジェクションできます。</p>
 * 
 * <pre class="highlight"><code class="java">
 * {@literal @CsvBean}
 * public class SampleCsv {
 *     
 *     {@literal @CsvColumn(number=1)}
 *     {@literal @CsvWordForbid(provider=FileForbiddenWordProvider.class)}
 *     private String comment;
 *     
 *     // setter/getterは省略
 * }
 * 
 * // プロバイダクラスの実装（ファイルから語彙を取得する）
 * public class FileForbiddenWordProvider implements ForbiddenWordProvider {
 *     
 *     {@literal @Override}
 *     public {@literal Collection<String>} getForbiddenWords(final FieldAccessor field) {
 *         
 *         try {
 *              return Files.readAllLines(
 *                      new File("forbidden_word.txt").toPath(), Charset.forName("UTF-8"));
 *              
 *         } catch (IOException e) {
 *             throw new RuntimeException("fail reading the forbidden words file.", e);
 *         }
 *         
 *     }
 * }
 * </code></pre>
 * 
 * @since 2.0
 * @author T.TSUCHIE
 *
 */
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repeatable(CsvWordForbid.List.class)
@CsvConstraint(value={})
public @interface CsvWordForbid {
    
    /**
     * 禁止語彙を指定します。
     * <p>DBやファイルなどから語彙を取得する場合は、{@link #provider()}を指定します。</p>
     * @return 複数指定可能です。
     */
    String[] value() default {};
    
    /**
     * 禁止語彙を取得するプロバイダクラスを指定します。
     * @return {@link ForbiddenWordProvider}の実装クラスを指定します。
     */
    Class<? extends ForbiddenWordProvider>[] provider() default {};
    
    /**
     * エラー時のメッセージを指定します。
     * <p>{@literal {key}}の書式の場合、プロパティファイルから取得した値を指定できます。</p>
     * 
     * <p>使用可能なメッセージ中の変数は下記の通りです。</p>
     * <ul>
     *   <li>lineNumber : カラムの値に改行が含まれている場合を考慮した実際の行番号です。1から始まります。</li>
     *   <li>rowNumber : CSVの行番号です。1から始まります。</li>
     *   <li>columnNumber : CSVの列番号です。1から始まります。</li>
     *   <li>label : カラムの見出し名です。</li>
     *   <li>validatedValue : 実際のカラムの値です。</li>
     *   <li>words : カラムの値の中に含まれている禁止語彙です。{@link Collection}です。</li>
     * </ul>
     * 
     * @return 省略した場合は、適用された{@link CellProcessor}に基づいたメッセージが出力されます。
     */
    String message() default "{com.github.mygreen.supercsv.annotation.constraint.CsvWordForbid.message}";
    
    /**
     * 適用するケースを指定します。
     * @return 何も指定しない場合は全てのケースに適用されます。
     */
    BuildCase[] cases() default {};
    
    /**
     * グループのクラスを指定します。
     * <p>処理ごとに適用するアノテーションを切り替えたい場合に指定します。
     * @return 指定しない場合は、{@link DefaultGroup}が適用され全ての処理に適用されます。
     */
    Class<?>[] groups() default {};
    
    /**
     * アノテーションの処理順序の定義。
     * @return 値が大きいほど後に実行されます。
     *         値が同じ場合は、アノテーションのクラス名の昇順になります。
     */
    int order() default 0;
    
    /**
     * アノテーションを複数個指定する際の要素です。
     */
    @Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        
        CsvWordForbid[] value();
    }
}
