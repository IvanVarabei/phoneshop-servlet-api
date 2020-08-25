package com.es.phoneshop.model.service.impl;

import com.es.phoneshop.model.service.DosProtectionService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultDosProtectionService implements DosProtectionService {
    private static final long THRESHOLD = 200;
    private static final long HISTORY_LIFESPAN = 60_000;
    private Map<String, Long> users = new ConcurrentHashMap<>();
    private long historyClearingTime;

    private DefaultDosProtectionService() {
    }

    private static class DosProtectionServiceHolder {
        private static final DefaultDosProtectionService DOS_PROTECTION_SERVICE_INSTANCE = new DefaultDosProtectionService();
    }

    public static DefaultDosProtectionService getInstance() {
        return DosProtectionServiceHolder.DOS_PROTECTION_SERVICE_INSTANCE;
    }

    @Override
    public boolean isAllowed(String remoteAddr) {
        clearHistoryIfTimeExpired();
        Long count = users.get(remoteAddr);
        if (count == null) {
            count = 1L;
        } else {
            if (count > THRESHOLD) {
                return false;
            }
            count++;
        }
        users.put(remoteAddr, count);
        return true;
    }

    private void clearHistoryIfTimeExpired() {
        if (historyClearingTime != 0 && System.currentTimeMillis() - historyClearingTime > HISTORY_LIFESPAN) {
            users.clear();
            historyClearingTime = 0;
        }
        if (historyClearingTime == 0) {
            historyClearingTime = System.currentTimeMillis();
        }
    }
}
