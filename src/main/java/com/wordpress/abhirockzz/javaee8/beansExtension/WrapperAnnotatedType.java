package com.wordpress.abhirockzz.javaee8.beansExtension;

import com.wordpress.abhirockzz.javaee8.interceptors.Monitored;

import javax.enterprise.inject.spi.AnnotatedConstructor;
import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.util.AnnotationLiteral;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

public class WrapperAnnotatedType<A> implements AnnotatedType<A> {

    private final AnnotatedType<A> delegate;
    private final Set<Annotation> annotations;



    public WrapperAnnotatedType(final AnnotatedType<A> at, Annotation additionalAnnotation) {
        this.delegate = at;
        this.annotations = new HashSet<Annotation>(at.getAnnotations().size() + 1);
        this.annotations.addAll(at.getAnnotations());
        this.annotations.add(additionalAnnotation);
    }

    @Override
    public <T extends Annotation> T getAnnotation(Class<T> annotationType) {
        for (final Annotation annotation : this.annotations) {
            if (annotation.annotationType() == annotationType ){
                return annotationType.cast(annotation);
            }
        }
        return null;
    }

    @Override
    public boolean isAnnotationPresent(Class<? extends Annotation> annotationType) {
        return getAnnotation(annotationType)!= null;
    }


    @Override
    public Class<A> getJavaClass() {
        return this.delegate.getJavaClass();
    }

    @Override
    public Set<AnnotatedConstructor<A>> getConstructors() {
        return this.delegate.getConstructors();
    }

    @Override
    public Set<AnnotatedMethod<? super A>> getMethods() {
        return this.delegate.getMethods();
    }

    @Override
    public Set<AnnotatedField<? super A>> getFields() {
        return this.delegate.getFields();
    }

    @Override
    public Type getBaseType() {
        return this.delegate.getBaseType();
    }

    @Override
    public Set<Type> getTypeClosure() {
        return this.delegate.getTypeClosure();
    }


    @Override
    public Set<Annotation> getAnnotations() {
        return this.annotations;
    }
}
