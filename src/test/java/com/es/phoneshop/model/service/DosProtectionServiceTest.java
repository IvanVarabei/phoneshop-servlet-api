package com.es.phoneshop.model.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class DosProtectionServiceTest {
    private DosProtectionService dosProtectionService = DosProtectionService.getInstance();

    @Before
    public void setup() {

    }

    @Test
    public void testIsAllowed() {
        boolean actual = dosProtectionService.isAllowed("address");
        assertTrue(actual);
    }

    @Test
    public void testIsAllowedExceed() {
        for (int i = 0; i <= 20; i++) {
            dosProtectionService.isAllowed("address");
        }
        boolean actual = dosProtectionService.isAllowed("address");
        assertFalse(actual);
    }

    @Test
    public void testIsAllowedSecondMinute() throws InterruptedException {
        for (int i = 0; i <= 20; i++) {
            dosProtectionService.isAllowed("address");
        }
        Thread.sleep(60_001);
        boolean actual = dosProtectionService.isAllowed("address");
        assertTrue(actual);
    }
}
