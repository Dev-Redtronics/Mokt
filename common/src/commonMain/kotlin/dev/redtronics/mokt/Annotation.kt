package dev.redtronics.mokt

/**
 * Marks a class or type as part of the Mokt Domain Specific Language (DSL).
 *
 * This annotation serves as a DSL marker to improve type safety and readability when working with Mokt's DSL.
 * It helps prevent implicit access to external receivers of the same type within nested builders,
 * making the DSL more predictable and safer to use.
 *
 * Usage:
 * - Apply to classes that are part of the Mokt builder DSL
 * - Apply to types that should be treated as part of the DSL context
 *
 * @since 0.1.0
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE)
@DslMarker
public annotation class MoktDSL