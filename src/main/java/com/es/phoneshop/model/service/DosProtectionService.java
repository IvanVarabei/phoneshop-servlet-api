package com.es.phoneshop.model.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DosProtectionService {
    private static final long THRESHOLD = 20;
    private static final long HISTORY_LIFESPAN = 60_000;
    private Map<String, Long> users = new ConcurrentHashMap<>();
    long historyClearingTime;

    private DosProtectionService() {
    }

    private static class DosProtectionServiceHolder {
        private static final DosProtectionService DOS_PROTECTION_SERVICE_INSTANCE = new DosProtectionService();
    }

    public static DosProtectionService getInstance() {
        return DosProtectionServiceHolder.DOS_PROTECTION_SERVICE_INSTANCE;
    }

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

    private void clearHistoryIfTimeExpired(){
        if (historyClearingTime != 0 && System.currentTimeMillis() - historyClearingTime > HISTORY_LIFESPAN) {
            users.clear();
            historyClearingTime = 0;
        }
        if (historyClearingTime == 0) {
            historyClearingTime = System.currentTimeMillis();
        }
    }
}
