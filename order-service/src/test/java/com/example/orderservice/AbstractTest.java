package com.example.orderservice;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;

/**
 * An abstract test to extend.
 *
 * @author Laffini
 *
 */
public abstract class AbstractTest {

    /**
     * Setup.
     */
    @BeforeEach
    public void setUp() {
        // Initialize mocks created above
        MockitoAnnotations.openMocks(this);
        this.init();
    }

    /**
     * Any additional initialization.
     */
    public abstract void init();

}
