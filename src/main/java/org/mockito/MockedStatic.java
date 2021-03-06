/*
 * Copyright (c) 2007 Mockito contributors
 * This program is made available under the terms of the MIT License.
 */
package org.mockito;

import org.mockito.stubbing.OngoingStubbing;
import org.mockito.verification.VerificationMode;

import static org.mockito.Mockito.*;

/**
 * Represents an active mock of a type's static methods. The mocking only affects the thread
 * on which this static mock was created and it is not safe to use this object from another
 * thread. The static mock is released when this object's {@link MockedStatic#close()} method
 * is invoked. If this object is never closed, the static mock will remain active on the
 * initiating thread. It is therefore recommended to create this object within a try-with-resources
 * statement unless when managed explicitly, for example by using a JUnit rule or extension.
 * <p>
 * If the {@link Mock} annotation is used on fields or method parameters of this type, a static mock
 * is created instead of a regular mock. The static mock is activated and released upon completing any
 * relevant test.
 *
 * @param <T> The type being mocked.
 */
@Incubating
public interface MockedStatic<T> extends AutoCloseable {

    /**
     * See {@link Mockito#when(Object)}.
     */
    <S> OngoingStubbing<S> when(Verification verification);

    /**
     * See {@link Mockito#verify(Object)}.
     */
    default void verify(Verification verification) {
        verify(times(1), verification);
    }

    /**
     * See {@link Mockito#verify(Object, VerificationMode)}.
     */
    void verify(VerificationMode mode, Verification verification);

    /**
     * See {@link Mockito#reset(Object[])}.
     */
    void reset();

    /**
     * See {@link Mockito#clearInvocations(Object[])}.
     */
    void clearInvocations();

    /**
     * {@link Mockito#verifyNoMoreInteractions(Object...)}.
     */
    void verifyNoMoreInteractions();

    /**
     * See {@link Mockito#verifyNoInteractions(Object...)}.
     */
    void verifyNoInteractions();

    /**
     * Checks if this mock is closed.
     *
     * @return {@code true} if this mock is closed.
     */
    boolean isClosed();

    /**
     * Releases this static mock and throws a {@link org.mockito.exceptions.base.MockitoException} if closed already.
     */
    @Override
    void close();

    /**
     * Releases this static mock and is non-operational if already released.
     */
    void closeOnDemand();

    interface Verification {

        void apply() throws Throwable;
    }
}
